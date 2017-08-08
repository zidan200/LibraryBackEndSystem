package com.library.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.library.controller.mapper.BookMapper;
import com.library.controller.mapper.Author;
import com.library.datatransferobject.BookDTO;
import com.library.datatransferobject.AuthorDTO;
import com.library.domainobject.BookDO;
import com.library.exception.ConstraintsViolationException;
import com.library.exception.EntityNotFoundException;
import com.library.service.author.AuthorService;

/**
 * All book operations will be in this controller.
 * 
 */
@RestController
@RequestMapping("v1/author")
public class AuthorController
{

    @Autowired
    private AuthorService authorService;


    /**
     * getting AuthorDO by ID
     * 
     * @param authorId
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping("/{authorId}")
    @Secured({ "ROLE_AUTHOR" , "ROLE_ADMIN" })
    public AuthorDTO getAuthor(
        @Valid @PathVariable long authorId)
        throws EntityNotFoundException
    {
        return Author.makeAuthorDTO(authorService.find(authorId));
    }


    /**
     * Create new Manafucturer
     * 
     * @param authorId
     * @param bookDTO
     * @return
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException
     */
    @PostMapping("/{authorId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({ "ROLE_ADMIN" })
    public List<BookDTO> createBook(
        @Valid @PathVariable long authorId, @Valid @RequestBody BookDTO bookDTO)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        BookDO bookDO = BookMapper.makeBookDO(bookDTO);
        return BookMapper.makebookDTOList(authorService.create(authorId, bookDO));
    }


    /**
     * A service triggered by author to update book table 
     * 
     * @param authorId
     * @param bookDTOs
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException
     */
    @PutMapping("/{authorId}")
    @Secured({ "ROLE_AUTHOR" , "ROLE_ADMIN" })
    public void updateBook(
        @Valid @PathVariable long authorId, @Valid @RequestBody List<BookDTO> bookDTOs)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        List<BookDO> bookDO = BookMapper.makeBooksDO(bookDTOs);
        authorService.updateBook(authorId, bookDO);
    }


    /**
     * A service triggered by author to get all books made by him
     * 
     * @param authorId
     * @return
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException
     */
    @GetMapping("/books")
    @Secured({ "ROLE_AUTHOR" , "ROLE_ADMIN"})
    public List<BookDTO> findBooks(
        @RequestParam Long authorId)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        return BookMapper.makebookDTOList(authorService.findBookByAuthorId(authorId));
    }
}
