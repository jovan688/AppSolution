package com.comunicator;
import java.lang.Exception;

public  class ComunicatorInvalidException extends 	Exception {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ComunicatorInvalidException() { super(); }
	public ComunicatorInvalidException(String message) { super(message); }
	public ComunicatorInvalidException(String message, Throwable cause) { super(message, cause); }
	public ComunicatorInvalidException(Throwable cause) { super(cause); }
}