package edu.smith.cs.csc212.p4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This represents a place in our text adventure.
 * @author jfoley
 *
 */
public class Place {
	/**
	 * This is a list of places we can get to from this place.
	 */
	private List<Exit> exits;
	/**
	 * This is the identifier of the place.
	 */
	private String id;
	/**
	 * What to tell the user about this place.
	 */
	private String description;
	/**
	 * Whether reaching this place ends the game.
	 */
	private boolean terminal;
	
	private Boolean hasBody;
	
	public Boolean hasFKey;
	
	public Boolean hasCKey;
	
	
	/**
	 * Internal only constructor for Place. Use {@link #create(String, String, Boolean)} or {@link #terminal(String, String, Boolean)} instead.
	 * @param id - the internal id of this place.
	 * @param description - the user-facing description of the place.
	 * @param terminal - whether this place ends the game.
	 */
	private Place(String id, String description, boolean terminal, Boolean hasBody, Boolean hasFKey, Boolean hasCKey) {
		this.id = id;
		this.description = description;
		this.exits = new ArrayList<>();
		this.terminal = terminal;
		this.hasBody = hasBody;
		this.hasFKey = hasFKey;
		this.hasCKey = hasCKey;
	}
	
	/**
	 * Create an exit for the user to navigate to another Place.
	 * @param exit - the description and target of the other Place.
	 */
	public void addExit(Exit exit) {
		this.exits.add(exit);
	}
	
	public void addLockedExit(LockedExit lockedExit) {
		this.exits.add(lockedExit);
	}
	
	/**
	 * For gameplay, whether this place ends the game.
	 * @return true if this is the end.
	 */
	public boolean isTerminalState() {
		return this.terminal;
	}
	
	/**
	 * The internal id of this place, for referring to it in {@link Exit} objects.
	 * @return the id.
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * The narrative description of this place.
	 */	
	public void printDescription() {
		System.out.println(this.description);
		if (this.hasBody) {
			System.out.println(Clue.foundBoddy);
		}
		if (this.hasFKey && !Clue.hasFrontKey) {
			System.out.println("You spot the key to the front door lying on the ground.");
		}
		if (this.hasCKey && !Clue.hasCabinetKey) {
			System.out.println("You spot the key to the weapons cabinet lying on the ground.");
		}
		System.out.println (Clue.time);
		
		
	}
//	public String getDescription() {
//		return this.description;
//	}

	/**
	 * Get a view of the exits from this Place, for navigation.
	 * @return all the exits from this place.
	 */
	public List<Exit> getVisibleExits() {
		return Collections.unmodifiableList(exits);
	}
	
	/**
	 * This is a terminal location (good or bad).
	 * @param id - this is the id of the place (for creating {@link Exit} objects that go here).
	 * @param description - this is the description of the place.
	 * @return the Place object.
	 */
	public static Place terminal(String id, String description, Boolean hasBody, Boolean hasFKey, Boolean hasCKey) {
		return new Place(id, description, true, hasBody, hasFKey, hasCKey);
	}
	
	/**
	 * Create a place with an id and description.
	 * @param id - this is the id of the place (for creating {@link Exit} objects that go here).
	 * @param description - this is what we show to the user.
	 * @return the new Place object (add exits to it).
	 */
	public static Place create(String id, String description, Boolean hasBody, Boolean hasFKey, Boolean hasCKey) {
		return new Place(id, description, false, hasBody, hasFKey, hasCKey);
	}
	
	/**
	 * Implements what we need to put Place in a HashSet or HashMap.
	 */
	public int hashCode() {
		return this.id.hashCode();
	}
	
	/**
	 * Give a string for debugging what place is what.
	 */
	public String toString() {
		return "Place("+this.id+" with "+this.exits.size()+" exits.)";
	}
	
	/**
	 * Whether this is the same place as another.
	 */
	public boolean equals(Object other) {
		if (other instanceof Place) {
			return this.id.equals(((Place) other).id);
		}
		return false;
	}
	
}
