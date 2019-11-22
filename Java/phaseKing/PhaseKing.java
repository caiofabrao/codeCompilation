package phaseKing;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhaseKing {

	private int[] votes;
	private int tiebreaker;
	
	public PhaseKing(int id, int GROUP_SIZE){
		// Binary vote
		votes = new int[2];
		
		// Start PhaseKing
		Random rand = new Random();
		int vote = rand.nextInt(2);

		int phases = GROUP_SIZE;
		int faults = phases/4;
		
		// Execution Milestone
		System.out.println("Starting Phase King. Vote: "+vote);

		for (int p = 1; p <= faults+1; p++) {
			// Round 1  			
			// Sending vote

			// Wait for all the votes
			while (votes[0]+votes[1] < GROUP_SIZE) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException ex) {
					Logger.getLogger(PhaseKing.class.getName()).log(Level.SEVERE, null, ex);
				}
			}

			// Calculate Majority
			// Default value: 0
			int majority = 0;
			int mult = 0;
			if (votes[1] > GROUP_SIZE/2) {
				mult = votes[1];
				majority = 1;
			} else if (votes[0] > GROUP_SIZE/2) {
				mult = votes[0];
			} else {
				System.out.println("[Phase King] Default Majority");
			}

			// Reset the votes
			votes = new int[2];

			// Execution Milestone
			System.out.println("[Phase King] Majority: "+majority);

			// Round 2
			if (id == p) {
				// Send majority as tiebreaker
				
				// Set your tiebreaker
				tiebreaker = majority;
				// Execution Milestone
				System.out.println("[Phase King] I am the King. Tiebreaker: "+majority);
			} else {
				// Get the tiebreaker from PhaseKing
				tiebreaker = 0;
			}
			
			// Update vote
			if (mult > GROUP_SIZE/2 + faults) {
				vote = majority;
				System.out.println("[Phase King] Majority by me");
			} else {
				vote = tiebreaker;
				System.out.println("[Phase King] Majority by Tiebreaker");
			}

			// Execution Milestone
			System.out.println("[Phase King] New vote: "+vote);

			// Delay to wait for the others Processes to complete theirs calculation
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				Logger.getLogger(PhaseKing.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
