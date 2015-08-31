package org.minecraftairshippirates.customwhitelist;

import org.bukkit.command.CommandSender;

/**
 * This class is to house all necessary components for executing a CW function
 */
public class CWExecutionUnit{
	private static final int TYPE_ADD_USER_BY_NAME = 0,
		TYPE_ADD_USER_BY_UUID = 1,
		TYPE_REMOVE_USER_BY_NAME = 2,
		TYPE_REMOVE_USER_BY_UUID = 3,
		TYPE_LIST_WITHOUT_RESOLVE = 4,
		TYPE_LIST_WITH_RESOLVE = 5;
		
	private static final int[] VALID_TYPES = {0, 1, 2, 3, 4, 5};
	private final int type;
	private final CommandSender sender;
	private final String[] subCmdArgs, subCmdOpts;
	
	CWExecutionUnit(int newType, CommandSender newSender, String[] newSubCmdArgs, String[] newSubCmdOpts) throws InvalidCWEUTypeException{
		boolean typeIsValid = false;
		for(int i : VALID_TYPES){ // For every element in VALID_TYPES
			if(i == newType){ // If the current element matches newType
				typeIsValid = true; // Mark it as valid
				break; // Then exit the loop
			}
		}
		if(!typeIsValid){ // If the type is not valid
			throw new InvalidCWEUTypeException();
		}
		type = newType;
		sender = newSender;
		subCmdArgs = newSubCmdArgs;
		subCmdOpts = newSubCmdOpts;
	}
	
	public void process(){
		// TODO Add processing blocks for all types
	}
}
