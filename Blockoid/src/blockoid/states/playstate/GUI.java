package blockoid.states.playstate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import blockoid.Game;
import blockoid.states.playstate.world.World;
import blockoid.states.playstate.world.characters.Being;
import blockoid.states.playstate.world.characters.Dog;
import blockoid.states.playstate.world.characters.ItFollows;
import blockoid.states.playstate.world.characters.Player;
import blockoid.states.playstate.world.items.Inventory;
import blockoid.states.playstate.world.items.InventorySlot;
import blockoid.states.playstate.world.items.Item;
import blockoid.states.playstate.world.items.ToolBelt;

public class GUI {

	Game game;
	
	World world;
	Player player;
	PlayState state;
	public ArrayList<Inventory> inventories = new ArrayList<Inventory>();
	public Inventory selectedInventory = null;
	private InventorySlot selectedSlot = null;
	public Item grabbedItem = null;
	private int mx;
	private int my;
	private int oldX;
	private int oldY;
	private boolean chatting = false;
	private String chatText = "";
	
	public GUI(PlayState playState) {
		this.game = playState.game;
		this.world = playState.world;
		this.player = world.player;
		this.state = playState;
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
		
		if (chatting) {
			if (game.keyboard.isKeyTyped(KeyEvent.VK_ENTER)) {
				chatting = false;
				if (chatText.length() > 0) {
					if (chatText.startsWith("cheat")) {
						processCheat();
					}
					// TODO(griffy) Send... somewhere!
					chatText = "";
				}
			} else {
				CopyOnWriteArrayList<Character> buffer = game.keyboard.getCharacterBuffer();
				for (Character ch : buffer) {					
					if ((int)ch == 8) {
						chatText = chatText.substring(0, chatText.length() > 0 ? chatText.length()-1 : 0);
					} else if ((int)ch >= 32 && (int)ch <= 126) {
						if (game.graphicsContext().getFontMetrics(state.font).stringWidth(chatText) < game.width - 20)
							chatText += ch;
					}
				}
			}
		} else if (game.keyboard.isKeyTyped(KeyEvent.VK_T)) {
			chatting = true;
		}

		
		oldX = mx;
		oldY = my;
	}
	
	protected void processCheat() {
		Pattern cheatPattern = Pattern.compile("cheat (?<type>[^\\s]+) (?<class>[^\\s]+)( (?<amount>\\d+))?");
		Matcher m = cheatPattern.matcher(chatText);
		if (m.matches()) {
			String type = m.group("type");
			String clazzName = m.group("class");
			int amount = m.group("amount") != null ? Integer.parseInt(m.group("amount")) : 1;
			try {
				if (type.equals("being")) {
					Class<?> clazz = null;
					clazz = Class.forName("blockoid.states.playstate.world.characters." + clazzName);
					Constructor<?> constructor = clazz.getConstructor(Game.class);
					for (int i = 0; i < amount; i++) {
						Being being = (Being)constructor.newInstance(game);
						world.beings.add(being);
						world.beings.get(world.beings.size()-1).place((int)player.x, (int)player.y);
					}
				} else if (type.equals("item")) {
					Class<?> clazz = null;
					clazz = Class.forName("blockoid.states.playstate.world.items." + clazzName);
					for (int i = 0; i < amount; i++) {
						player.inventory.addItem((Item)clazz.newInstance());
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
		
		if (chatting) {
			g.setColor(Color.white);
			g.drawLine(2, 12, game.width - 3, 12);
			g.setColor(Color.white);
			g.drawString("> " + chatText, 2, 10);
		}
	}
	
}
