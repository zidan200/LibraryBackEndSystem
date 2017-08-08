package com.library.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.domainvalue.GeoCoordinate;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReaderDTO
{
    @JsonIgnore
    private Long id;

    @NotNull(message = "Username can not be null!")
    private String username;

    @NotNull(message = "Password can not be null!")
    private String password;

    private GeoCoordinate coordinate;

    private BookDTO bookDTO;
    
    private ReaderDTO()
    {
    }


    private ReaderDTO(Long id, String username, String password, GeoCoordinate coordinate, BookDTO bookDTO)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.coordinate = coordinate;
        this.bookDTO = bookDTO;
    }


    public static ReaderDTOBuilder newBuilder()
    {
        return new ReaderDTOBuilder();
    }


    @JsonProperty
    public Long getId()
    {
        return id;
    }


    public String getUsername()
    {
        return username;
    }


    public String getPassword()
    {
        return password;
    }


    public GeoCoordinate getCoordinate()
    {
        return coordinate;
    }

    public BookDTO getBookDTO(){
        return bookDTO;
    }
    
    public static class ReaderDTOBuilder
    {
        private Long id;
        private String username;
        private String password;
        private GeoCoordinate coordinate;
        private BookDTO bookDTO;


        public ReaderDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public ReaderDTOBuilder setUsername(String username)
        {
            this.username = username;
            return this;
        }


        public ReaderDTOBuilder setPassword(String password)
        {
            this.password = password;
            return this;
        }


        public ReaderDTOBuilder setCoordinate(GeoCoordinate coordinate)
        {
            this.coordinate = coordinate;
            return this;
        }


        public ReaderDTOBuilder setBookDTO(BookDTO bookDTO)
        {
            this.bookDTO = bookDTO;
            return this;
        }
        
        public ReaderDTO createReaderDTO()
        {
            return new ReaderDTO(id, username, password, coordinate, bookDTO);
        }


        

    }
}
