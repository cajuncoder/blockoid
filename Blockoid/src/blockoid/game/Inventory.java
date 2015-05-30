package blockoid.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import blockoid.Assets;
import blockoid.audio.Audio;
import blockoid.game.item.Item;
import blockoid.game.tile.Tile;
import blockoid.graphics.SpriteSheet;

public class Inventory {

	boolean moveable;
	public int x = 0;
	public int y = 0;
	int nOfTilesX;
	int nOfTilesY;
	public int sizeX;
	public int sizeY;
	public int padding = 3;
	public int titleH;
	public String title;
	public InventorySlot[][] slots;
	
	public Audio pickupBloop = Assets.getAudio("pickupItem");
	
	public Inventory(String title, int nOfTilesX, int nOfTilesY) {
		this.title = title;
		this.nOfTilesX = nOfTilesX;
		this.nOfTilesY = nOfTilesY;
		slots = new InventorySlot[nOfTilesX][nOfTilesY];
		for(int xi = 0; xi < nOfTilesX; xi++) {
			for(int yi = 0; yi < nOfTilesY; yi++) {
				slots[xi][yi] = new InventorySlot(x+padding+(xi*InventorySlot.SIZE), y+padding+(yi*InventorySlot.SIZE)+titleH);
			}
		}
		titleH = 10;
		sizeX = (InventorySlot.SIZE*nOfTilesX) + (padding*2);
		sizeY = (InventorySlot.SIZE*nOfTilesY) + (padding*2) + titleH;
	}
	
	
	public boolean inventoryFull() {
		for(int xi = 0; xi < nOfTilesX; xi++) {
			for(int yi = 0; yi < nOfTilesY; yi++) {
				if(slots[xi][yi].item==null || slots[xi][yi].item.stackable && slots[xi][yi].item.numInStack < slots[xi][yi].item.maxInStack) {
					return false;
				}
			}
		}
		return true;
	}
	
	//WARNING: ITEMSLEFTOVER GET DELETED
	public int addItem(Item item) {
		//Stackable
		int itemsLeftover = 0;
		
		for(int yi = 0; yi < nOfTilesY; yi++) {
			for(int xi = 0; xi < nOfTilesX; xi++) {
				Item slotItem = slots[xi][yi].item;
				
				//Non Stackable
				if(slots[xi][yi].item==null) {
					item.inventory = this;
					slots[xi][yi].item = item;
					pickupBloop.play(false);
					return 0;
				}
					///if(item!=null) {
						//Item slotItem = slots[xi][yi].item;
						//if(slots[xi][yi].item==null) {
						//	item.inventory = this;
						//	slots[xi][yi].item = item;
						//	pickupBloop.play(false);
						//	return 0;
						//}
				if(item.stackable) {
						if(slots[xi][yi].item!=null && slots[xi][yi].itemsMatch(item)) {
							while(slotItem.numInStack < slotItem.maxInStack) {
								item.numInStack-=1;
								slotItem.numInStack+=1;
								if(item.numInStack<=0) {
									pickupBloop.play(false);
									return 0;
								}
							}
						}
					}
				}
			}
		///}else{

		//}
//
		return itemsLeftover;
	}
	
	public void removeItem(Item item) {
		for(int yi = 0; yi < nOfTilesY; yi++) {
			for(int xi = 0; xi < nOfTilesX; xi++) {
				if(slots[xi][yi].item==item) {
					slots[xi][yi].item.inventory = null;
					slots[xi][yi].item=null;
					return;
				}
			}
		}
	}
	
	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
		for(int xi = 0; xi < nOfTilesX; xi++) {
			for(int yi = 0; yi < nOfTilesY; yi++) {
				slots[xi][yi].x = x+padding+(xi*InventorySlot.SIZE);
				slots[xi][yi].y	= y+padding+(yi*InventorySlot.SIZE)+titleH;
			}
		}
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(x-1, y-1, sizeX+2, sizeY+2);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x, y, sizeX, sizeY);
		g.setColor(Color.black);
		g.drawString(title,x+padding,y+titleH);
		for(int xi = 0; xi < nOfTilesX; xi++) {
			for(int yi = 0; yi < nOfTilesY; yi++) {
				slots[xi][yi].draw(g);
			}
		}
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}

	public InventorySlot getSelectedSlot(int mx, int my) {
		InventorySlot selectedSlot = null;
		for(int xi = 0; xi < nOfTilesX; xi++) {
			for(int yi = 0; yi < nOfTilesY; yi++) {
				if(slots[xi][yi].isSelected(mx, my)) {
					selectedSlot = slots[xi][yi];
					return selectedSlot;
				}
			}
		}
		return null;
	}
}
