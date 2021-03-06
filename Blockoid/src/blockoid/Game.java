package blockoid;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import blockoid.input.Keyboard;
import blockoid.input.Mouse;
import blockoid.input.MouseMotion;
import blockoid.input.MouseWheel;
import blockoid.state.MainMenuState;
import blockoid.state.MenuState;
import blockoid.state.State;


//@SuppressWarnings("serial")
public class Game implements Serializable {

	// ****************************************************//
	//                      Blockoid                       //
	// ****************************************************//

	// JFrame
	public JFrame jframe = new JFrame();
	JPanel jpanel = new JPanel();
	
	// GameState
	public Stack<State> stateStack = new Stack<State>();

	// Graphics
	//public static int WIDTH = 160+80;
	//public static int HEIGHT = 120+60;
	public int width = 16*17; //272
	public int height = 16*13; //208
	public int oldHeight = 0;
	public int  oldWidth = 0;
	public int scale = 3;
	public BufferedImage bufferImage;
	public Graphics2D bufferGraphics;
	public Graphics2D g;
	//public Audio audio = new Audio();
	public int cameraX = 0;
	public int cameraY = 0;
	
	public static int MENU = 0;
	public static int GAME = 1;
	public int mode = MENU;

	// Keyboard
	public Keyboard keyboard;
	public Mouse mouse;
	public MouseMotion mouseMotion;
	public MouseWheel mouseWheel;
	
	// Multiplayer
	
	// Level

	// -----------------Constructor-------------------//
	public Game() {
		long maxBytes = Runtime.getRuntime().maxMemory();
		System.out.println("Max memory: " + maxBytes / 1024 / 1024 + "M");
		//JOptionPane.showMessageDialog(jframe, ("Max memory: " + maxBytes / 1024 / 1024 + "M"));
		// JFrame
		jframe.setContentPane(jpanel);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//jframe.setResizable(false);
		jframe.setVisible(true);
		// jframe.setFocusable(true);
		
		// JPanel
		jpanel.setPreferredSize(new Dimension(width * scale, height * scale));
		// jpanel.setFocusable(true);
		// jpanel.requestFocus();
		jframe.pack();
		jframe.setLocationRelativeTo(null);

		// Input
		keyboard = new Keyboard();
		mouse = new Mouse();
		mouseWheel = new MouseWheel();
		mouseMotion = new MouseMotion(this);
		jframe.addKeyListener(keyboard);
		jpanel.addMouseListener(mouse);
		jpanel.addMouseMotionListener(mouseMotion);
		jpanel.addMouseWheelListener(mouseWheel);
		jframe.setFocusTraversalKeysEnabled(false);
		
		// Transparent 16 x 16 pixel cursor image.
		//BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		// Create a new blank cursor.
		//Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		// cursorImg, new Point(0, 0), "blank cursor");
		// Set the blank cursor to the JFrame.
		//jframe.getContentPane().setCursor(blankCursor);
		
		resetState(new MainMenuState(this));
	}
		
	public State currentState() {
		return stateStack.peek();
	}
	
	public State popState() {
		return stateStack.pop();
	}
	
	public void pushState(State state) {
		stateStack.push(state);
	}
	
	public void resetState(State state) {
		stateStack.clear();
		stateStack.push(state);
	}
	
	// ------------------Main--------------------//
	public static void main(String[] args) {
		Game game = new Game();
		game.run();
	}

	// -------------------Run--------------------//
	public void run() {
		try{
			width = jpanel.getWidth()/scale;
			height = jpanel.getHeight()/scale;
			bufferImage = (BufferedImage) jpanel.createImage(width, height);
			bufferGraphics = (Graphics2D) bufferImage.getGraphics();

		// time	
		long dt = 1000000000 / 60;
		long currentTime = System.nanoTime();
		long oldfps = 0;
		long lastRender = 0;
		int ticks = 0;
		long accumulator = 0;
		long t = 0;
		int fps = 0;
		
		while (true) {
			
			long fpslimit = 1000000000 / 999;
			width = jpanel.getWidth()/scale;
			height = jpanel.getHeight()/scale;
			
			if(oldWidth != width || oldHeight != height) {
				bufferImage = (BufferedImage) jpanel.createImage(width, height);
				bufferGraphics = (Graphics2D) bufferImage.getGraphics();
				g = graphicsContext();
			}
			
			// update
			long newTime = System.nanoTime();
			long frameTime = newTime - currentTime;
			currentTime = newTime;
			accumulator += frameTime;
			
			while (accumulator >= dt) {
				//we don't need t passed into update.
				//this runs at 60 ticks regardless, which is all we need.
				update(t);
				accumulator -= dt;
				t += dt;
				ticks++;
			}
			
			if (currentTime - lastRender >= fpslimit) {
				render();
				fps++;
				lastRender = currentTime;
			}
			
			if (currentTime - oldfps >= 1000000000) {
				jframe.setTitle("-Blockoid-     FPS: " + fps + " Updates: " + ticks);
				fps = 0;
				ticks = 0;
				oldfps = currentTime;
			}
			
			oldWidth = width;
			oldHeight = height;
		}
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(jframe, e.getStackTrace(), e.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
		}
	}

	// -------------------Update---------------------//
	public void update(long elapsedTime) {
		keyboard.update();
		currentState().update(elapsedTime);
		mouseWheel.clear();
		mouse.clear();
		keyboard.clear();
	}

	
	// -------------------Render---------------------//
	
	public void render() {
		if(g == null) 
		g = graphicsContext();
		currentState().draw(bufferGraphics);

		g.drawImage(bufferImage, 0, 0, width * scale, height * scale, jpanel);

		//g.dispose();
	}
	
	public void draw() {
		//if(g == null) 
		//g = graphicsContext();
		g.drawImage(bufferImage, 0, 0, width * scale, height * scale, jpanel);
		//g.dispose();
	}
	
	public Graphics2D graphicsContext() {
		return (Graphics2D) jpanel.getGraphics();
	}
}
