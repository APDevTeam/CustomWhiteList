package net.whitewolfdoge.customwhitelist;

@SuppressWarnings("serial")
public class UsernameNotFoundException extends Exception{

	public UsernameNotFoundException(){
		// Do nothing
	}

	public UsernameNotFoundException(String arg0){
		super(arg0);
	}

	public UsernameNotFoundException(Throwable arg0){
		super(arg0);
	}

	public UsernameNotFoundException(String arg0, Throwable arg1){
		super(arg0, arg1);
	}

	public UsernameNotFoundException(String arg0, Throwable arg1, boolean arg2, boolean arg3){
		super(arg0, arg1, arg2, arg3);
	}
}
