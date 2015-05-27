package blockoid.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import blockoid.Assets;
import blockoid.Game;
import blockoid.audio.Audio;
import blockoid.game.being.Being;
import blockoid.game.item.*;
import blockoid.game.object.GameObject;
import blockoid.game.tile.Dirt;
import blockoid.game.tile.Empty;
import blockoid.game.tile.Tile;
import blockoid.game.tile.Water;
import blockoid.graphics.SpriteSheet;
import blockoid.state.GameState;
import blockoid.state.State;

public class Player extends Being {	
	
	public boolean inventoryOpen = false;
	public Inventory toolbelt;
	public int toolbeltIndex = 0;
	public GameObject selectedObject = null;
	
	public Player(Game game) {
		super(game);
		toolbelt = new ToolBelt("Tool Belt");
		inventory.addItem(new PickAxe());
		inventory.addItem(new DirtBlock(16));
		inventory.addItem(new DirtBlock());
		inventory.addItem(new DirtBlock(9));
		inventory.addItem(new DirtBlock());
		inventory.addItem(new DirtBlock());
		inventory.addItem(new DirtBlock(8));
		inventory.addItem(new DirtBlock());
	}
	
	boolean oldSpace = false;
	public void act(World world, long elapsedTime) {
		//Controls
		if(game.keyboard.right){
			moveRight();
		}
		if(game.keyboard.left){
			moveLeft();
		}
	
		if(game.keyboard.space && !oldSpace && timeOnGround>1 || game.keyboard.space && yVel < 0 && timeInAir < 14){
			jump();
		}
		oldSpace = game.keyboard.space;
		
		//Tool Belt
		toolbelt.moveTo(game.width/2 - toolbelt.sizeX/2, game.height - toolbelt.sizeY-2);
		if(game.mouseWheel.mouseWheelDown) {
			toolbeltIndex-=1;
			if(toolbeltIndex < 0) toolbeltIndex = toolbelt.slots.length-1;
		}
		if(game.mouseWheel.mouseWheelUp) {
			toolbeltIndex+=1;
			if(toolbeltIndex > toolbelt.slots.length-1) toolbeltIndex = 0;
		}
		for (int i = 1; i < 10; i++) {
			if (game.keyboard.num[i]) toolbeltIndex = i-1;
		}
		if(game.keyboard.num[0]) toolbeltIndex = 9;
		
		rightHandItem = toolbelt.slots[toolbeltIndex][0].item;
		
		GameState ps = (GameState)game.currentState();
		lastItemUse++;
		if(ps.gui.selectedInventory==null && rightHandItem==null) {
			if(ps.gui.grabbedItem==null) {
				if(world.game.mouse.holdL)
					emptyPrimary(world);
				if(world.game.mouse.holdR) 
					emptySecondary(world);
			}
		}
		if(inventoryOpen == false && rightHandItem!=null) {
			if(ps.gui.grabbedItem==null) {
				rightHandItem.processPrimary(world);
				rightHandItem.processSecondary(world);
			}
		}

		
		//Inventory
		if(ps.gui.inventories.size() > 1) {
			inventoryOpen = true;
		}else{inventoryOpen = false;}
		if(game.keyboard.i && oldi == false) {
			if(inventoryOpen==true) {
				ps.gui.removeInventory(inventory);
			}else{
				inventory.moveTo(game.width/2 - 4*12, game.height/2 - 2*12);
				ps.gui.addInventory(inventory);
			}
		}
	}
	
	////Empty Hand////
	int lastItemUse = 0;
	
	public void emptyPrimary(World world) {
		//lastItemUse++;
		if(lastItemUse>=15) {
		if(selectedObject==null) {
			int tileX = (int) ((world.game.mouseMotion.x+world.CameraOffX)/8);
			int tileY = (int) ((world.game.mouseMotion.y+world.CameraOffY)/8);
			if(tileX >= world.sizeX) tileX = world.sizeX-1;
			if(tileX < 0) tileX = 0;
			if(tileY >= world.sizeY) tileY = world.sizeY-1;
			if(tileY < 0) tileY = 0;
	
			if(world.tiles[tileX][tileY].object!=null){
			world.tiles[tileX][tileY].object.damage(1);
			}else{world.tiles[tileX][tileY].damage(1);}
		}
		else {
			selectedObject.damage(1);
			//selectedObject.
		}
		lastItemUse = 0;
		}
	}
	
	public void emptySecondary(World world) {
		
		if(lastItemUse>=15) {
		int tileX = (int) ((world.game.mouseMotion.x+world.CameraOffX)/8);
		int tileY = (int) ((world.game.mouseMotion.y+world.CameraOffY)/8);
		if(tileX >= world.sizeX) tileX = world.sizeX-1;
		if(tileX < 0) tileX = 0;
		if(tileY >= world.sizeY) tileY = world.sizeY-1;
		if(tileY < 0) tileY = 0;
	
		if(world.bgTiles[tileX][tileY].object!=null){
			world.bgTiles[tileX][tileY].object.damage(1);
		}else{world.bgTiles[tileX][tileY].damage(1);}
		
		lastItemUse = 0;
		}
	}
}
