package com.library.domainobject;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.format.annotation.DateTimeFormat;

import com.library.domainvalue.GeoCoordinate;
import com.library.domainvalue.ReaderStatus;

@Entity
@Table(
    name = "reader",
    uniqueConstraints = @UniqueConstraint(name = "uc_username", columnNames = {"username"}))
public class ReaderDO
{

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(nullable = false)
    @NotNull(message = "Username can not be null!")
    private String username;

    @Column(nullable = false)
    @NotNull(message = "Password can not be null!")
    private String password;

    @Column(nullable = false)
    private Boolean deleted = false;

    @Embedded
    private GeoCoordinate coordinate;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCoordinateUpdated = ZonedDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReaderStatus readerStatus;

    @OneToOne
    @JoinColumn(name = "BOOK_ID")
    private BookDO bookDO;


    private ReaderDO()
    {}


    public ReaderDO(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.deleted = false;
        this.coordinate = null;
        this.dateCoordinateUpdated = null;
        this.readerStatus = ReaderStatus.DEACTIVATED;
        this.bookDO = null;
    }


    public Long getId()
    {
        return id;
    }


    public void setId(Long id)
    {
        this.id = id;
    }


    public String getUsername()
    {
        return username;
    }


    public String getPassword()
    {
        return password;
    }


    public Boolean getDeleted()
    {
        return deleted;
    }


    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }


    public ReaderStatus getReaderStatus()
    {
        return readerStatus;
    }


    public void setReaderStatus(ReaderStatus onlineStatus)
    {
        this.readerStatus = onlineStatus;
    }


    public GeoCoordinate getCoordinate()
    {
        return coordinate;
    }


    public void setCoordinate(GeoCoordinate coordinate)
    {
        this.coordinate = coordinate;
        this.dateCoordinateUpdated = ZonedDateTime.now();
    }


    public BookDO getBookDO()
    {
        return bookDO;
    }


    public void setBookDO(BookDO bookDO)
    {
        this.bookDO = bookDO;
    }


    @Override
    public String toString()
    {
        return "ReaderDO [id=" + id + ", dateCreated=" + dateCreated + ", username=" + username + ", password="
            + password + ", deleted=" + deleted + ", coordinate=" + coordinate + ", dateCoordinateUpdated="
            + dateCoordinateUpdated + ", readerStatus=" + readerStatus + ", bookDO=" + bookDO + "]";
    }

}
