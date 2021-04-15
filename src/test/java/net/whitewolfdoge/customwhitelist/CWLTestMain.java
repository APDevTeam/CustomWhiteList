package net.whitewolfdoge.customwhitelist;

import java.util.Scanner;
import java.util.UUID;

import net.whitewolfdoge.customwhitelist.util.UUIDFetcher;
import net.whitewolfdoge.customwhitelist.util.UUIDNotFoundException;
import net.whitewolfdoge.customwhitelist.util.UUIDResolverNotAvailableException;
import net.whitewolfdoge.customwhitelist.util.UsernameInvalidException;

public class CWLTestMain{
	private static Scanner sc;
	public static void main(String[] pms){
		sc = new Scanner(System.in);
		
		System.out.print("Username: ");
		String testResolveUUID = sc.nextLine();
		try{
			UUID resolvedUUID = UUIDFetcher.getUUID(testResolveUUID);
			System.out.println("Returned " + resolvedUUID.toString());
		}
		catch(UUIDResolverNotAvailableException urnaex){
			System.err.println(urnaex.getMessage());
			urnaex.printStackTrace();
		}
		catch(UUIDNotFoundException unfex){
			System.err.println(unfex.getMessage());
			unfex.printStackTrace();
		}
		catch(UsernameInvalidException uniex){
			System.err.println(uniex.getMessage());
			uniex.printStackTrace();
		}
		
		sc.close();
	}
}
