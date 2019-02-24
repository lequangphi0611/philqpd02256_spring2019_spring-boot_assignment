package com.quangphi.exception;

public class DepartmentNameExistsException extends Error {
	
	
	private static final long serialVersionUID = 2407670236103817559L;

	public DepartmentNameExistsException(String message) {
		super(message);
	}
	
}
