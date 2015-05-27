package blockoid.state;

import blockoid.Game;
import blockoid.graphics.Button;

public class MainMenuState extends MenuState {
	public MainMenuState(Game game) {
		super(game);
		addOption("start", "Start");
		addOption("editor", "Map Editor");
		addOption("help", "Help");
		addOption("exit", "Exit");
	}
	
	public void update(long elapsedTime) {
		super.update(elapsedTime);
		
		// If a menu option was clicked, change states
		if (isOptionClicked("start")) {
			game.resetState(new GameState(game));
		}
		
		if (isOptionClicked("editor")) {
			//System.exit(0);
			//game.resetState(new EditorState(game));
		}
		
		if (isOptionClicked("help")) {
			game.resetState(new HelpState(game));
		}

		if (isOptionClicked("exit")) {
			System.exit(0);
		}
	}
}
