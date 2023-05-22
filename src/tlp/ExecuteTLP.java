package tlp;

public class ExecuteTLP {

	public static void main(String[] args) {
		TLP tlp = new TLP();
		
		int boxNumber = 100;
		
		tlp.makeBoxes(boxNumber);
		
		for(Box b : tlp.getBoxes()) {
			System.out.println("Width: " + b.getWidth() + " Height: " + b.getHeight());
		}
		
		long tIn1 = System.nanoTime();
		tlp.nftlp();
		long tOut1 = System.nanoTime();
		
		
		long tIn2 = System.nanoTime();
		tlp.bftlp();
		long tOut2 = System.nanoTime();
		
		System.out.println("Next-Fit time taken = " + (tOut1 - tIn1) + "ns");
		System.out.println("Best-Fit time taken = " + (tOut2 - tIn2) + "ns");
	}
	
}
