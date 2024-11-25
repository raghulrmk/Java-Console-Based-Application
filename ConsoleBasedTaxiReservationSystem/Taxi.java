package ConsoleBasedTaxiReservationSystem;

import java.util.*;



public class Taxi {
	
	private int TaxiId;
	private Integer CustId;
	private String CurrentLocation;
	private boolean TaxiStatus;
	private String PickupLocation;
	private String DropupLocaltion;
	private Integer PickupTime;
	private Integer DropupTime;
	private int totalEarnings;
	private static Set<Integer>taxiIdGenerator=new HashSet<Integer>();
	
	public Taxi() {
		this.CustId=null;
		this.CurrentLocation="A";
		this.DropupLocaltion=null;
		this.PickupLocation=null;
		this.DropupTime=null;
		this.PickupTime=null;
		this.totalEarnings=0;
		this.TaxiId=generateTaxiId();
		this.TaxiStatus=false;
	}
	
	private int generateTaxiId() {
		Random random=new Random();
		while(true) {
			int taxiId=random.nextInt(90)+10;
			if(!taxiIdGenerator.contains(taxiId)) {
				taxiIdGenerator.add(taxiId);
				return taxiId;
			}
		}
		
	}

	public int getTaxiId() {
		return TaxiId;
	}

	public void setTaxiId(int taxiId) {
		TaxiId = taxiId;
	}

	public Integer getCustId() {
		return CustId;
	}

	public void setCustId(Integer custId) {
		CustId = custId;
	}

	public String getCurrentLocation() {
		return CurrentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		CurrentLocation = currentLocation;
	}

	public String getPickupLocation() {
		return PickupLocation;
	}

	public void setPickupLocation(String pickupLocation) {
		PickupLocation = pickupLocation;
	}

	public String getDropupLocaltion() {
		return DropupLocaltion;
	}

	public void setDropupLocaltion(String dropupLocaltion) {
		DropupLocaltion = dropupLocaltion;
	}

	public Integer getPickupTime() {
		return PickupTime;
	}

	public void setPickupTime(Integer pickupTime) {
		PickupTime = pickupTime;
	}

	public Integer getDropupTime() {
		return DropupTime;
	}

	public void setDropupTime(Integer dropupTime) {
		DropupTime = dropupTime;
	}

	public int getTotalEarnings() {
		return totalEarnings;
	}

	public void setTotalEarnings(int totalEarnings) {
		this.totalEarnings = this.totalEarnings+totalEarnings;
	}

	public void setTaxiStatus(boolean status) {
		this.TaxiStatus=status;
	}
	@Override
	public String toString() {
		String status="";
		if(this.TaxiStatus) status="Busy";
		if(!this.TaxiStatus) status="Free";
		String result="Taxi id: "+this.TaxiId+"\nCustomer id: "+this.getCustId()+"\nTaxi status: "+status+"\n"
				+ "Taxi CurrentLocation: "+this.CurrentLocation+"\nTaxi PickUpLocation: "+this.PickupLocation+
				"\nTaxi DropupLocation: "+this.DropupLocaltion+"\nTaxi PickupTime: "+this.PickupTime+"\n"
				+"Taxi DropupTime: "+this.DropupTime+"\nTaxi Earnings: "+this.totalEarnings+"\n";
		return result;
	}
}
