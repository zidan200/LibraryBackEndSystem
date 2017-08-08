package com.library.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.library.controller.mapper.ReaderMapper;
import com.library.datatransferobject.BookDTO;
import com.library.datatransferobject.ReaderDTO;
import com.library.domainobject.ReaderDO;
import com.library.domainvalue.BookStatus;
import com.library.domainvalue.ReaderStatus;
import com.library.exception.BookAlreadyInUseException;
import com.library.exception.BookFunctionalException;
import com.library.exception.ConstraintsViolationException;
import com.library.exception.ReaderFunctionalException;
import com.library.service.reader.ReaderService;
import com.library.exception.EntityNotFoundException;

/**
 * All operations with a reader will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/readers")
public class ReaderController
{

    private final ReaderService readerService;


    @Autowired
    public ReaderController(final ReaderService readerService)
    {
        this.readerService = readerService;
    }


    @GetMapping("/{readerId}")
    @Secured({ "ROLE_READER" , "ROLE_ADMIN" })
    public ReaderDTO getReader(@Valid @PathVariable long readerId) throws EntityNotFoundException
    {
        return ReaderMapper.makeReaderDTO(readerService.find(readerId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({ "ROLE_ADMIN" })
    public ReaderDTO createReader(@Valid @RequestBody ReaderDTO readerDTO) throws ConstraintsViolationException
    {
        ReaderDO readerDO = ReaderMapper.makeReaderDO(readerDTO);
        return ReaderMapper.makeReaderDTO(readerService.create(readerDO));
    }


    @DeleteMapping("/{readerId}")
    @Secured({ "ROLE_ADMIN" })
    public void deleteReader(@Valid @PathVariable long readerId) throws EntityNotFoundException
    {
        readerService.delete(readerId);
    }


    @PutMapping("/{readerId}")
    @Secured({ "ROLE_READER" , "ROLE_ADMIN" })
    public void updateLocation(
        @Valid @PathVariable long readerId, @RequestParam double longitude, @RequestParam double latitude)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        readerService.updateLocation(readerId, longitude, latitude);
    }


    @GetMapping
    @Secured({ "ROLE_ADMIN" })
    public List<ReaderDTO> findReaders(@RequestParam ReaderStatus onlineStatus)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        return ReaderMapper.makeReaderDTOList(readerService.find(onlineStatus));
    }


    /**
     * Retrieve all books to be shown to the reader
     * 
     * @return List<bookDTO>
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException
     */
    @GetMapping("/books")
    @Secured({ "ROLE_READER" , "ROLE_ADMIN" })
    public List<BookDTO> findBooks()
        throws ConstraintsViolationException, EntityNotFoundException
    {
        return BookMapper.makebookDTOList(readerService.findAllBooks());
    }


    /**
     * Select a book by the reader
     * 
     * @param readerId
     * @param bookId
     * @throws EntityNotFoundException
     * @throws BookAlreadyInUseException
     * @throws ReaderFunctionalException 
     */
    @PutMapping("/selectbook/{readerId}")
    @Secured({ "ROLE_READER" , "ROLE_ADMIN"})
    public void selectBook(
        @Valid @PathVariable long readerId, @RequestParam long bookId)
        throws EntityNotFoundException, BookAlreadyInUseException, ReaderFunctionalException
    {
        readerService.selectBook(readerId, bookId);
    }


    /**
     * Deselect a book by the reader
     * 
     * @param readerId
     * @param bookId
     * @throws EntityNotFoundException
     * @throws BookFunctionalException
     * @throws ReaderFunctionalException 
     */
    @PutMapping("/deselectbook/{readerId}")
    @Secured({ "ROLE_READER" , "ROLE_ADMIN"})
    public void deselectBook(
        @Valid @PathVariable long readerId, @RequestParam long bookId)
        throws EntityNotFoundException, BookFunctionalException, ReaderFunctionalException
    {
        readerService.deselectBook(readerId, bookId);
    }


    /**
     * getting all readers assigned to books
     * 
     * @return
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException
     */

    @GetMapping("/assignedReaders")
    @Secured({ "ROLE_ADMIN" })
    public List<ReaderDTO> findReadersWithbooks()
        throws ConstraintsViolationException, EntityNotFoundException
    {
        return ReaderMapper.makeReaderDTOList(readerService.findReadersWithBooks());
    }

    /**
     * Getting all readers with selected books
     * 
     * @return
     * @throws ConstraintsViolationException
     * @throws EntityNotFoundException
     */
    @GetMapping("/bookTypes")
    @Secured({ "ROLE_ADMIN" })
    public List<ReaderDTO> findReadersWithbooks(@RequestParam BookStatus et)
        throws ConstraintsViolationException, EntityNotFoundException
    {
        return ReaderMapper.makeReaderDTOList(readerService.findReadersWithBookStatus(et));
    }
}
