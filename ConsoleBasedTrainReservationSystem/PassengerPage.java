package ConsoleBasedTrainReservationSystem;

import java.util.*;

public class PassengerPage {
	
	public static void main(String a[]) throws InterruptedException {
//		TicketBooking bt=new TicketBooking("Raghul",20,3,0);
		
		Scanner in=new Scanner(System.in);
		
		System.out.println("TIcket Booking is going to open within  2 seconds\n");
		Thread.sleep(2000);
		System.out.println("Ticket Booking opened\n");
		
		while(true) {
			int loop=0;
			getBookingOptions();
			System.out.println("Choose any one otions");
			int n=in.nextInt();
			in.nextLine();
			switch(n) {
				case 1:
					System.out.println("Enter your name");
					String name=in.nextLine();
					System.out.println("Enter your age");
					int age=in.nextInt();
					System.out.println("Enter how many tickets you need");
					int totalTickets=in.nextInt();
					System.out.println("\nBooking options\n");
					System.out.println("0.Lower Berth\n1.Middle Berth\n2.Upper Berth\n");
					System.out.println("Choose your preference...");
					int preference=in.nextInt();
					TicketBooking tb=new TicketBooking(name,age,totalTickets,preference);
					break;
				case 2:
					TicketBooking tb1=new TicketBooking();
					tb1.cancellingTheTicket();
					break;
				case 3:
					System.out.println("Can you enter your Ticket id please...");
					int id=in.nextInt();
					
					TicketBooking tb2=new TicketBooking();
					tb2.viewYourTicket(id);
					break;
				case 4:
					TicketBooking tb3=new TicketBooking();
					tb3.getConfirmedList();
					break;
				case 5:
					TicketBooking tb4=new TicketBooking();
					tb4.getRACList();
					break;
				case 6:
					TicketBooking tb5=new TicketBooking();
					tb5.getWaitingList();
					break;
				case 7:
					System.out.println("Shutting Down the process");
					System.out.println("Shutting Down....");
					Thread.sleep(2000);
					System.out.println("Application closed");
					loop++;
			    	break;
			   
			}
			if(loop!=0) break;
			
			
		}
	}
	
	public static void getBookingOptions() {
		String options="1.Book The Ticket\n2.Cancel The Ticket\n3.View Your Ticket\n4.See The Ticket Booked List"
				+ "\n5.See the Reservation list\n6.See the Waiting list\n7.Exit\n";
		
		System.out.println("\n-------------------\n");
		System.out.println(options);
		System.out.println("\n-------------------\n");
	}
}
