package net.whitewolfdoge.customwhitelist;

@SuppressWarnings("serial")
public class InvalidCWLEUTypeException extends Exception{

	public InvalidCWLEUTypeException(){
		//
	}

	public InvalidCWLEUTypeException(String arg0){
		super(arg0);
	}

	public InvalidCWLEUTypeException(Throwable arg0){
		super(arg0);
	}

	public InvalidCWLEUTypeException(String arg0, Throwable arg1){
		super(arg0, arg1);
	}

	public InvalidCWLEUTypeException(String arg0, Throwable arg1, boolean arg2, boolean arg3){
		super(arg0, arg1, arg2, arg3);
	}
}
