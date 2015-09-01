package org.minecraftairshippirates.customwhitelist;

@SuppressWarnings("serial")
public class InvalidCWEUTypeException extends Exception{

	public InvalidCWEUTypeException(){
		//
	}

	public InvalidCWEUTypeException(String arg0){
		super(arg0);
	}

	public InvalidCWEUTypeException(Throwable arg0){
		super(arg0);
	}

	public InvalidCWEUTypeException(String arg0, Throwable arg1){
		super(arg0, arg1);
	}

	public InvalidCWEUTypeException(String arg0, Throwable arg1, boolean arg2, boolean arg3){
		super(arg0, arg1, arg2, arg3);
	}
}
