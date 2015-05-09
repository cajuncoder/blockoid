package blockoid.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class SpriteSheet implements Serializable {

	//private Color transparent = new Color(163,73,164);
	public int spriteSizeX = 0;
	public int spriteSizeY = 0;
	public int spritesX;
	public int spritesY;
	public BufferedImage sheet;
	public BufferedImage[] sheets;
	public int sheetWidth;
	public int sheetHeight;
	public Sprite[] sprites;
	
	//----------------Constructor----------------//
	public SpriteSheet(String tileSheet, int spriteSizeX, int spriteSizeY) {
	
		//size
		this.spriteSizeX = spriteSizeX;
		this.spriteSizeY = spriteSizeY;
		
		//load image
		//sheet = new ImageIcon(tileSheet).getImage();
		BufferedImage img = null;
		try {
		    sheet = ImageIO.read(new File(tileSheet));
		} catch (IOException e) {
		}
		sheets = new BufferedImage[8];
		for(int i = 0; i < 8; i++) {
			sheets[i] = tint(sheet,i);
		}
		sheetWidth = sheet.getWidth(null);
		sheetHeight = sheet.getHeight(null);
		System.out.println(sheetWidth);

		//store sprite frame coordinates in sprite object.
		spritesX = (sheetWidth)/spriteSizeX;
		spritesY = (sheetHeight)/spriteSizeY;
		sprites = new Sprite[spritesX*spritesY];
		for(int y = 0; y < spritesY; y++) {
			for(int x = 0; x < spritesX; x++) {
				sprites[x + y*spritesX] = new Sprite(x*this.spriteSizeX, y*this.spriteSizeY);
			}
		}
		System.out.println("Sprites loaded: " + sprites.length);
	}
	
	//------------------drawSprite------------------//
	public void drawSprite(int dx, int dy, int spriteIndex, Graphics2D g) {
		
		//draw sprite from sheet
		g.drawImage(sheet, dx, dy, dx+spriteSizeX, dy+spriteSizeY, sprites[spriteIndex].x, sprites[spriteIndex].y, sprites[spriteIndex].x+spriteSizeX, sprites[spriteIndex].y+spriteSizeY, null);
	}
	
	public void drawSprite(int dx, int dy, int spriteIndex, int tint, Graphics2D g) {
		
		//draw sprite from sheet
		//BufferedImage tintedSheet = tint(sheet, tint);
		g.drawImage(sheets[tint], dx, dy, dx+spriteSizeX, dy+spriteSizeY, sprites[spriteIndex].x, sprites[spriteIndex].y, sprites[spriteIndex].x+spriteSizeX, sprites[spriteIndex].y+spriteSizeY, null);
	}
	
	
	public static BufferedImage tint(BufferedImage img, int shade) {

    	//BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(),
    	//        BufferedImage.TRANSLUCENT);
		//BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(),
		//        BufferedImage.TRANSLUCENT);
		BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		//shade = 8-shade;
		for (int x = 0; x < img.getWidth(); x++) {
	        for (int y = 0; y < img.getHeight(); y++) {
	            //Color color = new Color(img.getRGB(x, y));
	            Color color = new Color(img.getRGB(x, y), true);

	            // do something with the color :) (change the hue, saturation and/or brightness)
	             //float[] hsb = new float[3];
	             //shade = 3;
	            int redStep = color.getRed()/8;
	            int blueStep = color.getBlue()/8;
	            int greenStep = color.getGreen()/8;
	             int red = cap(redStep*(shade+1),0,255);
	             int green = cap(greenStep*(shade+1),0,255);
	             int blue = cap(blueStep*(shade+1),0,255);
	             int alpha = color.getAlpha();
	             //Color.RGBtoHSB(red, green, blue, hsb);
	             color = new Color(red,green,blue,alpha);
	             
	            // or just call brighter to just tint it
	            //Color brighter = color.darker();

	             //color = color.darker();
	            result.setRGB(x, y, color.getRGB());
	             //result.setRGB(x, y, Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
	             
	        }
	        
	    }
	    return result;
	}
	
	public static int cap(int value, int min, int max) {
		int result = value;
		if(result > max) result = max;
		if(result < min) result = min;
		return result;
	}
}
