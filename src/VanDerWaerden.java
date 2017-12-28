import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class VanDerWaerden {
	public static void main (String[] args) {
		Scanner reader = new Scanner(System.in);
		
		System.out.print("Enter number of colors: ");
		int r = reader.nextInt();
		System.out.print("Enter length of arithmetic progression: ");
		int l = reader.nextInt();
		
		HashMap<ArrayList<Integer>, Boolean> coloringsToContainsAP = new HashMap<ArrayList<Integer>, Boolean>();
		coloringsToContainsAP.put(new ArrayList<Integer>(), false);
		boolean foundLength = false;
		int len = 0;
		
		while (!foundLength) {
			len++;
			coloringsToContainsAP = getNextColorings(coloringsToContainsAP, r, l);
			foundLength = true;
			
			for (ArrayList<Integer> coloring : coloringsToContainsAP.keySet()) {
				if (!coloringsToContainsAP.get(coloring)) {
					foundLength = false;
					break;
				}
			}
		}
		
		System.out.println("The Van der Waerden number V(" + l + ", " + r + ") is " + len);
		reader.close();
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<ArrayList<Integer>, Boolean> getNextColorings(HashMap<ArrayList<Integer>, Boolean> coloringsToContainsAP, int r, int l) {
		HashMap<ArrayList<Integer>, Boolean> newColorings = new HashMap<ArrayList<Integer>, Boolean>();
		boolean alreadyContainsAP = true;
		
		for (ArrayList<Integer> c : coloringsToContainsAP.keySet()) {
			alreadyContainsAP = coloringsToContainsAP.get(c);
			for (int i = 1; i <= r; i++) {
				c.add(0, i);
				if (alreadyContainsAP) {
					newColorings.put((ArrayList<Integer>)c.clone(), true);
				} else {
					newColorings.put((ArrayList<Integer>)c.clone(), containsAPFromFirst(c, l));
				}
				c.remove(0);
			}
		}
		
		return newColorings;
	}
	
	public static boolean containsAPFromFirst(ArrayList<Integer> coloring, int l) {
		boolean containsAP = false;
		int maxStep = (coloring.size() - 1) / (l - 1);
		int start = coloring.get(0);
		
		for (int step = 1; step <= maxStep; step++) {
			containsAP = true;
			
			for (int i = 1; i < l; i++) {				
				if (coloring.get(step * i) != start) {
					containsAP = false;
					break;
				}
			}
			
			if (containsAP) break;
		}
		
		return containsAP;
	}
}
