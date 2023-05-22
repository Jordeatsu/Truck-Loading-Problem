package tlp;

import java.util.*;
@SuppressWarnings("unused")
public class Truck {

	
	private final int width;
	private final int height;
	
	private int currentWidth;
	
	private int boxNumber;
	private List<Pile> piles;
	
	public final static int TRUCK_HEIGHT = 400;
	public final static int TRUCK_WIDTH = 500;
	
	public final static int BOX_LIMIT = 25;
	
	public Truck() {
		width = TRUCK_WIDTH;
		height = TRUCK_WIDTH;
		currentWidth = 0;
		boxNumber = 0;
		piles = new LinkedList<Pile>();
	}
	
	public int getCurrentWidth() {
		return currentWidth;
	}
	
	public void increaseBoxNumber() {
		boxNumber++;
	}
	
	public int getBoxNumber() {
		return boxNumber;
	}
	
	public List<Pile> getPiles() {
		return piles;
	}
	
	public void addPile(Pile p) {
		piles.add(p);
		currentWidth += p.getCurrentWidth();
	}
		
}
