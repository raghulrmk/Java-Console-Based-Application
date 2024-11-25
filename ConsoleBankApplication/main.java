package ConsoleBankApplication;

import java.util.*;
import java.util.concurrent.*;


public class main {
	
	private static CyclicBarrier barrier=new CyclicBarrier(3, () -> {
		System.out.println("Result Loading.....");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	});
	private static CountDownLatch latch=new CountDownLatch(3);
	
	public static void main(String a[]) throws InterruptedException {
		
		IndianBank acc1=new IndianBank("Raghul",100,"password_19","Ponneri");
		IndianBank acc2=new IndianBank("Balaji",20000,"balaji@7584","Ponneri");
		
		Thread t1=new Thread(() -> {
			
			System.out.println(Thread.currentThread().getName()+" performs operations on "+acc1.getAccountHolderName()+"'s account");
			latch.countDown();
			System.out.println(Thread.currentThread().getName()+" performing transaction");
			acc1.toDoTransaction(acc2,2000);
			try {
				barrier.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(acc1);
			
		},"Thread 1");
		Thread t2=new Thread(() -> {
			
			System.out.println(Thread.currentThread().getName()+" performs operations on "+acc1.getAccountHolderName()+"'s account");
			latch.countDown();
			System.out.println(Thread.currentThread().getName()+" performing deposition");
			acc1.deposit(1000, false);
			try {
				barrier.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(acc1);
			
		},"Thread 2");
		Thread t3=new Thread(() -> {
			
			System.out.println(Thread.currentThread().getName()+" performs operations on "+acc2.getAccountHolderName()+"'s account");
			latch.countDown();
			System.out.println(Thread.currentThread().getName()+" performing transaction");
			acc2.toDoTransaction(acc1, 15000);
			try {
				barrier.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(acc2);
			
		},"Thread 3");
		
		
		
		t1.start(); t2.start(); t3.start();
		t1.join(); t2.join(); t3.join();
		
		
		Thread.sleep(500);
		System.out.println("Tranaction summary loading....");
		Thread.sleep(2000);
		acc1.getTransactionSummary();
	}
}
