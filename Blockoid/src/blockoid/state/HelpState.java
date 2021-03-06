package blockoid.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import blockoid.Assets;
import blockoid.Game;
import blockoid.audio.Audio;
import blockoid.graphics.Button;
import blockoid.graphics.FontUtil;

public class HelpState extends State {
	ArrayList<Button> options = new ArrayList<Button>();
	Font titleFont = Assets.getFont("Gabriola", Font.PLAIN, 32);
	Font bodyFont = Assets.getFont("Gabriola", Font.PLAIN, 16);
	
	public HelpState(Game game) {
		super(game);
	}
	
	public void update(long elapsedTime) {
		if (game.mouse.clickL) {
			game.resetState(new MainMenuState(game));
		}
	}
	
	public void draw(Graphics2D g) {
		// Draw the background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, game.width, game.height);
		
		// Draw the controls header
		g.setFont(titleFont);
		g.setColor(Color.WHITE);
		int headerFontHeight = FontUtil.getHeight("Controls", titleFont);
		g.drawString("Controls", game.width/2 - (FontUtil.getWidth("Controls", titleFont)/2), game.height/6);
	
		// Draw the controls explanation
		g.setFont(bodyFont);
		g.setColor(Color.WHITE);
		String a = "a / left arrow - move left";
		String d = "d / right arrow - move right";
		String w = "w / up arrow - raise arm";
		String s = "s / down arrow - lower arm";
		g.drawString(a, game.width/2 - (FontUtil.getWidth(a, bodyFont)/2), game.height/6 + headerFontHeight + 10);
		g.drawString(d, game.width/2 - (FontUtil.getWidth(d, bodyFont)/2), game.height/6 + headerFontHeight + 20);
		g.drawString(w, game.width/2 - (FontUtil.getWidth(w, bodyFont)/2), game.height/6 + headerFontHeight + 30);
		g.drawString(s, game.width/2 - (FontUtil.getWidth(s, bodyFont)/2), game.height/6 + headerFontHeight + 40);
	}
}
