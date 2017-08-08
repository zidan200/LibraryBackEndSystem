package com.library.security.dataaccessobject;

import org.springframework.data.repository.CrudRepository;

import com.library.security.domainobject.UserDO;

/**
 * Database Access Object for users table.
 * <p/>
 */
public interface UserRepository extends CrudRepository<UserDO, Long>
{

    UserDO findByUsername(String username);

}
