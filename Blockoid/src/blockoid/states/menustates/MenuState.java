package blockoid.states.menustates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import blockoid.Audio;
import blockoid.Game;
import blockoid.graphics.Button;
import blockoid.graphics.FontUtil;
import blockoid.states.GameState;
import blockoid.states.playstate.PlayState;

public class MenuState extends GameState {
	private static final int OPTION_START = 0;
	private static final int OPTION_EDITOR = 1;
	private static final int OPTION_HELP  = 2;
	private static final int OPTION_EXIT  = 3;
	private static String TITLE = "Blokkoid";
	ArrayList<Button> options = new ArrayList<Button>();
	Font titleFont = new Font("Gabriola", Font.PLAIN, 32);
	Font optionsFont = new Font("Gabriola", Font.PLAIN, 16);

	public MenuState(Game game) {
		super(game);
		options.add(new Button("Start", optionsFont));
		options.add(new Button("Map Editor", optionsFont));
		options.add(new Button("Help", optionsFont));
		options.add(new Button("Exit", optionsFont));
	}
	
	public void update() {
		// Update all the menu options
		for (int i = 0; i < options.size(); i++) {
			options.get(i).update(game.mouseMotion.x, game.mouseMotion.y, game.mouse.clickL);
			if (options.get(i).clicked) {
				Audio audio = new Audio("res/sfx/menuselect.wav");
				audio.play(false);
			}
		}
		
		// If a menu option was clicked, change states
		if (options.get(OPTION_START).clicked){
			game.gameState = new PlayState(game);
		}
		
		if (options.get(OPTION_EDITOR).clicked){
			//System.exit(0);
			//game.gameState = new EditorState(game);
		}
		
		if (options.get(OPTION_HELP).clicked){
			game.gameState = new HelpState(game);
		}

		if (options.get(OPTION_EXIT).clicked){
			System.exit(0);
		}
	}
	
	public void draw(Graphics2D g) {
		// Draw the menu background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, game.width, game.height);
		
		// Draw the game title
		g.setFont(titleFont);
		g.setColor(Color.WHITE);
		g.drawString(TITLE, game.width/2 - (FontUtil.getWidth(TITLE, titleFont)/2), game.height/6);

		// Draw the menu options
		int optionsHeight = getHeight(options);
		int y = game.height/2 - (optionsHeight/2);
		
		for (int o = 0; o < options.size(); o++) {
			int x = game.width/2 - (options.get(o).getWidth()/2);
			options.get(o).setPos(x, y);
			if(options.get(o).selected && !options.get(o).wasSelected) {
				Audio audio = new Audio("res/sfx/mouseover.wav");
				audio.play(false);
			}
			g.setColor(options.get(o).selected ? Color.RED : Color.WHITE);

			//g.setColor(options.get(o).selected ? Color.GREEN : Color.WHITE);
			options.get(o).draw(g);
			y += options.get(o).getHeight();
		}
	}
	
	private int getHeight(ArrayList<Button> buttons) {
		int result = 0;
		for (int i = 0; i < buttons.size(); i++) {
			result += buttons.get(i).getHeight();
		}
		return result;
	}
}
