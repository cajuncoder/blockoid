package blockoid.states.menustates;

import java.awt.event.KeyEvent;

import blockoid.Game;
import blockoid.graphics.Button;
import blockoid.states.playstate.PlayState;

public class PauseState extends MenuState {
	public PauseState(Game game) {
		super(game);
		addOption("resume", "Resume");
		addOption("main", "Main Menu");
	}
	
	public void update(long elapsedTime) {
		super.update(elapsedTime);
		
		// If a menu option was clicked, change states
		if (isOptionClicked("resume") || game.keyboard.isKeyTyped(KeyEvent.VK_ESCAPE)) {
			game.popState();
		}
		
		if (isOptionClicked("main")) {
			game.resetState(new MainMenuState(game));
		}
	}
}
