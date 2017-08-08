package com.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A generic Functional Exception for Book Entity
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "A functional exception related to book is thrown")
public class BookFunctionalException extends Exception
{

	private static final long serialVersionUID = 8446181365822690402L;

	public BookFunctionalException(String message)
    {
        super(message);
    }

}
