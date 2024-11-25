package TwoPlayerSwordGame;


import java.util.*;
import java.util.concurrent.*;


public class main {
	
	
	public static void main(String a[]) throws InterruptedException {
		System.out.println("Battle between the sword gods started....");
		Player1 player1=new Player1("Roronoro Zoro",true);
		Player2 player2=new Player2("Mihwak",true);
		Thread t1=new Thread(() -> {
			
			
			while(true) {
				int loop=0;
				if(Thread.currentThread().isInterrupted()) {
					System.out.println("Refree interupted the "+Thread.currentThread().getName());
					break;
				}
				Random random=new Random();
				int number=random.nextInt(2)+1;
				if(number==1) player1.strikeTheOpponent();
				if(number==2) player1.getTheHit();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					Thread.currentThread().interrupt();
					break;
				}
				//if(loop!=0) break;
				
				player1.getHealed();
				
			}
			
		},"Roronoro Zoro");
		
		Thread t2=new Thread(() -> {
			
		
			while(true) {
				int loop=0;
				if(Thread.currentThread().isInterrupted()) {
					System.out.println("Refree interupted the "+Thread.currentThread().getName());
					break;
				}
				Random random=new Random();
				int number=random.nextInt(2)+1;
				if(number==1) player2.strikeTheOpponent();
				if(number==2) player2.getTheHit();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					Thread.currentThread().interrupt();
					break;
				}
				
				
				player2.getHealed();
				
			}
			
		},"Mihwak");
	    Thread watcher=new Thread(() -> {
	    	while(true) {
	    		try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		
		    	if(player1.health<=0) {
		    		t1.interrupt();
		    		t2.interrupt();
		    		System.out.println(t2.getName()+" won the war");
		    		break;
		    	} else if(player2.health<=0) {
		    		t1.interrupt();
		    		t2.interrupt();
		    		System.out.println(t1.getName()+" won the war");
		    		break;
		    	}
	    	}
	    	
	    },"Refree");
	    
	    t1.start(); t2.start();
	    watcher.start();
	    t1.join(); t2.join(); watcher.join();
	    
	    System.out.println("\n Finally historical war between two rival Zoro and Mihawk came to an end...");
	    Thread.sleep(1000);
	    System.out.println("\nTO BE CONTINUED.....");
	}
}
