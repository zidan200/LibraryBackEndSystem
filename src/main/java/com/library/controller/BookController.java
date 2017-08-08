package com.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.controller.mapper.BookMapper;
import com.library.datatransferobject.BookDTO;
import com.library.exception.EntityNotFoundException;
import com.library.service.book.BookService;

/**
 * All book operations will be in this controller.
 * 
 */
@RestController
@RequestMapping("v1/books")
public class BookController
{

    @Autowired
    private BookService bookService;


    /**
     * The web service is used to get book by bookId
     * 
     * @return List<BookDTO>
     * @throws EntityNotFoundException
     */
    @GetMapping
    public List<BookDTO> getBooks() throws EntityNotFoundException
    {
        return BookMapper.makebookDTOList(bookService.findAll());
    }

}
