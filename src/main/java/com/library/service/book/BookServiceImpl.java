package com.library.service.book;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.library.dataaccessobject.BookRepository;
import com.library.domainobject.BookDO;
import com.library.exception.EntityNotFoundException;

@Service
public class BookServiceImpl implements BookService
{

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;


    public BookServiceImpl(final BookRepository bookRepository)
    {
        this.bookRepository = bookRepository;
    }


    @Override
    public List<BookDO> findAll() throws EntityNotFoundException
    {
        LOG.debug("Start retieving all books ...");
        List<BookDO> bookDOs = (List<BookDO>) this.bookRepository.findAll();

        if (bookDOs == null || bookDOs.size() <= 0)
        {
            LOG.error("No books found in DB.");
            throw new EntityNotFoundException("Could not find any book in DB");
        }

        LOG.debug("Filtering the books by selected only");
        //getting all selected books as this service will be triggered by ordinary users
        bookDOs.stream().filter(c -> c.isSelected());

        LOG.debug("Retrieving books service ended successfully");
        return bookDOs;
    }

}
