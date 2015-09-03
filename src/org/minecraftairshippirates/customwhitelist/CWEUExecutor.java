package org.minecraftairshippirates.customwhitelist;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class CWEUExecutor{
	private final LinkedList<CWExecutionUnit> cweuQueue;
	
	CWEUExecutor(){
		cweuQueue = new LinkedList<CWExecutionUnit>();
	}
	
	public synchronized boolean add(CWExecutionUnit newCWEU) throws IllegalStateException{
		return cweuQueue.add(newCWEU);
	}
	
	public synchronized CWExecutionUnit remove() throws NoSuchElementException{
		return cweuQueue.remove();
	}
	
	public synchronized CWExecutionUnit element() throws NoSuchElementException{
		return cweuQueue.peek();
	}
}
