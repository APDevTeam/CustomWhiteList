package net.whitewolfdoge.customwhitelist;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class CWLEUExecutor{
	private final LinkedList<CWLExecutionUnit> cweuQueue;
	private volatile Executor executorThread = null;
	
	CWLEUExecutor(){
		cweuQueue = new LinkedList<CWLExecutionUnit>();
	}
	
	public synchronized boolean add(CWLExecutionUnit newCWEU) throws IllegalStateException{
		if((executorThread == null) || !executorThread.isAlive()){ // If the queue executor is not running
			// Start it
			executorThread = new Executor();
			executorThread.start();
		}
		return cweuQueue.add(newCWEU);
	}
	
	public synchronized CWLExecutionUnit remove() throws NoSuchElementException{
		return cweuQueue.remove();
	}
	
	public synchronized CWLExecutionUnit element() throws NoSuchElementException{
		return cweuQueue.peek();
	}
	
	public boolean isRunning(){
		try{
			return executorThread.isAlive();
		}
		catch(NullPointerException npex){
			return false;
		}
	}
	
	public void waitForFinish(){
		try{
			executorThread.join();
		}
		catch(InterruptedException iex){
			// Do nothing
		}
	}
	
	private class Executor extends Thread{
		@Override
		public void run(){
			while(true){
				try{
					remove().process();
				}
				catch(NoSuchElementException nseex){ // If there's nothing more to process
					break;
				}
			}
			// Thread done
		}
	}
}
