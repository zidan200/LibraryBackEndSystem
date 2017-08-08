package com.library.dataaccessobject;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.library.domainobject.ReaderDO;
import com.library.domainvalue.BookStatus;
import com.library.domainvalue.ReaderStatus;

/**
 * Database Access Object for reader table.
 * <p/>
 */
public interface ReaderRepository extends CrudRepository<ReaderDO, Long>
{

    List<ReaderDO> findByReaderStatus(ReaderStatus readerStatus);

    List<ReaderDO> findByBookDO_BookStatus(BookStatus status);

    ReaderDO findByUsername(String username);

}
