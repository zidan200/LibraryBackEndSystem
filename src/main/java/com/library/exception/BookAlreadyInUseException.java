package com.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An Functional Exception class used to show that 
 * A Book is in use now
 * 
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Book is already in use by another Reader")
public class BookAlreadyInUseException extends BookFunctionalException
{

	private static final long serialVersionUID = 3859780152447089226L;

	public BookAlreadyInUseException(String message)
    {
        super(message);
    }

}
