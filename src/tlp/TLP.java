package tlp;

import java.util.*;

public class TLP {

	private Box[] boxes;
	private List<Truck> trucks;
	private int piles;

	private final static int MIN_BOX_SIZE = 1;

	public TLP() {
		trucks = new LinkedList<Truck>();
		piles = 0;
	}

	public void makeBoxes(int numberOfBoxes) {

		boxes = new Box[numberOfBoxes];
		for (int i = 0; i < numberOfBoxes; i++) {
			Random random = new Random();
			// random could get 0, if random = 0, this will make it equal 1
			int width = random.nextInt((100 - MIN_BOX_SIZE) + 1) + MIN_BOX_SIZE;
			int height = random.nextInt((100 - MIN_BOX_SIZE) + 1) + MIN_BOX_SIZE;
			boxes[i] = new Box(width, height);
			// System.out.println("Box " + i + " has Width: " + boxes[i].getWidth() + " and
			// Height: " + boxes[i].getHeight());
		}
	}

	public void nftlp() {
		trucks.clear();
		trucks.add(new Truck());
		piles = 0;
		System.out.println("=== Next Fit Begin ===");

		Pile p = new Pile();

		p.setCurrentWidth(boxes[0].getWidth());
		trucks.get(0).addPile(p);
		piles++;

		for (int i = 0; i < boxes.length; i++) {
			nftlpAddBox(boxes[i],
					trucks.get(trucks.size() - 1).getPiles().get(trucks.get(trucks.size() - 1).getPiles().size() - 1),
					trucks.get(trucks.size() - 1));
		}
		System.out.println("Trucks used: " + trucks.size());
		System.out.println("Piles used: " + piles);
		System.out.println("=== Next Fit End ===");
	}

	public void nftlpAddBox(Box b, Pile p, Truck t) {
		if (t.getBoxNumber() < Truck.BOX_LIMIT) {

			if (b.getWidth() <= p.getCurrentWidth() && p.getCurrentHeight() + b.getHeight() <= Truck.TRUCK_HEIGHT) {
				p.addBox(b);
				t.increaseBoxNumber();
				System.out.println("Box added at Truck: " + (trucks.indexOf(t) + 1) + " Pile: "
						+ (trucks.get(trucks.indexOf(t)).getPiles().indexOf(p) + 1));
			} else {
				Pile p2 = new Pile();
				if (t.getCurrentWidth() + b.getWidth() <= Truck.TRUCK_WIDTH && b.getHeight() <= Truck.TRUCK_HEIGHT) {
					p2.addBox(b);
					t.addPile(p2);
					piles++;
					t.increaseBoxNumber();
					System.out.println("Box added at Truck: " + (trucks.indexOf(t) + 1) + " Pile: "
							+ (trucks.get(trucks.indexOf(t)).getPiles().indexOf(p2) + 1));
				} else {
					Truck t2 = new Truck();
					p2.addBox(b);
					t2.addPile(p2);
					piles++;
					t2.increaseBoxNumber();
					trucks.add(t2);
					System.out.println("Box added at Truck: " + (trucks.indexOf(t2) + 1) + " Pile: "
							+ (trucks.get(trucks.indexOf(t2)).getPiles().indexOf(p2) + 1));
				}
			}
		} else {
			Truck t2 = new Truck();
			Pile p2 = new Pile();
			p2.addBox(b);
			t2.addPile(p2);
			piles++;
			t2.increaseBoxNumber();
			trucks.add(t2);
		}
	}

	public void bftlp() {
		trucks.clear();
		trucks.add(new Truck());
		System.out.println("=== Best Fit Begin ===");
		piles = 0;

		for (int i = 0; i < boxes.length; i++) {
			bftlpAddBox(boxes[i]);
		}
		System.out.println("Trucks used: " + trucks.size());
		System.out.println("Piles used: " + piles);
		System.out.println("=== Best Fit End ===");
	}

	public void bftlpAddBox(Box b) {
		int minSpace = Truck.TRUCK_WIDTH;
		int minPile = 0;
		int minTruck = 0;

		boolean makePile = false;

		for (int i = 0; i < trucks.size(); i++) {
			Truck t = trucks.get(i);

			if (t.getPiles().isEmpty()) {
				Pile p = new Pile();
				p.setCurrentWidth(b.getWidth());
				t.addPile(p);
				piles++;
				minSpace = b.getWidth();
			}

			if (t.getBoxNumber() < Truck.BOX_LIMIT) {
				for (int j = 0; j < t.getPiles().size(); j++) {
					Pile p = t.getPiles().get(j);

					if (b.getWidth() <= p.getCurrentWidth()
							&& p.getCurrentHeight() + b.getHeight() <= Truck.TRUCK_HEIGHT) {
						if (p.getCurrentWidth() - b.getWidth() < minSpace && p.getCurrentWidth() - b.getWidth() >= 0) {
							minSpace = p.getCurrentWidth() - b.getWidth();
							minPile = j;
							minTruck = i;
							makePile = false;
						}
					}
				}

				if (Truck.TRUCK_WIDTH - t.getCurrentWidth() - b.getWidth() < minSpace
						&& (Truck.TRUCK_WIDTH - t.getCurrentWidth() - b.getWidth()) >= 0) {
					minSpace = Truck.TRUCK_WIDTH - t.getCurrentWidth() - b.getWidth();
					minTruck = i;
					makePile = true;
				}
			}
		}

		if (minSpace <= trucks.get(minTruck).getCurrentWidth() - b.getWidth() && makePile == false) {
			trucks.get(minTruck).getPiles().get(minPile).addBox(b);
			trucks.get(minTruck).increaseBoxNumber();
		} else if (makePile == true && trucks.get(minTruck).getCurrentWidth() + b.getWidth() <= Truck.TRUCK_WIDTH) {
			Pile p2 = new Pile();
			p2.addBox(b);
			trucks.get(minTruck).addPile(p2);
			piles++;
			trucks.get(minTruck).increaseBoxNumber();
			minPile = trucks.get(minTruck).getPiles().size() - 1;
		} else {
			Truck t2 = new Truck();
			Pile p2 = new Pile();
			p2.addBox(b);
			t2.addPile(p2);
			piles++;
			t2.increaseBoxNumber();
			trucks.add(t2);
			minTruck = trucks.size() - 1;
			minPile = 0;
		}
		System.out.println("Box added at Truck: " + (minTruck + 1) + " Pile: " + (minPile + 1));
	}

	public Box[] getBoxes() {
		return boxes;
	}

	public List<Truck> getTrucks() {
		return trucks;
	}

}
