package blockoid.states.playstate.world.items;

import java.awt.Color;
import java.awt.Graphics2D;

public class ToolBelt extends Inventory {

	public ToolBelt(String str) {
		super("",10,1); // ii -- REALLY???
		padding = 1;
		this.title = title;
		this.nOfTilesX = nOfTilesX;
		this.nOfTilesY = nOfTilesY;
		slots = new InventorySlot[nOfTilesX][nOfTilesY];
		for(int xi = 0; xi < nOfTilesX; xi++) {
			for(int yi = 0; yi < nOfTilesY; yi++) {
				slots[xi][yi] = new InventorySlot(x+padding+(xi*InventorySlot.SIZE), y+padding+(yi*InventorySlot.SIZE)+titleH);
			}
		}
		titleH = 0;
		sizeX = (InventorySlot.SIZE*nOfTilesX) + (padding*2);
		sizeY = (InventorySlot.SIZE*nOfTilesY) + (padding*2) + titleH;
	}
	
	
	public void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(x-1, y-1, sizeX+2, sizeY+2);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x, y, sizeX, sizeY);
		g.setColor(Color.black);
		for(int xi = 0; xi < nOfTilesX; xi++) {
			for(int yi = 0; yi < nOfTilesY; yi++) {
				slots[xi][yi].draw(g);
			}
		}
	}
}
