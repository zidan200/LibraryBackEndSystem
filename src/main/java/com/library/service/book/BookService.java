package com.library.service.book;

import java.util.List;

import com.library.domainobject.BookDO;
import com.library.exception.EntityNotFoundException;

public interface BookService {
	
	/**
	 * 
	 * @return
	 * @throws EntityNotFoundException
	 */
	List<BookDO> findAll() throws EntityNotFoundException;

}
