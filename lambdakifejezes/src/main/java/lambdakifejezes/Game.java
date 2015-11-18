package lambdakifejezes;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import eu.loxon.centralcontrol.CentralControl;
import eu.loxon.centralcontrol.CentralControlServiceService;
import eu.loxon.centralcontrol.CommonResp;
import eu.loxon.centralcontrol.IsMyTurnResponse;
import eu.loxon.centralcontrol.StartGameResponse;
import eu.loxon.centralcontrol.WsBuilderunit;
import lambdakifejezes.Polling.IsMyTurnListener;
import lambdakifejezes.Polling.IsMyTurnWatcher;
import lambdakifejezes.Map.Map;

public class Game implements IsMyTurnListener {
	private CentralControl centralControl;
	private URL wsdlUrl;
	private String username, password;
	private IsMyTurnWatcher isMyTurnWatcher;
	private Map map;
	private List<WsBuilderunit> builderunits;
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.M.dd. hh:mm:ss,S");;
	
	public Game(URL url, String username, String password)
	{
		this.wsdlUrl = url;
		this.username = username;
		this.password = password;
	}
	
	public void startGame()
	{
		//setting authentication
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {          
				return new PasswordAuthentication(username, password.toCharArray());
			}
		});

		//connecting to the server
		centralControl = new CentralControlServiceService(wsdlUrl).getCentralControlPort();

		//creating the poller thread
		isMyTurnWatcher = new IsMyTurnWatcher(centralControl);
		isMyTurnWatcher.addListener(this);
		
		//starting the game
		handleGameStart(centralControl.startGame(null));
		isMyTurnWatcher.start();
	}
	public void quitGame()
	{
		isMyTurnWatcher.exit();
	}
	
	private void handleGameStart(StartGameResponse response)
	{
		//TODO handleGameStart: update map, etc...
		System.out.println("Game started at: " + dateFormat.format(new Date()));
		
		map = new Map(response.getSize());
		builderunits = response.getUnits();
		handleCommonResponse(response.getResult());
	}
	
	private void handleCommonResponse(CommonResp resp)
	{
		//TODO think about how to solve Request / respone messaging, implement the solution (preferred in other class)
	}
	

	@Override
	public void onOurTurnStarted(IsMyTurnResponse response) {
		//TODO onOurTurnStarted
		System.out.println("Our turn started at: " + dateFormat.format(new Date()));
	}

	@Override
	public void onGameEnded(IsMyTurnResponse response) {
		//TODO onGameEnded
		System.out.println("The game ended at: " + dateFormat.format(new Date()));
	}
}
