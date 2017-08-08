package com.library.service.reader;

import java.util.Collection;
import java.util.List;

import com.library.domainobject.BookDO;
import com.library.domainobject.ReaderDO;
import com.library.domainvalue.BookStatus;
import com.library.domainvalue.ReaderStatus;
import com.library.exception.BookAlreadyInUseException;
import com.library.exception.BookFunctionalException;
import com.library.exception.ConstraintsViolationException;
import com.library.exception.ReaderFunctionalException;
import com.library.exception.EntityNotFoundException;

public interface ReaderService
{

    ReaderDO find(Long readerId) throws EntityNotFoundException;

    ReaderDO create(ReaderDO readerDO) throws ConstraintsViolationException;

    void delete(Long readerId) throws EntityNotFoundException;

    void updateLocation(long readerId, double longitude, double latitude) throws EntityNotFoundException;

    List<ReaderDO> find(ReaderStatus onlineStatus);

    /**
     * 
     * @return
     */
	List<BookDO> findAllBooks();

	/**
	 * 
	 * @param readerId
	 * @param bookId
	 * @throws EntityNotFoundException
	 * @throws ReaderFunctionalException
	 * @throws BookAlreadyInUseException
	 */
	void selectBook(long readerId, long bookId) 
			throws EntityNotFoundException, ReaderFunctionalException, BookAlreadyInUseException;

	/**
	 * 
	 * @param readerId
	 * @param bookId
	 * @throws EntityNotFoundException
	 * @throws BookFunctionalException
	 * @throws ReaderFunctionalException
	 */
	void deselectBook(long readerId, long bookId)
			throws EntityNotFoundException, BookFunctionalException, ReaderFunctionalException;

	List<ReaderDO> findReadersWithBooks();

	/**
	 * 
	 * @return
	 */
    List<ReaderDO> findReadersWithBookStatus(BookStatus et);

}
