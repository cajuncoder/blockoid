package blockoid.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import blockoid.Assets;
import blockoid.Game;
import blockoid.audio.Audio;
import blockoid.graphics.Button;
import blockoid.graphics.FontUtil;

abstract public class MenuState extends State {
	private static String TITLE = "Blockoid";
	HashMap<String, Integer> optionIndexMap = new HashMap<String, Integer>();
	ArrayList<Button> options = new ArrayList<Button>();
	Font titleFont = Assets.getFont("Gabriola", Font.PLAIN, 32);
	Font optionsFont = Assets.getFont("Gabriola", Font.PLAIN, 16);
	
	public MenuState(Game game) {
		super(game);
	}
	
	protected void addOption(String name, String title) {
		optionIndexMap.put(name, options.size());
		options.add(new Button(title, optionsFont));
	}
	
	protected boolean isOptionClicked(String name) {
		int index = optionIndexMap.get(name);
		return options.get(index).clicked;
	}
	
	public void update(long elapsedTime) {
		// Update all the menu options
		for (Button option : options) {
			option.update(game.mouseMotion.x, game.mouseMotion.y, game.mouse.clickL);
			if (option.clicked) {
				Audio audio = Assets.getAudio("menuselect");
				audio.play(false);
			}
			if (option.selected && !option.wasSelected) {
				Audio audio = Assets.getAudio("mouseover");
				audio.play(false);
			}
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
		int optionsHeight = getHeight();
		int y = game.height/2 - (optionsHeight/2);
		
		for (int o = 0; o < options.size(); o++) {
			int x = game.width/2 - (options.get(o).getWidth()/2);
			options.get(o).setPos(x, y);

			g.setColor(options.get(o).selected ? Color.RED : Color.WHITE);

			//g.setColor(options.get(o).selected ? Color.GREEN : Color.WHITE);
			options.get(o).draw(g);
			y += options.get(o).getHeight();
		}
	}
	
	private int getHeight() {
		int result = 0;
		for (Button option : options) {
			result += option.getHeight();
		}
		return result;
	}
}
