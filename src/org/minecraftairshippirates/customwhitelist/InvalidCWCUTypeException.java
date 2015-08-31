package org.minecraftairshippirates.customwhitelist;

@SuppressWarnings("serial")
public class InvalidCWCUTypeException extends Exception{

	public InvalidCWCUTypeException(){
		//
	}

	public InvalidCWCUTypeException(String arg0){
		super(arg0);
	}

	public InvalidCWCUTypeException(Throwable arg0){
		super(arg0);
	}

	public InvalidCWCUTypeException(String arg0, Throwable arg1){
		super(arg0, arg1);
	}

	public InvalidCWCUTypeException(String arg0, Throwable arg1, boolean arg2, boolean arg3){
		super(arg0, arg1, arg2, arg3);
	}
}
