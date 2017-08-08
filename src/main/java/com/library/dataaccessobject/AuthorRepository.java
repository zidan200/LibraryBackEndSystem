package com.library.dataaccessobject;

import org.springframework.data.repository.CrudRepository;

import com.library.domainobject.AuthorDO;


public interface AuthorRepository extends CrudRepository<AuthorDO, Long>{

    AuthorDO findByName(String name);

	
}
