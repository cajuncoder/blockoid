package blockoid.states.playstate.world.items;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.CopyOnWriteArrayList;

import blockoid.Assets;
import blockoid.graphics.SpriteSheet;

public class InventorySlot {
	public static int SIZE = 12;
	public int x;
	public int y;
	public Item item;
	public SpriteSheet sprite;
	
	public InventorySlot(int x, int y) {
		this.sprite = Assets.getSpriteSheet("gui/inventorySlot", SIZE, SIZE);
		this.x = x;
		this.y = y;
		item = null;
	}
	
	public void draw(Graphics2D g) {
		sprite.drawSprite(x, y, 0, g);
		if(item!=null) item.draw(g, x, y);
	}

	public boolean itemsMatch(Item comparedItem) {
		if(comparedItem!=null && this.item!=null) {
			if(this.item.getClass().equals(comparedItem.getClass())) {
				return true;
			}
		}
		return false;
	}
	public boolean isSelected(int mx, int my) {
		if(mx > x && mx < x+SIZE) {
			if(my > y && my < y+SIZE) {
				return true;
			}
		}
		return false;
	}
}
