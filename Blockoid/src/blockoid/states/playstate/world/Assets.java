package blockoid.states.playstate.world;

import java.util.HashMap;
import java.util.Map;

import blockoid.Audio;
import blockoid.graphics.SpriteSheet;

public class Assets {

	public static HashMap<String, SpriteSheet> spriteSheets = new HashMap<String, SpriteSheet>();
	public static HashMap<String, Audio> sounds = new HashMap<String, Audio>();
	
	public Assets(){
		SpriteSheet sprite;
		
		////////////////// S P R I T E S /////////////////////
		
		//// TILES ////
		//lightMask
		sprite = new SpriteSheet("res/gfx/tiles/lightmask.png",8,8);
		spriteSheets.put("lightMask",sprite);
		
		//damageOverlay
		sprite = new SpriteSheet("res/gfx/tiles/tile_damage_overlay.png",8,8);
		spriteSheets.put("damageOverlay",sprite);
		
		//dirt
		sprite = new SpriteSheet("res/gfx/tiles/dirt.png",8,8);
		spriteSheets.put("dirt",sprite);
		
		//grass
		sprite = new SpriteSheet("res/gfx/tiles/grass.png",8,8);
		spriteSheets.put("grass",sprite);
		
		//desert
		sprite = new SpriteSheet("res/gfx/tiles/desert.png",8,8);
		spriteSheets.put("desert",sprite);
		
		//desertGrass
		sprite = new SpriteSheet("res/gfx/tiles/deserttop.png",8,8);
		spriteSheets.put("desertGrass",sprite);
		
		//// OBJECTS ////
		//oakTree
		sprite = new SpriteSheet("res/gfx/objects/tree_oak.png", 60, 83);
		spriteSheets.put("oakTree",sprite);
		
		//palmTree
		sprite = new SpriteSheet("res/gfx/objects/tree_palm.png", 51, 54);
		spriteSheets.put("palmTree",sprite);
		
		//// ITEMS////
		sprite = new SpriteSheet("res/gfx/items/inventory_pickaxe.png",11,11);
		spriteSheets.put("inventoryPickaxe", sprite);
		
		//// GUI ////
		sprite = new SpriteSheet("res/gfx/gui/inventory_slot.png", 12, 12);
		spriteSheets.put("inventorySlot",sprite);
		
		
		
		////////////////// S O U N D S /////////////////////
		Audio audio;
		
		audio = new Audio("res/sfx/BestBreakBlockYet.wav", true);
		sounds.put("breakDirt", audio);
		
		audio = new Audio("res/sfx/PickupItem.wav", true);
		sounds.put("pickupItem", audio);
		
	}
	
	public static SpriteSheet get(String img) {
		SpriteSheet result = spriteSheets.get(img);
		return result;
	}
	
	public static Audio getSound(String audio) {
		Audio result = sounds.get(audio);
		return result;
	}
}
