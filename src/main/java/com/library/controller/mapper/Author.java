package com.library.controller.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.library.datatransferobject.AuthorDTO;
import com.library.domainobject.AuthorDO;

public class Author
{

    /**
     * Building DTO to be send in response
     * @param authorDO
     * @return
     */
    public static AuthorDTO makeAuthorDTO(AuthorDO authorDO)
    {
        AuthorDTO.AuthorDTOBuilder authorDTOBuilder = AuthorDTO.newBuilder().setId(authorDO.getId())
            .setName(authorDO.getName()).setDescription(authorDO.getDescription());

        return authorDTOBuilder.createAuthorDTO();
    }


    /**
     * Building DTO List to be sent in repsonse
     * @param author
     * @return
     */
    public static List<AuthorDTO> makeAuthorDTOList(Collection<AuthorDO> author)
    {
        return author.stream().map(Author::makeAuthorDTO).collect(Collectors.toList());
    }

}
