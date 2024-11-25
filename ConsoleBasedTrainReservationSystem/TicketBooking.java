package ConsoleBasedTrainReservationSystem;

import java.util.*;

public class TicketBooking {
	
	private String PassengerName;
	private int ticketId;
	private int[] seatId;
	private int age;
	private int totalTickets;
	private int UpperBerth=0;
	private int LowerBerth=0;
	private int MiddleBerth=0;
	private int preferenceType;
	private int TravellingCost;
	private static Set<Integer>ticketIdTracker=new HashSet<>();
	private static Set<Integer>seatIdTracker=new HashSet<>();
	private static Map<Integer,Passenger>ConfirmedTicket=new HashMap<>();
	private static Map<Integer,Passenger>RACList=new HashMap<>();
	private static Map<Integer,Passenger>WaitingList=new HashMap<>();
	private static int availableLowBerth=5;
	private static int availableMiddleBerth=5;
	private static int availableUpperBerth=5;
	public TicketBooking() {}
	public TicketBooking(Passenger traveller) throws InterruptedException {
		this.ticketId=traveller.getTicketId();
		this.PassengerName=traveller.getPassengerName();
		this.totalTickets=traveller.getTotalTickets();
		this.preferenceType=traveller.getPreferenceType();
		this.age=traveller.getAge();
		this.seatId=new int[this.totalTickets];
		for(int i=0; i<this.totalTickets; i++) {seatId[i]=seatIdGenerator();}
		if(this.preferenceType==0) bookLowerBerth();
		if(this.preferenceType==1) bookMiddleBerth();
		if(this.preferenceType==2) bookUpperBerth();
	}
	public TicketBooking(String name, int age, int totalTickets, int preferenceType) throws InterruptedException {
		this.ticketId=ticketIdGenerator();
		this.PassengerName=name;
		this.age=age;
		this.totalTickets=totalTickets;
		this.preferenceType=preferenceType;
		this.seatId=new int[this.totalTickets];
		
		
		if(this.totalTickets>(this.availableLowBerth+this.availableMiddleBerth+this.availableUpperBerth)) {
			this.seatId=new int[this.totalTickets];
			for(int i=0; i<this.totalTickets; i++) seatId[i]=0;
			if(RACList.size()==1) {
				waitingList();
			} else {
			   reservationList();
			}
		} else {
			this.seatId=new int[this.totalTickets];
			for(int i=0; i<this.totalTickets; i++) {seatId[i]=seatIdGenerator();}
			if(this.preferenceType==0) bookLowerBerth();
			if(this.preferenceType==1) bookMiddleBerth();
			if(this.preferenceType==2) bookUpperBerth();
		}
		
		
		
	}
	
	private void bookUpperBerth() throws InterruptedException {
		System.out.println("Ticket Availabilty Checking for Upper Breath......");
		Thread.sleep(1000);
		if(this.totalTickets<=this.availableUpperBerth) {
			this.availableUpperBerth-=this.totalTickets;
			this.UpperBerth=this.totalTickets;
			System.out.println("Your Train ticket with Upper Berth has booked successfully");
			
			System.out.println("Bill generating....");
			Thread.sleep(2000);
			generateTheBill();
			return;
		} else {
				System.out.println("There are no enough seats for you in Upper Berth,so let us check for availability in Middle and Lower Berth");
				Thread.sleep(2000);
				this.UpperBerth=this.availableUpperBerth;
				this.totalTickets=this.totalTickets-this.availableUpperBerth;
				this.availableUpperBerth=0;
				
					if(this.totalTickets<=this.availableMiddleBerth) {
						this.availableMiddleBerth-=this.totalTickets;
						this.MiddleBerth=this.totalTickets;
						
					} else {
						this.MiddleBerth=this.availableMiddleBerth;
						this.totalTickets-=this.availableMiddleBerth;
						this.availableMiddleBerth=0;
						if(this.totalTickets<=this.availableLowBerth) {
							this.availableLowBerth-=this.totalTickets;
							this.LowerBerth=this.totalTickets;
						
						} else {
							this.LowerBerth=this.availableLowBerth;
							this.totalTickets-=this.availableLowBerth;
							this.availableLowBerth=0;
						}
					}
				
				
				System.out.println("It seems there are some seats available for you in Middle and Lower berth");
				System.out.println("Let us generate the bills for you sir/madam");
				System.out.println("Bill generating........");
				Thread.sleep(2000);
				generateTheBill();
				return;
			
			
		} 
	}
	private void  bookMiddleBerth() throws InterruptedException {
		System.out.println("Ticket Availabilty Checking for Middle Berth......");
		Thread.sleep(1000);
		if(this.totalTickets<=this.availableMiddleBerth) {
			this.availableMiddleBerth-=this.totalTickets;
			this.MiddleBerth=this.totalTickets;
			System.out.println("Your Train ticket with Middle Berth has booked successfully");
			
			System.out.println("Bill generating....");
			Thread.sleep(2000);
			generateTheBill();
			return;
		} else {
				System.out.println("There are no enough seats for you in Middle Berth,so let us check for availability in Upper and Lower Berth");
				Thread.sleep(2000);
				this.MiddleBerth=this.availableMiddleBerth;
				this.totalTickets=this.totalTickets-this.availableMiddleBerth;
				this.availableMiddleBerth=0;
				
					if(this.totalTickets<=this.availableUpperBerth) {
						this.availableUpperBerth-=this.totalTickets;
						this.UpperBerth=this.totalTickets;
						
					} else {
						this.UpperBerth=this.availableUpperBerth;
						this.totalTickets-=this.availableUpperBerth;
						this.availableUpperBerth=0;
						if(this.totalTickets<=this.availableLowBerth) {
							this.availableLowBerth-=this.totalTickets;
							this.LowerBerth=this.totalTickets;
						
						} else {
							this.LowerBerth=this.availableLowBerth;
							this.totalTickets-=this.availableLowBerth;
							this.availableLowBerth=0;
						}
					}
				
				
				System.out.println("It seems there are some seats available for you in Upper and Lower berth");
				System.out.println("Let us generate the bills for you sir/madam");
				System.out.println("Bill generating........");
				Thread.sleep(2000);
				generateTheBill();
				return;
			
			
		} 
	}
	private void bookLowerBerth() throws InterruptedException {
		System.out.println("Ticket Availabilty Checking for Lower Berth......");
		Thread.sleep(2000);
		if(this.totalTickets<=this.availableLowBerth) {
			this.availableLowBerth-=this.totalTickets;
			this.LowerBerth=this.totalTickets;
			System.out.println("Your Train ticket with Low Berth has booked successfully");
			
			System.out.println("Bill generating....");
			Thread.sleep(2000);
			generateTheBill();
			return;
		} else {
			System.out.println("There is no enough seats available seats in Low Berth so we are searching for available seats in Upper and Middle berth...");
			Thread.sleep(2000);
			
				this.LowerBerth=this.availableLowBerth;
				this.totalTickets=this.totalTickets-this.availableLowBerth;
				this.availableLowBerth=0;
				
					if(this.totalTickets<=this.availableMiddleBerth) {
						this.availableMiddleBerth-=this.totalTickets;
						this.MiddleBerth=this.totalTickets;
						
					} else {
						this.MiddleBerth=this.availableMiddleBerth;
						this.totalTickets-=this.availableMiddleBerth;
						this.availableMiddleBerth=0;
						if(this.totalTickets<=this.availableUpperBerth) {
							this.availableUpperBerth-=this.totalTickets;
							this.UpperBerth=this.totalTickets;
						
						} else {
							this.UpperBerth=this.availableUpperBerth;
							this.totalTickets-=this.availableUpperBerth;
							this.availableUpperBerth=0;
						}
					}
				
				
				System.out.println("It seems there are some seats available for you in Middle and Upper breath");
				System.out.println("Let us generate the bills for you sir/madam");
				System.out.println("Bill generating........");
				Thread.sleep(1000);
				generateTheBill();
				return;
			}
			
		
	}
	public void cancellingTheTicket() throws InterruptedException {
		Scanner in=new Scanner(System.in);
		System.out.println("Enter your ticket id for further continuing your cancellation request");
		int ticket=in.nextInt();
		System.out.println("Cancelling your Ticket....");
		Thread.sleep(2000);
		Passenger traveller=ConfirmedTicket.get(ticket);
		availableLowBerth+=(traveller.getLowBerth());
		availableUpperBerth+=(traveller.getUpperBerth());
		availableMiddleBerth+=(traveller.getMiddleBerth());
		this.ConfirmedTicket.remove(ticket);
		System.out.println("Your ticket has cancelled successfully....");
		Passenger RACTraveller=null;
		for(Passenger check:this.RACList.values()) {
			RACTraveller=check;break;
		}
		if(RACTraveller!=null && RACTraveller.getTotalTickets()<=(this.availableLowBerth+this.availableMiddleBerth+this.availableUpperBerth)) {
			System.out.println("Now assigning the cancelled ticket to the reserving list......");
			Thread.sleep(3000);
			TicketBooking tb=new TicketBooking(RACTraveller);
			RACList.clear();
			for(Passenger passenger:this.WaitingList.values()) {
				this.RACList.put(passenger.getTicketId(), passenger);
				this.WaitingList.remove(passenger.getTicketId());
				break;
			}
		}
		
	}
	private  void reservationList() throws InterruptedException {
		System.out.println("Since there are no available seats, we are sending your request under reservation list ");
		System.out.println("Reserving your request...");
		Thread.sleep(1000);
	    RACList.put(this.ticketId,new Passenger(this.PassengerName,this.age,this.seatId,this.totalTickets,0,0,0,this.preferenceType,this.ticketId));
	    System.out.println("Added to the reservation list, we will notify you if the seats are available");
	    return;
	}
	private void waitingList() throws InterruptedException {
		System.out.println("Since there are no available seats, we are sending your request under waiting list");
		System.out.println("Keeping under waiting list...");
		Thread.sleep(1000);
		WaitingList.put(this.ticketId, new Passenger(this.PassengerName,this.age,this.seatId,this.totalTickets,0,0,0,this.preferenceType,this.ticketId));
	}
	private void generateTheBill() {
		int tickets=this.LowerBerth+this.UpperBerth+this.MiddleBerth;
		this.ConfirmedTicket.put(this.ticketId,new Passenger(this.PassengerName,this.age,this.seatId,tickets,this.LowerBerth,this.MiddleBerth,this.UpperBerth,this.preferenceType,this.ticketId));
		System.out.println("\n-----------------\n");
		System.out.println(this.ConfirmedTicket.get(this.ticketId));
		System.out.println("\n-----------------\n");
		return;
	}
	private int ticketIdGenerator() {
		Random random=new Random();
		while(true) {
			int ticketIds=random.nextInt(900)+100;
			if(!this.ticketIdTracker.contains(ticketIds)) {
				this.ticketIdTracker.add(ticketIds);
				return ticketIds;
			}
		}
	}
	
	private int seatIdGenerator() {
		Random random=new Random();
		while(true) {
			int seatIds=random.nextInt(9000)+1000;
			if(!this.seatIdTracker.contains(seatIds)) {
				this.seatIdTracker.add(seatIds);
				return seatIds;
			}
		}
	}
	public void getConfirmedList() throws InterruptedException {
		System.out.println("Booking Confirmed List Generating.....");
	    Thread.sleep(1000);
		for(Passenger passenger:this.ConfirmedTicket.values()) {
			System.out.println("\n----------------\n");
			System.out.println(passenger);
			System.out.println("\n----------------\n");
		}
	}
	public void getRACList() throws InterruptedException {
		System.out.println("Reservation List Generating.....");
	    Thread.sleep(1000);
		for(Passenger passenger:this.RACList.values()) {
			System.out.println("\n----------------\n");
			System.out.println(passenger);
			System.out.println("\n----------------\n");
		}
	}
	public void getWaitingList() throws InterruptedException {
	    System.out.println("Waiting List Generating.....");
	    Thread.sleep(1000);
		for(Passenger passenger:this.WaitingList.values()) {
			System.out.println("\n----------------\n");
			System.out.println(passenger);
			System.out.println("\n----------------\n");
		}
	}
	
	public void viewYourTicket(int ticketId) throws InterruptedException {
		Thread.sleep(500);
		System.out.println("Checking your Ticket Id whether is it valid or not?.....");
		Thread.sleep(2000);
		if(this.ConfirmedTicket.containsKey(ticketId)) {
			System.out.println("Your ticket is loading....");
			Thread.sleep(2000);
			System.out.println("\n"+this.ConfirmedTicket.get(ticketId)+"\n");
		} else {
			System.out.println("Sorry, Invalid ticket id");
		}
	}
	
	
}
