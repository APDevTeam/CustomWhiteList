package net.whitewolfdoge.customwhitelist.util;

/**
 * An UsernameInvalidException is thrown when a username has got dem issues.
 */
@SuppressWarnings("serial")
public class UsernameInvalidException extends Exception{

	public UsernameInvalidException(){
		// Do nothing
	}

	public UsernameInvalidException(String arg0){
		super(arg0);
	}

	public UsernameInvalidException(Throwable arg0){
		super(arg0);
	}

	public UsernameInvalidException(String arg0, Throwable arg1){
		super(arg0, arg1);
	}

	public UsernameInvalidException(String arg0, Throwable arg1, boolean arg2, boolean arg3){
		super(arg0, arg1, arg2, arg3);
	}
}
