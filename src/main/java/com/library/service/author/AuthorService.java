package com.library.service.author;

import java.util.List;

import com.library.domainobject.BookDO;
import com.library.exception.EntityNotFoundException;
import com.library.domainobject.AuthorDO;

public interface AuthorService {
	
	/**
	 * 
	 * @param auhtorId
	 * @return
	 * @throws EntityNotFoundException
	 */
	AuthorDO find(Long auhtorId) throws EntityNotFoundException;

	/**
	 * 
	 * @param authorId
	 * @return
	 * @throws EntityNotFoundException
	 */
	List<BookDO> findBookByAuthorId(Long authorId) throws EntityNotFoundException;
	
	/**
	 * 
	 * @param authorId
	 * @param bookDO
	 * @throws EntityNotFoundException
	 */
	void updateBook(long authorId, List<BookDO> bookDO) throws EntityNotFoundException;
	
	/**
	 * 
	 * @param authorId
	 * @param bookDO
	 * @return
	 * @throws EntityNotFoundException
	 */
	List<BookDO> create(long authorId, BookDO bookDO) throws EntityNotFoundException;

}
