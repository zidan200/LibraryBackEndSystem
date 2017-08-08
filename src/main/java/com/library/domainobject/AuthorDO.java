package com.library.domainobject;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * This is the Domain Object to the Author Entity with all it's attributes
 *
 */
@Entity
@Table(name = "author")
public class AuthorDO
{

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "username cant be null")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "password cant be null")
    private String password;

    @Column
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BookDO> books;


    public Long getId()
    {
        return id;
    }


    public void setId(Long id)
    {
        this.id = id;
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }


    public String getDescription()
    {
        return description;
    }


    public void setDescription(String description)
    {
        this.description = description;
    }


    @JsonIgnore
    public List<BookDO> getBooks()
    {
        return books;
    }


    public void setBooks(List<BookDO> books)
    {
        this.books = books;
    }


    @Override
    public String toString()
    {
        return "AuthorDO [id=" + id + ", name=" + name + ", description=" + description + "]";
    }

}
