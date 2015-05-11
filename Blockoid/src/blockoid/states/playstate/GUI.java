package blockoid.states.playstate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import blockoid.Game;
import blockoid.states.playstate.world.World;
import blockoid.states.playstate.world.characters.Player;
import blockoid.states.playstate.world.items.Inventory;
import blockoid.states.playstate.world.items.InventorySlot;
import blockoid.states.playstate.world.items.Item;
import blockoid.states.playstate.world.items.ToolBelt;

public class GUI {

	Game game;
	
	World world;
	Player player;
	public ArrayList<Inventory> inventories = new ArrayList<Inventory>();
	public Inventory selectedInventory = null;
	private InventorySlot selectedSlot = null;
	public Item grabbedItem = null;
	private int mx;
	private int my;
	private int oldX;
	private int oldY;
	
	public GUI(PlayState playState) {
		this.game = playState.game;
		this.world = playState.world;
		this.player = world.player;
	}
	
	public void update() {
		if(game.keyboard.keys[KeyEvent.VK_ESCAPE]) {
			inventories.clear();
		}
		
		if(player!=null && !inventories.contains(player.toolbelt)) {
			inventories.add(player.toolbelt);
		}
		
		mx = game.mouseMotion.x;
		my = game.mouseMotion.y;
		selectedInventory = null;
		for(Inventory o : inventories) {
			if(mx > o.x && mx < o.x+o.sizeX) {
				if(my > o.y && my < o.y+o.sizeY) {
					selectedInventory = o;
				}
			}
		}
		if(selectedInventory!=null) {
			selectedInventory.update();
			selectedSlot = selectedInventory.getSelectedSlot(mx, my);
			//
			if(selectedSlot!=null && game.mouse.clickL) {
				if(grabbedItem != null && selectedSlot.item != null && grabbedItem.stackable && selectedSlot.itemsMatch(grabbedItem) && selectedSlot.item.numInStack < selectedSlot.item.maxInStack) {
					while(selectedSlot.item.numInStack < selectedSlot.item.maxInStack && grabbedItem.numInStack >= 1) {
						grabbedItem.numInStack-=1;
						selectedSlot.item.numInStack+=1;
					}
					if(grabbedItem.numInStack<=0) grabbedItem = null;
				} else {
				Item previouslyGrabbedItem = grabbedItem;
				if(grabbedItem!=null)grabbedItem.inventory = selectedInventory;
				grabbedItem = selectedSlot.item;
				selectedSlot.item = previouslyGrabbedItem;
				}
			}
			if(selectedSlot!=null && game.mouse.clickR) {
				if(grabbedItem != null && selectedSlot.item != null && grabbedItem.stackable && selectedSlot.itemsMatch(grabbedItem) && selectedSlot.item.numInStack < selectedSlot.item.maxInStack) {
						grabbedItem.numInStack-=1;
						selectedSlot.item.numInStack+=1;
					if(grabbedItem.numInStack<=0) grabbedItem = null;
				} else if(selectedSlot.item==null && grabbedItem.stackable && grabbedItem.numInStack>=1) {
				selectedSlot.item = grabbedItem.getNewInstance();
				selectedSlot.item.inventory = selectedInventory;
				grabbedItem.numInStack-=1;
				if(grabbedItem.numInStack<=0) grabbedItem=null;
				}
			}
			//Move Inventory
			if(selectedSlot==null && grabbedItem == null && game.mouse.holdL && !selectedInventory.getClass().equals(ToolBelt.class)) {
				int xOff = selectedInventory.x-oldX;
				int yOff = selectedInventory.y-oldY;
				int newX = mx+xOff;
				int newY = my+yOff;
				selectedInventory.moveTo(newX, newY);
			}
			
		}else{
			selectedSlot = null;
		}
		
		if(grabbedItem!=null && selectedInventory==null && game.mouse.clickL) {
			grabbedItem.xVel = (mx-oldX)/2;
			grabbedItem.yVel = (my-oldY)/2;
			world.addItem(grabbedItem, mx+world.CameraOffX, my+world.CameraOffY);
			grabbedItem=null;
		}
		
		oldX = mx;
		oldY = my;
	}
	
	public void addInventory(Inventory inventory) {
		if(!inventories.contains(inventory)) {
			int nOfElements = inventories.size();
			if(nOfElements > 1) {
				int x = inventories.get(nOfElements-1).x+inventories.get(nOfElements-1).sizeX;
				inventory.moveTo(x, inventories.get(nOfElements-1).y);
				//Random r = new Random();
				//inventory.moveTo(r.nextInt(game.width), r.nextInt(game.height));
				//inventory.title = inventory.title+ " "+nOfElements;
			}
			inventories.add(inventory);
		}
	}
	
	public void removeInventory(Inventory inventory) {
		if(inventories.contains(inventory)) {
			inventories.remove(inventory);
		}
	}
	
	public void draw(Graphics2D g) {
		//for(Inventory o : inventories) {
			//o.draw(g);
		//}
		for(int i = 0; i < inventories.size(); i++) {
			inventories.get(i).draw(g);
		}
		if(player!=null && player.toolbelt!=null) {
			int dx = player.toolbelt.slots[player.toolbeltIndex][0].x;
			int dy = player.toolbelt.slots[player.toolbeltIndex][0].y;
			g.setColor(Color.WHITE);
			g.drawRect(dx, dy, 11, 11);
			//g.drawRect(dx-1, dy-1, 12, 12);
		}
		if(grabbedItem!=null) {
			grabbedItem.draw(g, game.mouseMotion.x, game.mouseMotion.y);
		}
		if(selectedSlot!=null) {
			if(selectedSlot.item!=null) {
				g.setColor(Color.white);
				g.drawString(selectedSlot.item.name, mx, my);
			}
		}
	}
	
}