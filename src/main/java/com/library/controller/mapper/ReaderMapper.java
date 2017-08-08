package com.library.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.library.datatransferobject.ReaderDTO;
import com.library.domainobject.BookDO;
import com.library.domainobject.ReaderDO;
import com.library.domainvalue.GeoCoordinate;

public class ReaderMapper
{
    public static ReaderDO makeReaderDO(ReaderDTO readerDTO)
    {
        return new ReaderDO(readerDTO.getUsername(), readerDTO.getPassword());
    }


    public static ReaderDTO makeReaderDTO(ReaderDO readerDO)
    {
        ReaderDTO.ReaderDTOBuilder readerDTOBuilder = ReaderDTO.newBuilder()
            .setId(readerDO.getId())
            .setPassword(readerDO.getPassword())
            .setUsername(readerDO.getUsername());

        GeoCoordinate coordinate = readerDO.getCoordinate();
        if (coordinate != null)
        {
            readerDTOBuilder.setCoordinate(coordinate);
        }
        BookDO book = readerDO.getBookDO();
        if(book != null){
            
            readerDTOBuilder.setBookDTO(BookMapper.makebookDTO(book));
        }

        return readerDTOBuilder.createReaderDTO();
    }


    public static List<ReaderDTO> makeReaderDTOList(Collection<ReaderDO> reader)
    {
        return reader.stream()
            .map(ReaderMapper::makeReaderDTO)
            .collect(Collectors.toList());
    }
}
