package edu.smith.cs.csc212.p4;

public class LockedExit extends Exit {

	public LockedExit(String target, String description) {
		super(target, description);
		
		String blah = this.getTarget();
		System.out.println(blah + " blah");
		if (Clue.items.contains(blah)) {
			System.out.println ("Good job");
		} else {
			System.out.println ("Sorry, you don't have the right key.");
		}
	}

}
