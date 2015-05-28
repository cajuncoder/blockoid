package blockoid.state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import blockoid.Game;

public abstract class State {

	public Game game;
	
	public State(Game game) {
		this.game = game;
	}
	
	public void update(long elapsedTime) {
		
	}
	
	public void draw(Graphics2D g) {

	}
	
}
