package edu.smith.cs.csc212.p4;

public class GameTime {
	   int hour;

	    public GameTime() {
	    	this.hour = 0;
	    }


	    int getHour(){
	    	return this.hour;
	    }

	    void increaseHour(){
	    	if (this.hour < 23) {
	    		this.hour++;
	    	} else if (this.hour == 23) {
	    		this.hour = 0;
	    	}
	    }
 
	   public String toString() {
		   return this.hour+":00";
	    }
}
