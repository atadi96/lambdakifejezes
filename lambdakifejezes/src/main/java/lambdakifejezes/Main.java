package lambdakifejezes;

import java.net.MalformedURLException;
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;

import eu.loxon.centralcontrol.CentralControl;
import eu.loxon.centralcontrol.CentralControlServiceService;
import eu.loxon.centralcontrol.GetSpaceShuttleExitPosRequest;
import eu.loxon.centralcontrol.IsMyTurnRequest;
import eu.loxon.centralcontrol.RadarRequest;
import eu.loxon.centralcontrol.StartGameRequest;
import eu.loxon.centralcontrol.StartGameResponse;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException, MalformedURLException {
		
		//hogyan is lehet haszn�lni
		Game myGame = new Game(new URL("http://javachallenge.loxon.hu:8443/engine/CentralControl?wsdl"), "lambdakifejezes", "AUPI4847");
		myGame.startGame();
		Thread.sleep(10000);	//lets 10 seconds to run the game
		myGame.quitGame();
		//m�dos�t�s v�ge: Bal�zs
		
		System.out.println("Hello world!");
		//System.out.println(args[0]);
		
		/* what we need:
		egy olyan met�dus ami
			+ kezeli az argumentumokat �s ellen�riz
			+ elind�tja a mi alap oszt�lyunkb�l a j�t�kot
				
		*/
		
		
		//plusz adok m�g egy kis piszkot:
		//lambdakifejezes:AUPI4847@
		String ipAddress="http://javachallenge.loxon.hu:8443/engine/CentralControl?wsdl";
		//ez val�j�ban az arg[0] lesz
		 
		final URL url = new URL(ipAddress);
		Authenticator.setDefault(new Authenticator()
		{
		  @Override
		  protected PasswordAuthentication getPasswordAuthentication()
		  {
		    return new PasswordAuthentication("lambdakifejezes", "AUPI4847".toCharArray());
		  }
		});
		//final URLConnection connection = url.openConnection();
		CentralControlServiceService ipService =new CentralControlServiceService(url);
		//ipService
		CentralControl centralControl =ipService.getCentralControlPort();
		StartGameResponse a=centralControl.startGame(new StartGameRequest());
		System.out.println(centralControl.getSpaceShuttleExitPos(new GetSpaceShuttleExitPosRequest()).getCord().getX());
		System.out.println(centralControl.getSpaceShuttleExitPos(new GetSpaceShuttleExitPosRequest()).getCord().getY());
		System.out.println(a.getSize().getX()+" "+a.getSize().getX());
		System.out.println(centralControl.radar(new RadarRequest()).getResult().getActionPointsLeft());
		System.out.println(a.getResult().getScore().getTotal());
		System.out.println(a.getResult().getActionPointsLeft());
		System.out.println(centralControl.isMyTurn(new IsMyTurnRequest()).isIsYourTurn());
		
		//System.out.println(a.getUnits().get(0).getUnitid());
		
	}

}
