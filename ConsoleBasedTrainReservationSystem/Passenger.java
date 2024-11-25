package ConsoleBasedTrainReservationSystem;

import java.util.*;

public class Passenger {
	
	private String PassengerName;
	private int passengerId;
	private int age;
	private int ticketId;
	private int[] seatId;
	private int totalTickets;
	private int LowBerth;
	private int UpperBerth;
	private int MiddleBerth;
	private int preferenceType;
	private int TravellingCost;
	private static Set<Integer>passengerIdTracker=new HashSet<>();
	
	
	public Passenger(String PassengerName, int age, int[] seatId, int totalTickets, int LowBerth, int MiddleBerth, int UpperBerth, int preferenceType,int ticketId) {
		this.PassengerName=PassengerName;
		this.age=age;
		this.ticketId=ticketId;
		this.seatId=seatId;
		this.totalTickets=totalTickets;
		this.LowBerth=LowBerth;
		this.MiddleBerth=MiddleBerth;
		this.UpperBerth=UpperBerth;
		this.preferenceType=preferenceType;
		this.TravellingCost=TravellingCost;
		this.passengerId=generatePassengerId();
	}
	private int generatePassengerId() {
		Random random=new Random();
		while(true) {
			int passengerIds=random.nextInt(90)+10;
			if(!this.passengerIdTracker.contains(passengerIds)) {
				this.passengerIdTracker.add(passengerIds);
				return passengerIds;
			}
		}
	}
	
	public String getPassengerName() {
		return PassengerName;
	}

	public void setPassengerName(String passengerName) {
		PassengerName = passengerName;
	}

	public int getPassengerId() {
		return passengerId;
	}

	public int getAge() {
		return age;
	}


	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public int[] getSeatId() {
		return seatId;
	}

	public void setSeatId(int[] seatId) {
		this.seatId = seatId;
	}

	public int getTotalTickets() {
		return totalTickets;
	}

	public void setTotalTickets(int totalTickets) {
		this.totalTickets = totalTickets;
	}

	public int getLowBerth() {
		return LowBerth;
	}

	public void setLowBerth(int lowBerth) {
		LowBerth = lowBerth;
	}

	public int getUpperBerth() {
		return UpperBerth;
	}

	public void setUpperBerth(int upperBerth) {
		UpperBerth = upperBerth;
	}

	public int getMiddleBerth() {
		return MiddleBerth;
	}

	public void setMiddleBerth(int middleBerth) {
		MiddleBerth = middleBerth;
	}

	public int getPreferenceType() {
		return preferenceType;
	}

	public void setPreferenceType(int preferenceType) {
		this.preferenceType = preferenceType;
	}
	
	public String toString() {
		String preference="";
		ArrayList<Integer>list=new ArrayList<>();
		for(int i:this.seatId) list.add(i);
		if(this.preferenceType==0) preference="Lower";
		if(this.preferenceType==1) preference="Middle";
		if(this.preferenceType==2) preference="Upper";
		String ticketBill="Passenger Name: "+this.PassengerName+"\nAge: "+this.age+"\nTIcket Id: "+this.ticketId+"\nNo of Upper Berth: "
				+this.UpperBerth+"\nNo of Middle Berth: "+this.MiddleBerth+"\nNo of Lower Berth: "+this.LowBerth
				+"\nTotal no of Tickets: "+this.totalTickets+"\nPreference: "+preference+"\nAllocated seats: "+list.toString()+"\n";
		return ticketBill;
	}
	
}
