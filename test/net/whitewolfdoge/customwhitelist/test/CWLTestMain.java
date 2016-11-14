package net.whitewolfdoge.customwhitelist.test;

import java.util.Scanner;
import java.util.UUID;

import net.whitewolfdoge.customwhitelist.util.UUIDFetcher;
import net.whitewolfdoge.customwhitelist.util.UUIDNotFoundException;

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
		catch(UUIDNotFoundException e){
			System.out.println("No match!");
			e.printStackTrace();
		}
		
		sc.close();
	}
}
