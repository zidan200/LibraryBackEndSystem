package com.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A Generic Exception for reader entity
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "A functional exception is thrown related to reader")
public class ReaderFunctionalException extends Exception
{

	private static final long serialVersionUID = -1736806492657679220L;

	public ReaderFunctionalException(String message)
    {
        super(message);
    }

}
