package com.library;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.library.LibraryServerApplicantTestApplication;
import com.library.controller.BookController;
import com.library.controller.ReaderController;
import com.library.controller.HomeController;
import com.library.controller.AuthorController;
import com.library.dataaccessobject.BookRepository;
import com.library.dataaccessobject.ReaderRepository;
import com.library.dataaccessobject.AuthorRepository;
import com.library.datatransferobject.BookDTO;
import com.library.datatransferobject.ReaderDTO;
import com.library.domainvalue.ReaderStatus;
import com.library.exception.BookAlreadyInUseException;
import com.library.exception.BookFunctionalException;
import com.library.exception.ConstraintsViolationException;
import com.library.exception.ReaderFunctionalException;
import com.library.exception.EntityNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LibraryServerApplicantTestApplication.class)
@ActiveProfiles("test")
public class MytaxiServerApplicantTestApplicationTests
{

    @Autowired
    private HomeController controller;

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    private ReaderRepository readerRepo;


    @Test
    public void contextLoads()
    {
        assertThat(controller).isNotNull();
    }

    //---------------------------------------------------------------
    // start testing the books module
    //----------------------------------------------------------------
    @Autowired
    private BookController bookController;


    @SuppressWarnings("deprecation")
    @Test
    public void getbooks() throws Exception
    {
        List<BookDTO> books = bookController.getBooks();
        assertThat(books).isNotNull();
    }

    //---------------------------------------------------------------
    // start testing the readers module
    //----------------------------------------------------------------
    @Autowired
    private ReaderController readerController;


    @Test
    public void createreader() throws ConstraintsViolationException
    {
        
        ReaderDTO.ReaderDTOBuilder readerDTOBuilder = ReaderDTO.newBuilder()
            .setId(1L)
            .setPassword("pass")
            .setUsername("myreader");
        ReaderDTO result = readerController.createReader(readerDTOBuilder.createReaderDTO());

        assertThat(result.getUsername()).contains("myreader");
    }


    @Test
    public void geteReaders() throws ConstraintsViolationException, EntityNotFoundException
    {
        List<ReaderDTO> readers = readerController.findReaders(ReaderStatus.DEACTIVATED);
        assertThat(readers.size()).isEqualTo(5);
    }


    @Test
    @Rollback
    public void selectBookToReader() throws BookAlreadyInUseException, EntityNotFoundException, ReaderFunctionalException
    {
        readerController.selectBook(4L, 2L);
        List<BookDTO> books = bookController.getBooks();
        assertThat(books).isNotNull();
    }


    //Failure TC
    @Test
    public void selectBookToOfflineReader() throws BookAlreadyInUseException, EntityNotFoundException
    {
        try
        {
            readerController.selectBook(1L, 1L);
        }
        catch (ReaderFunctionalException e)
        {
            assertThat(e.getMessage()).contains("reader is deactivated and can't select book");
            e.printStackTrace();
        }

    }


    //Failure TC
    @Test
    @Rollback
    public void selectAselctedBookToReader() throws EntityNotFoundException, ReaderFunctionalException
    {
        try
        {
            readerController.selectBook(4L, 1L);
            readerController.selectBook(5L, 1L);
        }
        catch (BookAlreadyInUseException e)
        {
            assertThat(e.getMessage()).contains("The book you chose has been selected before!");
            e.printStackTrace();
        }
    }


    @Test
    public void deselectBookFromReader() throws BookAlreadyInUseException, EntityNotFoundException, ReaderFunctionalException
    {
        readerController.selectBook(5L, 3L);
        List<BookDTO> books = bookController.getBooks();
        assertThat(books).isNotNull();

        try
        {
            readerController.deselectBook(5L, 3L);
        }
        catch (BookFunctionalException e)
        {

        }
    }

    //---------------------------------------------------------------
    // start testing the manfacturer module
    //----------------------------------------------------------------
    @Autowired
    private AuthorController authorController;


    @Test
    public void getAuthor()
    {
        try
        {
        	authorController.getAuthor(5L);
        }
        catch (EntityNotFoundException e)
        {
            assertThat(e.getMessage()).contains("Could not find entity with id:");
            e.printStackTrace();
        }
    }
}
