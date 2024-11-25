package PizzaBillGenerator;

import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class main {
	
	private static CyclicBarrier barrier=new CyclicBarrier(4,() -> {
		System.out.println("Bill Loading...\n");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	});
	private static ExecutorService basicEx=Executors.newCachedThreadPool();
	private static ExecutorService DeluxeEx=Executors.newCachedThreadPool(); 
	private static  CountDownLatch latch=new CountDownLatch(4);
	public static void main(String a[]) throws InterruptedException {
		
		System.out.println("Pizza Bill Generated process started....");
		
		basicEx.submit(() -> {
		    try {
				BasicPizza basic=new BasicPizza(true);
				basic.addExtraCheese();
				basic.addExtraToppins();
				basic.takeAway();
				barrier.await();
				Thread.sleep(1000);
				System.out.println(basic.getBill());
				latch.countDown();
		    } catch(Exception e) {System.out.println(e);}
			
		});
		
		basicEx.submit(() -> {
			try {
				BasicPizza basic1=new BasicPizza(false);
				basic1.addExtraCheese();
				basic1.addExtraToppins();
				basic1.takeAway();
				barrier.await();
				Thread.sleep(1000);
				System.out.println(basic1.getBill());
				latch.countDown();
		    } catch(Exception e) {System.out.println(e);}
			
		});
		DeluxeEx.submit(() -> {
			try {
				DeluxePizza deluxe=new DeluxePizza(true);
				deluxe.addExtraCheese();
				deluxe.addExtraToppins();
				deluxe.takeAway();
				barrier.await();
				Thread.sleep(1000);
				System.out.println(deluxe.getBill());
				latch.countDown();
			} catch(Exception e) {System.out.println(e);}
			
		});
		DeluxeEx.submit(() -> {
			try {
				DeluxePizza deluxe1=new DeluxePizza(false);
				deluxe1.addExtraCheese();
				deluxe1.addExtraToppins();
				deluxe1.takeAway();
				barrier.await();
				Thread.sleep(1000);
				System.out.println(deluxe1.getBill());
				latch.countDown();
			} catch(Exception e) {System.out.println(e);}
			
		});
		
		
		latch.await();
		
		Thread.sleep(1000);
		System.out.println("Bill generated process ended...");
		basicEx.shutdown();
		DeluxeEx.shutdown();
		
	}
}
