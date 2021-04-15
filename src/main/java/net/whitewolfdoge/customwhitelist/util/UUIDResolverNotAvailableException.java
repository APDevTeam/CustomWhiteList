package net.whitewolfdoge.customwhitelist.util;

/**
 * A UUIDResolverNotAvailableException is thrown when a UUIDResolver is not available at this time.
 */
@SuppressWarnings("serial")
public class UUIDResolverNotAvailableException extends Exception{

	public UUIDResolverNotAvailableException(){
		// Do nothing
	}

	public UUIDResolverNotAvailableException(String arg0){
		super(arg0);
	}

	public UUIDResolverNotAvailableException(Throwable arg0){
		super(arg0);
	}

	public UUIDResolverNotAvailableException(String arg0, Throwable arg1){
		super(arg0, arg1);
	}

	public UUIDResolverNotAvailableException(String arg0, Throwable arg1, boolean arg2, boolean arg3){
		super(arg0, arg1, arg2, arg3);
	}
}
