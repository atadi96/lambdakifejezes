package lambdakifejezes.Polling;

import eu.loxon.centralcontrol.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.loxon.centralcontrol.IsMyTurnResponse;

public class IsMyTurnWatcher extends Thread{
	public IsMyTurnWatcher(CentralControl centralControl)
	{
		if (centralControl == null)
			throw new NullPointerException();
		this.centralControl = centralControl;
	}
	
	private CentralControl centralControl;
	private List<IsMyTurnListener> listeners = new ArrayList<IsMyTurnListener>();
	private int lastBuilderUnit = -1;
	private boolean isExitting = false;
	private Date lastMyTurnStartingTime = null;
	
	public Date getLastMyTurnStartingTime()
	{
		return this.lastMyTurnStartingTime;
	}
	
	private IsMyTurnWatcher getInstance()
	{
		return this;
	}
	
	public void addListener(IsMyTurnListener toAdd) {
        listeners.add(toAdd);
    }
	
	public void notifyAllListeners(final IsMyTurnResponse response) {
		//creating other thread to notify the listeners
		new Thread(new Runnable(){
			@Override
			public void run() {
		        // Notify everybody that may be interested.
		        for (IsMyTurnListener l : listeners)
		            l.onOurTurnStarted(response, getInstance());
			}  
		}).start();
    }
	
	public void notifyAllListenersGameEnded(final IsMyTurnResponse response) {
		// Notify everybody that may be interested on the current thread, becouse we dont need to poll th server anymore.
        for (IsMyTurnListener l : listeners)
            l.onGameEnded(response, this);
    }
	
	public void exit()
	{
		isExitting = true;
	}
	
	@Override
	public void run()
	{
		IsMyTurnResponse resp = null;
		while(!isExitting)
		{
			resp = centralControl.isMyTurn(null);
			lastMyTurnStartingTime = new Date();
			
			//if we turn
			if (resp.isIsYourTurn() && lastBuilderUnit != resp.getResult().getBuilderUnit())
			{
				lastBuilderUnit = resp.getResult().getBuilderUnit();
				notifyAllListeners(resp);
			}
			try {
				IsMyTurnWatcher.sleep(350);
			} catch (InterruptedException e) {
				System.out.println("IsMyTurnWatcherThread has been interrupted.");
				e.printStackTrace();
			}
			
			//If the game has ended, we should not poll there server
			if(resp.getResult().getTurnsLeft() == 0 && resp.isIsYourTurn())
				isExitting = true;
		}
		
		//on exitting
		notifyAllListenersGameEnded(resp);
	}
}
