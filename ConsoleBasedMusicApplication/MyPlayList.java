package ConsoleBasedMusicApplication;

import java.util.*;
import java.util.concurrent.locks.*;



public class MyPlayList implements Cloneable {
	
	private LinkedList<Song>play_List;
	private ReentrantLock lock=new ReentrantLock();
	
	public MyPlayList() {
		this.play_List=new LinkedList<>();
	}
	
	private  boolean isNotExist(Song song) {
		if(play_List.size()!=0) {
		for(Song songs:play_List) {
			if(songs.equals(song)) return false;
		}
		}
		return true;
	}
	
	public boolean addSongToPlayList(Song song) {
		lock.lock();
		int loop=0;
		try {
			if(isNotExist(song)) {this.play_List.add(song); loop++;}
		} finally {
			lock.unlock();
		}
		if(loop==0) return false;
		return true;
	}
	public void play() {
		if(play_List.size()==0) {
			System.out.println("Your PlayList is empty...");
		}
		ListIterator<Song>songTraversal=play_List.listIterator();
		System.out.println("Now playing..."+songTraversal.next().toString());
		printMenu();
		boolean quit=true;
		while(quit) {
			System.out.println("Enter the options");
			Scanner in=new Scanner(System.in);
			int n=in.nextInt();
			switch(n) {
			 case 0:
				 System.out.println("Shutting Down");
				 try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 System.out.println("ShutDowned the PlayList....");
				 quit=false;
				 break;
			 case 1:
				 if(songTraversal.hasNext()) {
					 System.out.println("Now Playing...."+songTraversal.next().toString());
					 try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
				 } else {
					 System.out.println("oops,sorry you are playlist has been completed! what are you going to do next");
					 printMenu();
				 }
				 break;
			 case 2: 
				 if(songTraversal.hasPrevious()) {
					 System.out.println("Now Playing...."+songTraversal.previous().toString());
					 try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
				 } else {
					 System.out.println("oops,sorry there is no previous song.....");
					 printMenu();
				 }
				 break;
			 case 3:
				 if(songTraversal.hasNext()) {
					 songTraversal.next();
					 System.out.println("Now playing...."+songTraversal.previous());
				 } else if(songTraversal.hasPrevious()) {
					 songTraversal.previous();
					 System.out.println("Now Playing...."+songTraversal.next());
				 } else {
					 System.out.println("There are no other songs");
				 }
				 break;
			
			case 4: 
				songList();
				break;
			case 5:
				printMenu();
				break;
			case 6:
				songTraversal.remove();
				System.out.println("Deleting....");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Deleted the current song...");
		}
	   }
		
	}
	private void printMenu() {
		System.out.println("Availbale options");
		System.out.print("0:Quit\n 1:Play next song\n 2:Play Previous Song\n 3: Replay\n 4:Get the List of all songs\n 5:Print the options\n 6: Delete current song from the playList\n");
		
	}
	private void songList() {
		Iterator<Song>songs=play_List.iterator();
		System.out.println("Available songs loading...");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(songs.hasNext()) {
			System.out.println(songs.next().toString());
		}
	}
	@Override
	public Object clone() throws CloneNotSupportedException{
		MyPlayList my=(MyPlayList) super.clone();
		
		my.play_List=new LinkedList<>();
		for(Song songs:this.play_List) {
			my.play_List.add(songs);
		}
		return my;
	}
	
	
}
