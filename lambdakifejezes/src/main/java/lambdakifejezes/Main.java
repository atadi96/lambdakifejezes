package lambdakifejezes;

import java.net.MalformedURLException;
import java.io.IOException;
import java.net.URL;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException, MalformedURLException {

		Game myGame = new Game(new URL(args[0]), args[1], args[2]);
		myGame.startGame();
	}
}