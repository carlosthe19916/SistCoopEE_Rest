package org.softgreen.sistcoop.organizacion.restapi.config;

import java.io.Serializable;

public class JError implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private String message;

	public static JError getJError(String message) {
		JError jsend = new JError();
		jsend.setMessage(message);
		
		return jsend;
	}

	private JError() {
		// TODO Auto-generated constructor stub
	}	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
