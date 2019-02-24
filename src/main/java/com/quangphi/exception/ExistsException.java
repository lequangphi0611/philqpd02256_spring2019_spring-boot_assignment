package com.quangphi.exception;

public class ExistsException extends Error{

	private static final long serialVersionUID = 1050328263791789458L;

	public ExistsException(String message) {
        super(message);
    }
    
}