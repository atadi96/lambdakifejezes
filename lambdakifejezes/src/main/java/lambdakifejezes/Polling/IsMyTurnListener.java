package lambdakifejezes.Polling;

import java.util.Date;

import eu.loxon.centralcontrol.IsMyTurnResponse;

public interface IsMyTurnListener {
	void onOurTurnStarted(IsMyTurnResponse response, Date time);	//response cannot be null, time can be null
	void onGameEnded(IsMyTurnResponse response, Date time);			//response can be null, time can be null
}
