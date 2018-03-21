package algorithmsProject;

import java.util.ArrayList;

public class AlgorithmsProject {

	public static void main(String[] args) {
		Database_AlgorithmProject database = new Database_AlgorithmProject("newlists.csv");
		
		System.out.println("\nThere are " + database.getTotalEventNum() + " unique events with 2 or more people.");
		System.out.println("The events contained " + database.getMaximumIndividualNumber() + " unique individuals.");
		System.out.println("The minimum number of co-occurances to be suspicious is " + database.getMinCoOccurances() + ".");
		
		System.out.println("\nThe following individuals were present at the same events more than the minimum number of times to be suspicious:");
		
		ArrayList<int[]> coOccuranceList = new ArrayList<int[]>(database.determineSuspiciousCoOccurances());
		
		for(int count = 0; count < coOccuranceList.size(); count++){
			System.out.println("Individual " + coOccuranceList.get(count)[0] + " and Individual " + coOccuranceList.get(count)[1] + " share events " + coOccuranceList.get(count)[2] + " times.");
		}
	}//end main
}//end class
