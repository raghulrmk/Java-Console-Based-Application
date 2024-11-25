package ConsoleBasedMusicApplication;

import java.util.concurrent.*;
import java.util.*;


public class main {
    static  CountDownLatch albumLatch=new CountDownLatch(3);
	static CyclicBarrier barrier=new CyclicBarrier(3, () -> {
		System.out.println("Here songs are added to their respective album");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Result Loading...");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	});
	private static ArrayList<Album>albums=new ArrayList<>();
	
	public static void main(String a[]) throws InterruptedException {
		
		ExecutorService ex=Executors.newCachedThreadPool();
		System.out.println("Album created process started.....");
		ex.submit(() -> {
			try {
				
				Album album=new Album("Rap Songs","Eminem");
				album.addSongToAlbum("Monster", 3.52);
				album.addSongToAlbum("Lose yourself", 5.00);
				album.addSongToAlbum("Venom", 4.00);
				barrier.await();
				albums.add(album);
				System.out.println(album.getArtistName()+" added to the collections");
				albumLatch.countDown();
			} catch (Exception e) {System.out.println(e);}
		},"Eminem Album");
		
		ex.submit(() -> {
			try {
				
				Album album=new Album("Vibe Songs","Weekend");
				album.addSongToAlbum("StarBoy", 3.00);
				album.addSongToAlbum("Often", 3.10);
				barrier.await();
				albums.add(album);
				System.out.println(album.getArtistName()+" added to the collections");
				albumLatch.countDown();
			} catch (Exception e) {System.out.println(e);}
		},"Weekend Album");
		
		ex.submit(() -> {
			try {
				Album album=new Album("Feel Good Songs","Alec Benjamin");
				album.addSongToAlbum("Let me down slowly", 3.52);
				album.addSongToAlbum("If we have each other", 5.00);
				barrier.await();
				albums.add(album);
				System.out.println(album.getArtistName()+" added to the collections");
				albumLatch.countDown();
			} catch (Exception e) {System.out.println(e);}
		},"Alec Benjamin Album");
		albumLatch.await();
		ex.shutdown();
		MyPlayList playList=new MyPlayList();
		Object lock=new Object(); 
		List<Thread>threadList=new ArrayList<>();
		for(int i=0; i<albums.size(); i++) {
			final int index=i;
			Thread thread=new Thread(() -> {
				synchronized(lock) {
					playList.addSongToPlayList(albums.get(index).getSong(0));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(albums.get(index).getSong(0)+" added to my PlayList");
				}
			},"Thread "+i);
			thread.start();
			threadList.add(thread);
			
		}
		
		for(Thread threads:threadList) {
			threads.join();
		}
		Thread.sleep(1000);
		System.out.println("Lets play our favourite song in our playList...");
		Thread.sleep(2000);
		playList.play();
		
		
		
		
	}
}
