package blockoid;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import blockoid.graphics.SpriteSheet;

public class Assets {
	private static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	private static HashMap<String, Audio> audioClips = new HashMap<String, Audio>();
	private static HashMap<String, SpriteSheet> spriteSheets = new HashMap<String, SpriteSheet>();
	
	public static BufferedImage getImage(String name) {
		return getImage(name, "png");
	}
	
	public static BufferedImage getImage(String name, String extension) {
		BufferedImage img = images.get(name);
		if (img == null) {
			String filename = "res/gfx/" + name + "." + extension;
			System.out.println(filename);
			try {
			    img = ImageIO.read(new File(filename));
			} catch (IOException e) {
				return null;
			}
			images.put(name, img);
		}
		return img;
	}
	
	public static Audio getAudio(String name) {
		return getAudio(name, "wav");
	}
	
	public static Audio getAudio(String name, String extension) {
		Audio audio = audioClips.get(name);
		if (audio == null) {
			String filename = "res/sfx/" + name + "." + extension;
			System.out.println(filename);
			audio = new Audio(filename);
			audioClips.put(name, audio);
		}
		return audio;
	}
	
	public static SpriteSheet getSpriteSheet(String name, int spriteWidth, int spriteHeight) {
		return getSpriteSheet(name, spriteWidth, spriteHeight, "png");
	}
	
	public static SpriteSheet getSpriteSheet(String name, int spriteWidth, int spriteHeight, String extension) {
		// Store the spritesheet indexed by width + height since they are part of the "type"
		String key = name + "_" + spriteWidth + "x" + spriteHeight;
		SpriteSheet sheet = spriteSheets.get(key);
		if (sheet == null) {
			BufferedImage img = getImage(name, extension);
			sheet = new SpriteSheet(img, spriteWidth, spriteHeight);
			spriteSheets.put(key, sheet);
		}
		return sheet;
	}
	
	public static Font getFont(String name, int style, int size) {
		// Did this so we can support custom fonts in the future that require
		// being loaded
		return new Font(name, style, size);
	}
}
