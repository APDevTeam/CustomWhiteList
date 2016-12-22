package net.whitewolfdoge.customwhitelist.util;

import java.util.UUID;

public class UUIDResolverTest{
	public static void main(String[] args){
		String username = "WhiteWolfdoge";
		
		UUID uid = null;
		try {
			uid = new NameMC_com().getUUID(username);
		}
		catch(Exception ex){
			System.err.println(ex.getMessage());
		}
		
		System.out.println(username + ": " + uid);
	}
}
