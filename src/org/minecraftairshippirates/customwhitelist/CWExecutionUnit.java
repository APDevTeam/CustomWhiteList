package org.minecraftairshippirates.customwhitelist;

import org.bukkit.command.CommandSender;

/**
 * This class is to house all necessary components for executing a CW function
 */
public class CWExecutionUnit{
	private static final int TYPE_LIST_WITH_RESOLVE = 0,
		TYPE_ADD_USER_BY_NAME = 1,
		TYPE_REMOVE_USER_BY_NAME = 2;
	private static final int[] VALID_TYPES = {0, 1, 2};
	private final int type;
	private final CommandSender sender;
	private final String[] subCmdArgs, subCmdOpts;
	
	CWExecutionUnit(int newType, CommandSender newSender, String[] newSubCmdArgs, String[] newSubCmdOpts) throws InvalidCWCUTypeException{
		boolean typeIsValid = false;
		for(int i : VALID_TYPES){ // For every element in VALID_TYPES
			if(i == newType){ // If the current element matches newType
				typeIsValid = true; // Mark it as valid
				break; // Then exit the loop
			}
		}
		if(!typeIsValid){ // If the type is not valid
			throw new InvalidCWCUTypeException();
		}
		type = newType;
		sender = newSender;
		subCmdArgs = newSubCmdArgs;
		subCmdOpts = newSubCmdOpts;
	}
	
	// TODO Add processing methods
}
