package blockoid.states.playstate.world.characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import blockoid.Assets;
import blockoid.Audio;
import blockoid.Game;
import blockoid.graphics.SpriteSheet;
import blockoid.states.GameState;
import blockoid.states.playstate.PlayState;
import blockoid.states.playstate.world.items.*;
import blockoid.states.playstate.world.tiles.Dirt;
import blockoid.states.playstate.world.tiles.Empty;
import blockoid.states.playstate.world.tiles.Tile;
import blockoid.states.playstate.world.tiles.Water;
import blockoid.states.playstate.world.World;

public class Player extends Character {	
	public Player() {
		super();
		
		inventory.addItem(new PickAxe());
		inventory.addItem(new DirtBlock(16));
		inventory.addItem(new DirtBlock());
		inventory.addItem(new DirtBlock(9));
		inventory.addItem(new DirtBlock());
		inventory.addItem(new DirtBlock());
		inventory.addItem(new DirtBlock(8));
		inventory.addItem(new DirtBlock());
	}
	
	public void act(Game game, World world) {
		//Controls
		if(game.keyboard.right){
			moveRight();
		}
		if(game.keyboard.left){
			moveLeft();
		}
	
		if(game.keyboard.space && timeOnGround>1 || game.keyboard.space && yVel < 0 && timeInAir < 14){
			jump();
		}
		
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
		rightHandItem = toolbelt.slots[toolbeltIndex][0].item;
		
		PlayState ps = (PlayState)game.gameState;
		if(ps.gui.selectedInventory==null && rightHandItem==null) {
			if(ps.gui.grabbedItem==null) {
				emptyPrimary(world);
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

}
