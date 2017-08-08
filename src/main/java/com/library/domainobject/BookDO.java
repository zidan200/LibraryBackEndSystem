package com.library.domainobject;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.library.domainvalue.BookStatus;
import com.library.domainvalue.RatingType;

/**
 * This is the Domain Object to the Book Entity with all it's attributes
 */
@Entity
@Table(name = "book")
public class BookDO
{

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateManfactured = null;

    @Column(nullable = false)
    private Boolean convertible = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Engine type cant be null !")
    private BookStatus bookStatus;

    @Column(nullable = false)
    @NotNull(message = "License plate cant be null !")
    private String licensePlate;

    @Column(nullable = false)
    private Long seatCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private RatingType rating;

    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    private AuthorDO author;

    @Column(nullable = false)
    private boolean isSelected;


    private BookDO()
    {}


    public BookDO(ZonedDateTime dateManfactured, BookStatus engineType, String licensePlate, Long seatCount)
    {

        this.dateManfactured = dateManfactured;
        this.bookStatus = engineType;
        this.licensePlate = licensePlate;
        this.convertible = false;
        this.seatCount = seatCount;
        this.rating = RatingType.NO_RATING;
        this.author = null;
    }


    public Long getId()
    {
        return id;
    }


    public void setId(Long id)
    {
        this.id = id;
    }


    public ZonedDateTime getDateManfactured()
    {
        return dateManfactured;
    }


    public void setDateManfactured(ZonedDateTime dateManfactured)
    {
        this.dateManfactured = dateManfactured;
    }


    public Boolean getConvertible()
    {
        return convertible;
    }


    public void setConvertible(Boolean convertible)
    {
        this.convertible = convertible;
    }


    public BookStatus getBookStatus()
    {
        return bookStatus;
    }


    public void setEngineType(BookStatus bookStatus)
    {
        this.bookStatus = bookStatus;
    }


    public String getLicensePlate()
    {
        return licensePlate;
    }


    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }


    public Long getSeatCount()
    {
        return seatCount;
    }


    public void setSeatCount(Long seatCount)
    {
        this.seatCount = seatCount;
    }


    public RatingType getRating()
    {
        return rating;
    }


    public void setRating(RatingType rating)
    {
        this.rating = rating;
    }


    public AuthorDO getAuthor()
    {
        return author;
    }


    public void setAuthor(AuthorDO auhtor)
    {
        this.author = auhtor;
    }


    public boolean isSelected()
    {
        return isSelected;
    }


    public void setSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }


    @Override
    public String toString()
    {
        return "BookDO [id=" + id + ", dateManfactured=" + dateManfactured + ", convertible=" + convertible
            + ", engine_type=" + bookStatus + ", license_plate=" + licensePlate + ", seat_count=" + seatCount
            + ", rating=" + rating + ", author=" + author;
    }

}
