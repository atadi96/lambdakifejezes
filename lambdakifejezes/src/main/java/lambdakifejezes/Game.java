package lambdakifejezes;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.loxon.centralcontrol.CentralControl;
import eu.loxon.centralcontrol.CentralControlServiceService;
import eu.loxon.centralcontrol.CommonResp;
import eu.loxon.centralcontrol.IsMyTurnResponse;
import eu.loxon.centralcontrol.StartGameResponse;
import eu.loxon.centralcontrol.WatchRequest;
import eu.loxon.centralcontrol.WsBuilderunit;
import lambdakifejezes.Polling.IsMyTurnListener;
import lambdakifejezes.Polling.IsMyTurnWatcher;
import lambdakifejezes.Connection.ConnectionManager;
import lambdakifejezes.Map.Map;

public class Game implements IsMyTurnListener {
	private CentralControl centralControl;
	private ConnectionManager connectionManager;
	private URL wsdlUrl;
	private String username, password;
	private IsMyTurnWatcher isMyTurnWatcher;
	private Map map;
	private List<BuilderUnit> builderunits;
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
		
		connectionManager = new ConnectionManager(centralControl);
		map = new Map(response.getSize());
		builderunits = new ArrayList<BuilderUnit>();
		
		for(WsBuilderunit unit : response.getUnits())
			builderunits.add(new BuilderUnit(unit));
	}
	

	@Override
	public void onOurTurnStarted(IsMyTurnResponse response, Date time) {
		//TODO onOurTurnStarted
		System.out.println("Remaining turns: " + response.getResult().getTurnsLeft() + "; Our turn started at: " + dateFormat.format(new Date()));
		//Date startintTime = sender.getLastMyTurnStartingTime(); //to make sure that you cannot send order to the server after 2,5 sec
	}

	@Override
	public void onGameEnded(IsMyTurnResponse response, Date time) {
		//TODO onGameEnded
		System.out.println("The game ended at: " + dateFormat.format(time));
	}
}
