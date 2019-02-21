package edu.smith.cs.csc212.p4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Clue implements GameWorld{
	/**
	 * List of items the player has.
	 */
	public static List<String> items = new ArrayList<>();

	 
	 /**
	  * List of items that can be found in the mansion.
	  */
	 public static List<String> mansionItems = new ArrayList<>();
	 
	 
	/**
	 * List of rooms in the game.
	 */
	String[] ROOMS = new String[] {
			"entranceHall1",
			"entranceHall2",
			"lounge",
			"study",
			"bathroom",
			"diningRoom",
			"billiardRoom",
			"kitchen",
			"attic",
			"cellar",
			"conservatory"
	};
	
	/**
	 * List of potential murder weapons.
	 */
	String[] WEAPONS = new String[] {
			"candlestick",
			"dagger",
			"revolver",
			"lead pipe",
			"rope",
			"wrench"
	};
	
	/**
	 * List of suspects. 
	 */
	String[] SUSPECTS = new String[] {
			"Miss Scarlet",
			"Mrs. White",
			"Mrs. Peacock",
			"Professor Plum",
			"Colonel Mustard",
			"Wadsworth"
	};
	
	//Defines the suspects in the mansion.
	Place scarlet;
	Place white;
	Place peacock;
	Place plum;
	Place mustard;
	Place wadsworth;
	
	//What will be output when the player enters the room with Mr. Boddy's body.
	public static String foundBoddy = "You've found Mr. Boddy! "
			+ "Remember which room you found him in for when you report back to Hoover.";
	
	//What will be output when the player interacts with the killer.
	String foundKiller;
	
	//Which weapon is the murder weapon.
	String murderWeapon;
	
	//TODO: comment everything
	
	public static String frontKey = "key to the front door";
	public static String cabinetKey = "key to the weapons cabinet";

	
	//Whether or not the player has the front door key.
	public static Boolean hasFrontKey = false;
	public static Boolean hasCabinetKey = false;
	
	String foundFKey = "The key to the front door is on the ground.";
	
	/**
	 * Keeps track of all of the places
	 */
	private Map<String, Place> places = new HashMap<>();
	
	/**
	 * Keeps track of which is the murder weapon.
	 */
	private Map<String, Boolean> mWeapon = new HashMap<>();
	
	/**
	 * Keeps track of which room has Mr. Boddy's body.
	 */
	private Map<String, Boolean> roomBoddy = new HashMap<>();
	
	/**
	 * Keeps track of which room has the key to the front door.
	 */
	public static Map<String, Boolean> roomFKey = new HashMap<>();
	
	/**
	 * Keeps track of which room has the key to the weapons cabinet.
	 */
	public static Map<String, Boolean> roomCKey = new HashMap<>();
	
	/**
	 * Keeps track of who the guilty suspect is.
	 */
	private Map<String, Boolean> suspects = new HashMap<>();
	
	public static GameTime time = new GameTime();

	/**
	 * Where should the player start?
	 */
	@Override
	public String getStart() {
		System.out.println ("\nWelcome to the mansion. \n"
				+ "You are Mr. Green, an FBI agent sent by your boss, J. Edgar Hoover, to investigate the death of Mr. Boddy. \n"
				+ "Your goal is to find Mr. Boddy's body, and to uncover who killed him and with what. \n"
				+ "When you're ready, exit the mansion through the front door to tell Hoover what you've found.\n");
		return "entranceHall1";
	}

	/**
	 * This constructor builds the Clue game.
	 */
	public Clue() {
		//Adds the two keys to mansionItems
		mansionItems.add (frontKey);
		mansionItems.add(cabinetKey);
		
		
		Random rand = new Random();
		
		//Generates a random number that will correspond to the index of the weapon
		//that was used to commit the murder.
		final int weaponN = rand.nextInt(WEAPONS.length-1);
		
		//Generates a random number that will correspond to the index of the room
		//that will contain Mr. Boddy's body, excluding entrance halls and lounge.
		final int mrBoddyN = rand.nextInt(ROOMS.length-4)+3;
		
		//Generates a random number that will correspond to the index of the room with the front door key,
		//excluding entrance halls and lounge.
		final int fKeyN = 8; //rand.nextInt(ROOMS.length-4)+3;
		
		//Generates a random number that will correspond to the index of the room with the weapons cabinet key,
		//excluding entrance halls and lounge.
		final int cKeyN = 5; //rand.nextInt(ROOMS.length-4)+3;
		
		//Generates a random number that will correspond to the index of the guilty character.
		final int whodunnit = rand.nextInt(SUSPECTS.length-1);
		

		
		//This adds all of the weapons and whether or not they are the murder weapon to mWeapon.
		for (int i = 0; i <= WEAPONS.length-1; i++) {
			String weapon = WEAPONS[i];
			if (i == weaponN) {
				mWeapon.put (weapon, true);
				murderWeapon = weapon;
			} else {
				mWeapon.put(weapon, false);
			}
		}
		
		//This adds all of the places and whether or not they contain the body to roomBoddy,
		//and all off the places and whether or not they contain the key to the front door to roomFKey.
		roomBoddy.put("entranceHall1", false);
		roomBoddy.put("entranceHall2", false);
		roomBoddy.put("lounge", false);
		roomFKey.put("entranceHall1", false);
		roomFKey.put("entranceHall2", false);
		roomFKey.put("lounge", false);
		roomCKey.put("entranceHall1", false);
		roomCKey.put("entranceHall2", false);
		roomCKey.put("lounge", false);
				for (int i = 3; i <= ROOMS.length-1; i++) {
					String room = ROOMS[i];
					if (i == mrBoddyN) {
						roomBoddy.put (room, true);
					} else if (i != mrBoddyN){
						roomBoddy.put(room, false);
					}
					if (i == fKeyN) {
						roomFKey.put(room, true);
					} else if (i != fKeyN) {
						roomFKey.put(room, false);
					}
					if (i == cKeyN) {
						roomCKey.put(room, true);
					} else if (i != cKeyN) {
						roomCKey.put(room, false);
					}
				}

		//This adds all of the characters and whether or not they're guilty to suspects.
				for (int i = 0; i <= SUSPECTS.length-1; i++) {
					String person = SUSPECTS[i];
					if (i == whodunnit) {
						suspects.put (person, true);
						//Updates foundKiller to print the name of the murderer and that they're suspicious.
						foundKiller = person + " is acting strangely guilty.";
					} else {
						suspects.put(person, false);
					}
				}
		
		//TODO: add comments to all of the room creations.		
		Place entranceHall1 = insert(
				Place.create("entranceHall1", "You are in the grand entrance hall of the mansion.\n"
						+ "The front door is locked.", false, false, false));
		entranceHall1.addExit(new Exit ("entranceHall2", "Continue further into the entrance hall."));
		entranceHall1.addExit(new Exit("cellar", "There are stairs leading down."));
		entranceHall1.addExit(new Exit("attic", "There are stairs leading up."));
		entranceHall1.addExit(new Exit ("bathroom", "There is a nondescript door."));
		entranceHall1.addLockedExit(new LockedExit("front door", "Talk to Hoover"));
		//TODO: make the front door a locked exit
		


		Place entranceHall2 = insert(
				Place.create("entranceHall2", "You are further into the grand hall of the mansion.", false, false, false));
		entranceHall2.addExit(new Exit ("entranceHall1", "Return to the front part of the entrance hall."));
		entranceHall2.addExit(new Exit("diningRoom", "There is an archway leading to the dining room."));
		entranceHall2.addExit(new Exit("study", "There is a rather decorative door."));
		entranceHall2.addExit(new Exit ("lounge", "There is an open door. Through which you can see the dinner party guests socalizing in the lounge."));
		
		Place frontDoor = insert(Place.create("front door","If you go out this door, you'll have to answer to Hoover. "+
				"Hopefully you have all of the information you need.", 
				false, false, false));
		frontDoor.addExit(new Exit ("entranceHall1", "Stay inside."));
		//TODO: add a locked exit
		
		
		//TODO: add a version of the attic at night, maybe like 
		//"it's so pitch-black that you can't even see your hand in front of your face"
		Place attic = insert(
				Place.create("attic", "You are in a dark, dusty attic."
						+ "There is a bit of sunlight shining in through the rafters that helps you see.", 
						roomBoddy.get("attic"), roomFKey.get("attic"), roomCKey.get("attic")
						));
		attic.addExit(new Exit ("entranceHall1", "There are stairs leading down."));
		attic.addExit(new Exit ("littleRoom", "There is a nondescript door in the corner of the attic."));
		
		Place littleRoom = insert(Place.terminal("littleRoom","As you shut the door, you hear it lock behind you. "+
							"There are no windows, and there's no way out. You're trapped.", false, false, false));
		
		Place cellar = insert(
				Place.create("cellar", "You have found the cellar of the mansion.\n" + 
		                           "It is darker down here.", roomBoddy.get("cellar"), roomFKey.get("cellar"), roomCKey.get("cellar")
						));
		cellar.addExit(new Exit("entranceHall1", "There are stairs leading up."));
		
		//TODO: add a night mode to the dining room; light the candles
		Place diningRoom = insert(
				Place.create("diningRoom", "You are in a lavish dining room.\n" + 
		                           "The table is beautifully set, though the candles have not been lit, as it is still daylight.", 
						roomBoddy.get("diningRoom"), roomFKey.get("diningRoom"), roomCKey.get("diningRoom")));
		diningRoom.addExit(new Exit("entranceHall2", "There is an archway leading to the entrance hall."));
		diningRoom.addExit(new Exit("kitchen", "There are almost-industrial-looking double swing doors."));
		
		Place kitchen = insert(
				Place.create("kitchen", "You have entered a very tidy and professional-looking kitchen.", 
						roomBoddy.get("kitchen"), roomFKey.get("kitchen"), roomCKey.get("kitchen")
						));
		kitchen.addExit(new Exit("diningRoom", "There are almost-industrial-looking double swing doors."));
		
		//TODO: create night mode for the conservatory
		Place conservatory = insert(
				Place.create("conservatory", "The sunlight streaming in from the conservatory windows\n" 
						+"casts itself beautifully onto the various plants.", roomBoddy.get("conservatory"),
						roomFKey.get("conservatory"), roomCKey.get("conservatory")
						));
		conservatory.addExit(new Exit("study", "There is a decorative door."));
		
		Place study = insert(
				Place.create("study", "The study is filled to the brim with books and cozy armchairs.", 
						roomBoddy.get("study"), roomFKey.get("study"), roomCKey.get("study")
						));
		study.addExit(new Exit("entranceHall2", "There is a rather decorative door."));
		study.addExit(new Exit("conservatory", "There is a glass door."));
		
		
		Place billiardRoom = insert(
				Place.create("billiardRoom", "The billiard room is mostly empty, save for a pool table in the middle.", 
						roomBoddy.get("billiardRoom"), roomFKey.get("billiardRoom"), roomCKey.get("billiardRoom")
						));
		billiardRoom.addExit(new Exit("lounge", "There is a fairly decorative door."));
		
		
		Place bathroom = insert(
				Place.create("bathroom", "You're standing in a standard bathroom that seems small for the size of the house.", 
						roomBoddy.get("bathroom"), roomFKey.get("bathroom"), roomCKey.get("bathroom")));
		bathroom.addExit(new Exit("entranceHall1", "There is a nondescript door."));
		bathroom.addExit(new Exit("lounge", "There is a door labeled: 'One-way exit. Door cannot be opened from the other side'."));
		
		//TODO: add a night mode to the lounge; make everyone tired as the night wears on
		Place lounge = insert(
					Place.create("lounge", "You are in a cozy, yet high-end lounge.\n" + 
			                           "All of the dinner party guests are in here, engaged in lively conversation.", false, false,
			                           false));
		lounge.addExit(new Exit("entranceHall2", "There is an open door leading to the entrance hall."));
		lounge.addExit(new Exit("billiardRoom", "There is a fairly decorative door."));
		lounge.addExit(new Exit ("conversation", "Speak to the guests"));
		
		Place conversation = insert (
					Place.create("conversation", "Who would you like to talk to?", false, false, false));
		conversation.addExit(new Exit ("scarlet", SUSPECTS[0]));
		conversation.addExit(new Exit ("white", SUSPECTS[1]));
		conversation.addExit(new Exit ("peacock", SUSPECTS[2]));
		conversation.addExit(new Exit ("plum", SUSPECTS[3]));
		conversation.addExit(new Exit ("mustard", SUSPECTS[4]));
		conversation.addExit(new Exit ("wadsworth", SUSPECTS[5]));
		conversation.addExit(new Exit ("lounge", "Stop socializing and get back to your investigation"));
		
		if (suspects.get("Miss Scarlet")) {
			scarlet = insert(
					Place.create("scarlet", "When you approach Miss Scarlet, you realize\n" + 
							"she's in the middle of a heated discussion with Wadsworth.\n" +
							"Miss Scarlet: 'That's not six.'\n" +
			                "Wadsworth: 'One plus two plus two plus one.'\n"+ 
							"Miss Scarlet: 'A-ha! There was only one that got the chandelier. \n"
							+ "That's one plus two plus one plus one!'\n" + 
							"Wadsworth: 'Even if you were right, that would be one plus one plus TWO plus one.'\n"+
							"Miss Scarlet: 'Ok, fine. One plus two plus one...'\n"+
							"You walk away."+foundKiller, false, false, false));
		} else {
			scarlet = insert(
					Place.create("scarlet", "When you approach Miss Scarlet, you realize\n" + 
	                           "she's in the middle of a heated discussion with Wadsworth.\n" + 
					"Miss Scarlet: 'That's not six.'\n" +
	                "Wadsworth: 'One plus two plus two plus one.'\n"+ 
					"Miss Scarlet: 'A-ha! There was only one that got the chandelier. \n"
					+ "That's one plus two plus one plus one!'\n" + 
					"Wadsworth: 'Even if you were right, that would be one plus one plus TWO plus one.'\n"+
					"Miss Scarlet: 'Ok, fine. One plus two plus one...'\n"+
					"You walk away.", false, false, false));
		}
		scarlet.addExit(new Exit("conversation", "Talk to another guest."));
		
		if (suspects.get("Mrs. White")) {
			white = insert(
					Place.create("white", "Mrs. White: 'It-it- the f - it -flam - flames. Flames, on the side of my face, \n"
							+"breathing-breathl- heaving breaths. Heaving breaths... Heaving...\n'" +foundKiller, false, false, false));
		} else {
			white = insert(
					Place.create("white", "Mrs. White: 'It-it- the f - it -flam - flames. Flames, on the side of my face, \n"
							+"breathing-breathl- heaving breaths. Heaving breaths... Heaving...'", false, false, false));
		}
		white.addExit(new Exit("conversation", "Talk to another guest."));
		
		if (suspects.get("Mrs. Peacock")) {
			peacock = insert(
					Place.create("peacock", "As you begin to approach her, Mrs. Peacock drops her glass and begins screaming hysterically.\n" 
							+ "You try to calm her, but nothing works, so you resort to slapping her across the face.\n"
							+"She stops screaming, and you walk away as if nothing happened.\n"+foundKiller, false, false, false));
		} else {
			peacock = insert(
					Place.create("peacock", "As you begin to approach her, Mrs. Peacock drops her glass and begins screaming hysterically.\n" 
							+ "You try to calm her, but nothing works, so you resort to slapping her across the face.\n"
							+"She stops screaming, and you walk away as if nothing happened.\n", false, false, false));
		}
		peacock.addExit(new Exit("conversation", "Talk to another guest."));
		
		if (suspects.get("Professor Plum")) {
			plum = insert(
					Place.create("plum", "Professor Plum: 'It's you and me, honey bunch.'\n" +foundKiller, false, false, false));
		} else {
			plum = insert(
					Place.create("plum", "Professor Plum: 'It's you and me, honey bunch.'", false, false, false));
		}
		plum.addExit(new Exit("conversation", "Talk to another guest."));
		
		if (suspects.get("Colonel Mustard")) {
			mustard = insert(
					Place.create("mustard", "Colonel Mustard: 'If I was the killer, I'd kill you next!'\n"
							+"The room falls silent.\n" +"Colonel Mustard:'If! I said IF!'"+foundKiller, false, false, false));
		} else {
			mustard = insert(
					Place.create("mustard", "Colonel Mustard: 'If I was the killer, I'd kill you next!'\n"
							+"The room falls silent.\n" +"Colonel Mustard:'If! I said IF!'", false, false, false));
		}
		mustard.addExit(new Exit("conversation", "Talk to another guest."));
		
		if (suspects.get("Wadsworth")) {
			wadsworth = insert(
					Place.create("wadsworth", "Wadsworth: 'I'm merely a humble butler. I buttle, sir.'\n" +foundKiller, false, false, false));
		} else {
			wadsworth = insert(
					Place.create("wadsworth", "Wadsworth: 'I'm merely a humble butler. I buttle, sir.'", false, false, false));
		}
		wadsworth.addExit(new Exit("conversation", "Talk to another guest."));
		
		//TODO: create a class SecretExit that extends Exit
		//TODO: implement keys and lockedExit
		//TODO: create a time system
		
//		Place takeWeapon = insert(
//				Place.create("takeWeapon", "You have taken the weapon."));
//		takeWeapon.addExit(new Exit("cellar", "Return to cellar."));

		
		// Make sure your graph makes sense!
		checkAllExitsGoSomewhere();
	}

	/**
	 * This helper method saves us a lot of typing. We always want to map from p.id
	 * to p.
	 * 
	 * @param p - the place.
	 * @return the place you gave us, so that you can store it in a variable.
	 */
	private Place insert(Place p) {
		places.put(p.getId(), p);
		return p;
	}
	
	/**
	 * I like this method for checking to make sure that my graph makes sense!
	 */
	private void checkAllExitsGoSomewhere() {
		boolean missing = false;
		// For every place:
		for (Place p : places.values()) {
			// For every exit from that place:
			for (Exit x : p.getVisibleExits()) {
				// That exit goes to somewhere that exists!
				if (!places.containsKey(x.getTarget())) {
					// Don't leave immediately, but check everything all at once.
					missing = true;
					// Print every exit with a missing place:
					System.err.println("Found exit pointing at " + x.getTarget() + " which does not exist as a place.");
				}
			}
		}
		
		// Now that we've checked every exit for every place, crash if we printed any errors.
		if (missing) {
			throw new RuntimeException("You have some exits to nowhere!");
		}
	}

	/**
	 * Get a Place object by name.
	 */
	public Place getPlace(String id) {
		return this.places.get(id);		
	}
}

