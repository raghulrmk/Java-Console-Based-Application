package ConsoleBasedTaxiReservationSystem;

import java.util.*;

public class ClientSide {
	
	public static void main(String a[]) throws InterruptedException {
		
		Scanner in=new Scanner(System.in);
		
		while(true) {
			printMenu();
			System.out.println("Choose any one options to get our respective services");
			int opt=in.nextInt();
			in.nextLine();
			switch(opt) {
				case 1:
					System.out.println("Enter your Pickup Location");
					String pickLocation=in.nextLine();
					System.out.println("Enter the DropUpLocation/Destination");
					String dropLocation=in.nextLine();
					System.out.println("Enter the Pickup Time");
					Integer pickTime=in.nextInt();
					TaxiBooking taxi=new TaxiBooking(pickLocation, dropLocation, pickTime);
					boolean bookingStatus=taxi.taxiAvailabilityChecker();
					if(bookingStatus) {
						System.out.println("oops! you can book another one too");
					} else {
						System.out.println("Do you want to try again...");
					}
					break;
				case 2:
					System.out.println("Taxi Details Generating......");
				    Thread.sleep(1000);
					TaxiBooking tb=new TaxiBooking();
					tb.getTaxiDetails();
					break;
				case 3:
					System.out.println("Shutting Down");
					Thread.sleep(1000);
					System.out.println("Application Turned Off");
					break;
			}
			if(opt==3) {
				break;
			}
		}
		
		
	}
	private static void printMenu() {
		String options="1.Book the car\n2.See the Taxi Details\n3.Exit";
		System.out.println(options);
		System.out.println("------------\n");
		
	}
}
