package edu.smith.cs.csc212.p4;

import java.util.List;

/**
 * This is our main class for P4. It interacts with a GameWorld and handles user-input.
 * @author jfoley
 *
 */
public class InteractiveFiction {

	/**
	 * This is where we play the game.
	 * @param args
	 */
	
	static GameWorld game;
	
	public static void main(String[] args) {
		// This is a text input source (provides getUserWords() and confirm()).
		TextInput input = TextInput.fromArgs(args);

		// This prompts the user to choose a game.
		System.out.println ("Hello! What game would you like to play: \n"
				+ "'Spooky Mansion' or 'Clue'?");
		
		// This takes the user's input and chooses the correct game accordingly.
		while (game == null) {
			List<String> games = input.getUserWords(">");
			String gameChoice = games.get(0).toLowerCase().trim();
			// Spooky Mansion starts if the first word of the user's input is "spooky"
			if (gameChoice.contentEquals("spooky")) {
				game = new SpookyMansion();
			// Clue starts if the first word of the user's input is "clue"
			} else if (gameChoice.contentEquals("clue")) {
				game = new Clue();
			//If the user types something other than the name of a game, it will prompt them to input something else.
			} else {
				System.out.println ("You must choose one of the games!");
				continue;
			}
		}
		
		
		// This is the current location of the player (initialize as start).
		// Maybe we'll expand this to a Player object.
		String place = game.getStart();

		// Play the game until quitting.
		// This is too hard to express here, so we just use an infinite loop with breaks.
		while (true) {
			// Print the description of where you are.
			Place here = game.getPlace(place);
			here.printDescription();

			// Game over after print!
			if (here.isTerminalState()) {
				break;
			}

			// Show a user the ways out of this place.
			List<Exit> exits = here.getVisibleExits();
			
			for (int i=0; i<exits.size(); i++) {
			    Exit e = exits.get(i);
				System.out.println(" ["+i+"] " + e.getDescription());
			}

			// Figure out what the user wants to do, for now, only "quit" is special.
			List<String> words = input.getUserWords(">");
			if (words.size() == 0) {
				System.out.println("Must type something!");
				continue;
			} else if (words.size() > 1) {
				System.out.println("Only give me 1 word at a time!");
				continue;
			}
			
			// Get the word they typed as lowercase, and no spaces.
			String action = words.get(0).toLowerCase().trim();
			

			if (action.equals("quit") || action.equals("escape") || action.equals("q")){
				if (input.confirm("Are you sure you want to quit?")) {
					break;
				} else {
					continue;
				}
			}
			
			//TODO: caption this
			//TODO: ask why mansionItems prints when user types "take"
			//TODO: debug this too rip
			if (action.equals("take")) {
				String room = here.getId();
				if (Clue.mansionItems.size() == 0) {
					System.out.println("There's nothing to take!");
					continue;
				} else if (Clue.roomFKey.get(room) == false && Clue.roomCKey.get(room) == false) {
					System.out.println("There's nothing to take!");
					continue;
				} else if (Clue.roomFKey.get(room) && Clue.mansionItems.contains(Clue.frontKey)){
					if (Clue.roomCKey.get(room) && Clue.mansionItems.contains(Clue.cabinetKey)) {
						if (input.choose("Do you want to take the key to the front door or the key to the weapons cabinet?") == "front") {
							Clue.items.add(Clue.frontKey);
							Clue.mansionItems.remove(Clue.frontKey);
							Clue.hasFrontKey = true;
							System.out.println ("The key to the front door is now in your inventory. Type 'stuff' to see your inventory.\n");
							continue;
						} else {
							Clue.items.add(Clue.cabinetKey);
							Clue.mansionItems.remove(Clue.cabinetKey);
							Clue.hasCabinetKey = true;
							System.out.println ("The key to the weapons cabinet is now in your inventory. Type 'stuff' to see your inventory.\n");
							continue;
						}
					} else {
						System.out.println (Clue.mansionItems);
						if (input.confirm("Are you sure you want to take the key to the front door?")) {
							Clue.items.add(Clue.frontKey);
							Clue.mansionItems.remove(Clue.frontKey);
							Clue.hasFrontKey = true;
							System.out.println ("The key to the front door is now in your inventory. Type 'stuff' to see your inventory.\n");
							continue;
						}
					}
					
				} else if (Clue.roomCKey.get(room) && Clue.mansionItems.contains(Clue.cabinetKey)){
					} if (input.confirm("Are you sure you want to take the key to the weapons cabinet?")) {
						Clue.items.add(Clue.cabinetKey);
						Clue.mansionItems.remove(Clue.cabinetKey);
						Clue.hasCabinetKey = true;
						System.out.println ("The key to the weapons cabinet is now in your inventory. Type 'stuff' to see your inventory.\n");
						continue;
				} else {
					System.out.println ("There's nothing to take!");
					continue;
					}
			}
			
			//TODO: comment this
			if (action.equals("stuff")) {
				if (Clue.items.size() != 0) {
					System.out.println ("Inventory: " + Clue.items);
					continue;
				} else if (Clue.items.isEmpty()) {
					System.out.println ("Your inventory is empty.");
					continue;
				}
				
			}
			
			// From here on out, what they typed better be a number!
			Integer exitNum = null;
			try {
				exitNum = Integer.parseInt(action);
			} catch (NumberFormatException nfe) {
				System.out.println("That's not something I understand! Try a number!");
				continue;
			}
			
			if (exitNum < 0 || exitNum > exits.size()) {
				System.out.println("I don't know what to do with that number!");
				continue;
			}

			// Move to the room they indicated.
			Exit destination = exits.get(exitNum);
			place = destination.getTarget();
		}

		// You get here by "quit" or by reaching a Terminal Place.
		System.out.println(">>> GAME OVER <<<");
	}

}
