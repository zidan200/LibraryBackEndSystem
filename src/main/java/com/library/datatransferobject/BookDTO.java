package com.library.datatransferobject;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.domainobject.ReaderDO;
import com.library.domainobject.AuthorDO;
import com.library.domainvalue.BookStatus;

/**
 * 
 * This Entity is built to be a BOOK transfer object from/to JSON object
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO
{

    @JsonIgnore
    private Long id;

    @NotNull(message = "service plate can not be null!")
    private String servicePlate;

    private AuthorDO author;

    @NotNull(message = "Engine type cant be null!")
    private BookStatus bookStatus;

    private ReaderDO reader;

    private Long numberOfSeats;

    private Boolean convertible;


    public BookDTO(Long id, String servicePlate, Long numberOfSeats, Boolean convertible, AuthorDO author, BookStatus engineType, ReaderDO reader)
    {

        this.id = id;
        this.servicePlate = servicePlate;
        this.author = author;
        this.bookStatus = engineType;
        this.reader = reader;
        this.numberOfSeats = numberOfSeats;
        this.convertible = convertible;
    }


    private BookDTO()
    {

    }


    public static BookDTOBuilder newBuilder()
    {
        return new BookDTOBuilder();
    }


    @JsonProperty
    public Long getId()
    {
        return id;
    }


    public Long getNumberOfSeats()
    {
        return numberOfSeats;
    }


    public Boolean getConvertible()
    {
        return convertible;
    }


    public String getServicePlate()
    {
        return servicePlate;
    }


    public AuthorDO getAuthor()
    {
        return author;
    }


    public BookStatus getBookStatus()
    {
        return bookStatus;
    }


    public ReaderDO getReader()
    {
        return reader;
    }

    public static class BookDTOBuilder
    {
        private Long id;
        private String servicePlate;
        private AuthorDO author;
        private Long numberOfSeats;
        private BookStatus bookStatus;
        private ReaderDO reader;
        private Boolean convertible;


        public BookDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public BookDTOBuilder setNumberOfSeats(Long seats)
        {
            this.numberOfSeats = seats;
            return this;
        }


        public BookDTOBuilder setServiceplate(String servicePlate)
        {
            this.servicePlate = servicePlate;
            return this;
        }


        public BookDTOBuilder setAuthor(AuthorDO author)
        {
            this.author = author;
            return this;
        }


        public BookDTOBuilder setBookStatus(BookStatus bookStatus)
        {
            this.bookStatus = bookStatus;
            return this;
        }


        public BookDTOBuilder setReader(ReaderDO reader)
        {
            this.reader = reader;
            return this;
        }


        public BookDTOBuilder setConvertable(Boolean convertible)
        {
            this.convertible = convertible;
            return this;
        }


        public BookDTO createBookDTO()
        {
            return new BookDTO(id, servicePlate, numberOfSeats, convertible, author, bookStatus, reader);
        }

    }

}
