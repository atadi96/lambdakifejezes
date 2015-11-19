package lambdakifejezes.Polling;

import eu.loxon.centralcontrol.IsMyTurnResponse;

public interface IsMyTurnListener {
	void onOurTurnStarted(IsMyTurnResponse response, IsMyTurnWatcher sender);	//response cannot be null
	void onGameEnded(IsMyTurnResponse response, IsMyTurnWatcher sender);		//response can be null
}
