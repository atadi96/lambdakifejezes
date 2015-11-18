package lambdakifejezes;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.jmx.snmp.Timestamp;

import eu.loxon.centralcontrol.CentralControl;
import eu.loxon.centralcontrol.CentralControlServiceService;
import eu.loxon.centralcontrol.IsMyTurnResponse;
import eu.loxon.centralcontrol.StartGameResponse;
import lambdakifejezes.Polling.IsMyTurnListener;
import lambdakifejezes.Polling.IsMyTurnWatcher;

public class Game implements IsMyTurnListener {
	private CentralControl centralControl;
	private URL wsdlUrl;
	private String username, password;
	private IsMyTurnWatcher isMyTurnWatcher;
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.M.dd. hh:mm:ss,S");;
	
	public Game(URL url, String username, String password)
	{
		this.wsdlUrl = url;
		this.username = username;
		this.password = password;
	}
	
	public void start()
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
	private void handleGameStart(StartGameResponse response)
	{
		//TODO handleGameStart: update map, etc...
		System.out.println("Game started at: " + dateFormat.format(new Date()));
	}
	
	public void quitGame()
	{
		isMyTurnWatcher.exit();
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
