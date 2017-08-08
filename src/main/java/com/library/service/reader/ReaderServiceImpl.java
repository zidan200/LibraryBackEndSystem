package com.library.service.reader;

import com.library.dataaccessobject.BookRepository;
import com.library.dataaccessobject.ReaderRepository;
import com.library.domainobject.BookDO;
import com.library.domainobject.ReaderDO;
import com.library.domainvalue.BookStatus;
import com.library.domainvalue.GeoCoordinate;
import com.library.domainvalue.ReaderStatus;
import com.library.exception.BookAlreadyInUseException;
import com.library.exception.BookFunctionalException;
import com.library.exception.ConstraintsViolationException;
import com.library.exception.ReaderFunctionalException;
import com.library.exception.EntityNotFoundException;
import com.library.domainobject.AuthorDO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some reader specific things.
 * <p/>
 */
@Service
public class ReaderServiceImpl implements ReaderService
{

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(ReaderServiceImpl.class);

    private final ReaderRepository readerRepository;

    private final BookRepository bookRepository;


    public ReaderServiceImpl(final ReaderRepository readerRepository, final BookRepository bookRepository)
    {
        this.readerRepository = readerRepository;
        this.bookRepository = bookRepository;
    }


    /**
     * Selects a reader by id.
     *
     * @param readerId
     * @return found reader
     * @throws EntityNotFoundException if no reader with the given id was found.
     */
    @Override
    public ReaderDO find(Long readerId) throws EntityNotFoundException
    {
        return findReaderChecked(readerId);
    }


    /**
     * Creates a new reader.
     *
     * @param readerDO
     * @return
     * @throws ConstraintsViolationException if a reader already exists with the given username, ... .
     */
    @Override
    public ReaderDO create(ReaderDO readerDO) throws ConstraintsViolationException
    {
        ReaderDO reader;
        try
        {
            reader = readerRepository.save(readerDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("Some constraints are thrown due to reader creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return reader;
    }


    /**
     * Deletes an existing reader by id.
     *
     * @param readerId
     * @throws EntityNotFoundException if no reader with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long readerId) throws EntityNotFoundException
    {
        ReaderDO readerDO = findReaderChecked(readerId);
        readerDO.setDeleted(true);
    }


    /**
     * Update the location for a reader.
     *
     * @param readerId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long readerId, double longitude, double latitude) throws EntityNotFoundException
    {
        ReaderDO readerDO = findReaderChecked(readerId);
        readerDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all readers by online state.
     *
     * @param readerStatus
     */
    @Override
    public List<ReaderDO> find(ReaderStatus readerStatus)
    {
        return readerRepository.findByReaderStatus(readerStatus);
    }


    private ReaderDO findReaderChecked(Long readerId) throws EntityNotFoundException
    {
        ReaderDO readerDO = readerRepository.findOne(readerId);
        if (readerDO == null)
        {
            throw new EntityNotFoundException("Could not find entity with id: " + readerId);
        }
        return readerDO;
    }


    /**
     * Getting all books retrieved by reader
     * 
     */
    @Override
    public List<BookDO> findAllBooks()
    {
        LOG.debug("Start searching for all books ...");
        List<BookDO> books = (List<BookDO>) bookRepository.findAll();
        LOG.debug("Number of books retrieved by author: " + books != null ? books.size() + "" : "null");
        return books;
    }


    /**
     * update the book entity to be selected by a reader
     * 
     * @param readerId
     * @param bookId
     * @throws EntityNotFoundException
     * @throws BookFunctionalException
     * @throws ReaderFunctionalException 
     * 
     */
    @Override
    @Transactional
    public void selectBook(long readerId, long bookId)
        throws EntityNotFoundException, BookAlreadyInUseException, ReaderFunctionalException
    {

        LOG.debug("Start selecting book " + bookId + " for the reader " + readerId);

        BookDO bookDO = findBookChecked(bookId);
        if (bookDO.isSelected())
        {
            LOG.error("Book has been selected before by another reader");
            throw new BookAlreadyInUseException("The book you chose has been selected before!");
        }

        LOG.debug("Start retrieving reader details from DB ...");
        ReaderDO readerDO = findReaderChecked(readerId);

        if (readerDO.getReaderStatus().equals(ReaderStatus.DEACTIVATED))
        {
            LOG.error("reader is in deactivated... ");
            throw new ReaderFunctionalException("reader is deactivated and can't select book");
        }

        LOG.debug("Start assiging the book: " + bookDO + " to the reader: " + readerDO);

        bookDO.setSelected(true); //Setting that the book is now selected
        readerDO.setBookDO(bookDO);

        readerRepository.save(readerDO);

        LOG.debug("book has been assigned to the reader successfully...");

    }


    /**
     * A healthy check for book entity
     * 
     * @param bookId
     * @return
     * @throws EntityNotFoundException
     */
    private BookDO findBookChecked(Long bookId) throws EntityNotFoundException
    {
        BookDO bookDO = this.bookRepository.findOne(bookId);

        if (bookDO == null)
        {
            throw new EntityNotFoundException("Could not find entity with id: " + bookId);
        }

        return bookDO;
    }


    /**
     * update the book entity by removing the reader selected to it
     * 
     * @param readerId
     * @param bookId
     * @throws {@link EntityNotFoundException}
     * @throws {@link BookFunctionalException}
     * @throws {@link ReaderFunctionalException}
     * 
     */
    @Override
    public void deselectBook(long readerId, long bookId)
        throws EntityNotFoundException, BookFunctionalException, ReaderFunctionalException
    {

        LOG.debug("Start deselecting book " + bookId + " for the reader " + readerId);

        BookDO bookDO = findBookChecked(bookId);
        if (!bookDO.isSelected())
        {
            LOG.error("Book is not selected by another reader");
            throw new BookFunctionalException("The book you chose hasnot been selected before!");
        }

        LOG.debug("Start retrieving reader details from DB ...");
        ReaderDO readerDO = findReaderChecked(readerId);

        if (readerDO.getBookDO() == null || !(readerDO.getBookDO().getId().equals(bookId)))
        {
            LOG.error("Reader is not the one assigned to book");
            throw new ReaderFunctionalException("Reader is not the one assigned to book");
        }

        bookDO.setSelected(false); //marking the book as not selected
        readerDO.setBookDO(bookDO);

        readerRepository.save(readerDO);

        LOG.debug("Book has been deselected from the reader successfully...");

    }


    /**
     * A web service to get all readers assigned to books
     * 
     */
    @Override
    public List<ReaderDO> findReadersWithBooks()
    {
        LOG.debug("Start retrieving readers with assigned books");
        List<ReaderDO> readers = (List<ReaderDO>) this.readerRepository.findAll();

        return readers.stream().filter(d -> d.getBookDO() != null).collect(Collectors.toList());
    }


    @Override
    public List<ReaderDO> findReadersWithBookStatus(BookStatus et)
    {
        LOG.debug("Start getting reader with book status");
        List<ReaderDO> readers = (List<ReaderDO>) this.readerRepository.findByBookDO_BookStatus(et);
        LOG.debug("Getting readers with book status ended successfully");
        return readers;
    }

}
