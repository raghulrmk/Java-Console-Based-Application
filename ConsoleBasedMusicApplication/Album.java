package ConsoleBasedMusicApplication;

import java.util.*;
import java.util.concurrent.locks.*;


public class Album {
	private String name;
	private String artist;
	private ArrayList<Song>songList;
	private ReentrantLock lock=new ReentrantLock();
	
	public Album(String name, String artist) {
		this.name=name;
		this.artist=artist;
		this.songList=new ArrayList<>();
	}
	
	private boolean findSongTitle(String title) {
		if(songList.size()!=0) {
			for(Song song:this.songList) {
				if(song.getSongName().equals(title)) return false;
			}
		}
		return true;
		
		
	}
	
	public boolean addSongToAlbum(String title, double duration) {
		
		lock.lock();
		int loop=0;
		try {
			if(findSongTitle(title)) {this.songList.add(new Song(title,duration));loop++;}
		} finally {
			lock.unlock();
		    
		}
		if(loop==0) return false;
		return true;
	}
	public String getArtistName() {
		return this.artist;
	}
	
	public Song getSong(int tokenId) {
		
		return songList.get(tokenId);
	}
}
