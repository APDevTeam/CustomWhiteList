package org.minecraftairshippirates.customwhitelist;

@SuppressWarnings("serial")
public class UUIDNotFoundException extends Exception{

	public UUIDNotFoundException(){
		// Do nothing
	}

	public UUIDNotFoundException(String message){
		super(message);
	}

	public UUIDNotFoundException(Throwable cause){
		super(cause);
	}

	public UUIDNotFoundException(String message, Throwable cause){
		super(message, cause);
	}

	public UUIDNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
