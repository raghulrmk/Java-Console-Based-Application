package ConsoleBasedMusicApplication;

import java.util.*;



public class Song {
	private String name;
	private double duration;
	
	public Song(String name, double duration) {
		
		this.name=name;
		this.duration=duration;
	}
	
	public String getSongName() {
		
		return this.name;
	}
	public double getSongDuration() {
		
		return this.duration;
	}
	
	@Override 
	public String toString() {
		return "Song name:" +this.name+" Duration: "+duration;
	}
}
