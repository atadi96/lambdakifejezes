package lambdakifejezes.Polling;

import eu.loxon.centralcontrol.IsMyTurnResponse;

public interface IsMyTurnListener {
	void onOurTurnStarted(IsMyTurnResponse response);	//response cannot be null
	void onGameEnded(IsMyTurnResponse response);		//response can be null
}
