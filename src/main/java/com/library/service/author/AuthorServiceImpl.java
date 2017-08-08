package com.library.service.author;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.library.dataaccessobject.AuthorRepository;
import com.library.domainobject.BookDO;
import com.library.exception.EntityNotFoundException;
import com.library.domainobject.AuthorDO;

@Service
public class AuthorServiceImpl implements AuthorService
{

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(AuthorServiceImpl.class);

    private final AuthorRepository authorRepository;


    public AuthorServiceImpl(AuthorRepository authorRepository)
    {
        this.authorRepository = authorRepository;
    }


    /**
     * This is a method to get author by his ID
     * 
     * @param authorId
     * @throws EntityNotFoundException
     * 
     */
    @Override
    public AuthorDO find(Long authorId) throws EntityNotFoundException
    {
        LOG.debug("Start finding Auhtor with Id: " + authorId);
        AuthorDO authorDO = findAuthorChecked(authorId);
        return authorDO;
    }


    /**
     * Healthy check method after retrieving the authorDO
     * 
     * @param authorId
     * @return
     * @throws EntityNotFoundException
     */
    private AuthorDO findAuthorChecked(Long authorId) throws EntityNotFoundException
    {
        AuthorDO authorDO = this.authorRepository.findOne(authorId);

        if (authorDO == null)
        {
            LOG.error("No Entity found for id: " + authorId);
            throw new EntityNotFoundException("Could not find entity with id: " + authorId);
        }
        LOG.debug("Author retrieved from DB");
        return authorDO;
    }


    /**
     * Getting all books by their Authors
     * 
     * @param authorId
     * @return
     * @throws EntityNotFoundException
     */
    @Override
    public List<BookDO> findBookByAuthorId(Long authorId) throws EntityNotFoundException
    {

        LOG.debug("Start searching for books by authorId: " + authorId);

        AuthorDO authorDO = findAuthorChecked(authorId);
        List<BookDO> books = authorDO != null ? authorDO.getBooks() : null;

        LOG.debug("Number of books retrieved by authors: " + books != null ? books.size() + "" : "null");
        return books;
    }


    /**
     * updating the list of books the author has with the JSON request sent
     * 
     * @param bookDos, authorId
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateBook(long authorId, List<BookDO> bookDos) throws EntityNotFoundException
    {
        LOG.debug("Start updating books for author: " + authorId);

        AuthorDO authorDO = findAuthorChecked(authorId);
        authorDO.setBooks(bookDos);

        this.authorRepository.save(authorDO);
        LOG.debug("Updating books ended successfully");
    }


    /**
     * Giving the ability for theauthor to create a new book and add it to his list
     * 
     * @param bookDO, authorId
     * @throws EntityNotFoundException
     * 
     */
    @Override
    @Transactional
    public List<BookDO> create(long authorId, BookDO bookDO) throws EntityNotFoundException
    {

        LOG.debug("Start creating book for author: " + authorId);

        AuthorDO auhtorDO = findAuthorChecked(authorId);

        bookDO.setAuthor(auhtorDO);
        auhtorDO.getBooks().add(bookDO);

        this.authorRepository.save(auhtorDO);

        LOG.debug("Book created successfully");
        AuthorDO authorDO2 = findAuthorChecked(authorId);

        return authorDO2.getBooks();
    }

}
