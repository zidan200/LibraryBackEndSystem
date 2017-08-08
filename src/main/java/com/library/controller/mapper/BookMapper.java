package com.library.controller.mapper;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.library.datatransferobject.BookDTO;
import com.library.domainobject.BookDO;

public class BookMapper
{

    /**
     * Buildng bookDo using bookDTO send in request
     * 
     * @param bookDTO
     * @return
     */
    public static BookDO makeBookDO(BookDTO bookDTO)
    {
        return new BookDO(null, bookDTO.getBookStatus(), bookDTO.getServicePlate(), bookDTO.getNumberOfSeats());
    }


    /**
     * Building list of BookDOs using list of DTOs send in request
     * @param bookDTOs
     * @return
     */
    public static List<BookDO> makeBooksDO(List<BookDTO> bookDTOs)
    {

    	
        List<BookDO> bookDOs = new ArrayList<>();
        for (BookDTO bookDTO : bookDTOs)
        {
            bookDOs.add(new BookDO(ZonedDateTime.now(), bookDTO.getBookStatus(), bookDTO.getServicePlate(),
                bookDTO.getNumberOfSeats()));
        }
        return bookDOs;
    }


    /**
     * building DTO to be sent in response
     * @param bookDO
     * @return
     */
    public static BookDTO makebookDTO(BookDO bookDO)
    {
        BookDTO.BookDTOBuilder bookDTOBuilder = BookDTO.newBuilder()
            .setBookStatus(bookDO.getBookStatus())
            .setNumberOfSeats(bookDO.getSeatCount())
            .setConvertable(bookDO.getConvertible())
            .setServiceplate(bookDO.getLicensePlate())
            .setAuthor(bookDO.getAuthor());

        return bookDTOBuilder.createBookDTO();
    }


    /**
     * Building DTOs from BookDOs to be sent in response
     * @param books
     * @return
     */
    public static List<BookDTO> makebookDTOList(Collection<BookDO> books)
    {
        return books.stream().map(BookMapper::makebookDTO).collect(Collectors.toList());
    }

}
