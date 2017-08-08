package com.library.dataaccessobject;

import org.springframework.data.repository.CrudRepository;

import com.library.domainobject.BookDO;


public interface BookRepository extends CrudRepository<BookDO, Long>{

}
