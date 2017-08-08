package com.library.datatransferobject;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 
 * This Entity is built to be a Author transfer 
 * object from/to JSON object
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorDTO
{

    @JsonIgnore
    private Long id;

    @NotNull(message = "service plate can not be null!")
    private String name;

    private String description;


    public AuthorDTO(Long id, String name, String description)
    {
        this.id = id;
        this.name = name;
        this.description = description;
    }


    private AuthorDTO()
    {

    }


    public static AuthorDTOBuilder newBuilder()
    {
        return new AuthorDTOBuilder();
    }


    @JsonProperty
    public Long getId()
    {
        return id;
    }


    public String getName()
    {
        return name;
    }


    public String getDescription()
    {
        return description;
    }

    public static class AuthorDTOBuilder
    {
        private Long id;
        private String name;
        private String description;


        public AuthorDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public AuthorDTOBuilder setName(String name)
        {
            this.name = name;
            return this;
        }


        public AuthorDTOBuilder setDescription(String description)
        {
            this.description = description;
            return this;
        }


        public AuthorDTO createAuthorDTO()
        {

            return new AuthorDTO(id, name, description);
        }

    }

}
