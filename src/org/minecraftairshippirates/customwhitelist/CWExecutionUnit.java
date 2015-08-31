package org.minecraftairshippirates.customwhitelist;

import org.bukkit.command.CommandSender;

/**
 * This class is to house all necessary components for executing a CW function
 */
public class CWExecutionUnit{
	private static final int TYPE_LIST_WITH_RESOLVE = 0,
		TYPE_ADD_USER_BY_NAME = 1,
		TYPE_REMOVE_USER_BY_NAME = 2;
	private final int type;
	private final CommandSender sender;
	private final String[] subCmdArgs, subCmdOpts;
	
	CWExecutionUnit(int newType, CommandSender newSender, String[] newSubCmdArgs, String[] newSubCmdOpts){
		type = newType;
		sender = newSender;
		subCmdArgs = newSubCmdArgs;
		subCmdOpts = newSubCmdOpts;
	}
}
