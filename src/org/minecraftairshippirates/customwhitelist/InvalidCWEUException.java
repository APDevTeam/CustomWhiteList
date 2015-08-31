package org.minecraftairshippirates.customwhitelist;

@SuppressWarnings("serial")
public class InvalidCWEUException extends Exception{

	public InvalidCWEUException(){
		//
	}

	public InvalidCWEUException(String message){
		super(message);
	}

	public InvalidCWEUException(Throwable cause){
		super(cause);
	}

	public InvalidCWEUException(String message, Throwable cause){
		super(message, cause);
	}

	public InvalidCWEUException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
