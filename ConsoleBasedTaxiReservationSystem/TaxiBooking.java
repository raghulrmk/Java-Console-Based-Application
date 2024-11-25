package ConsoleBasedTaxiReservationSystem;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TaxiBooking {
	
	private Integer DropupTime;
	private Integer PickupTime;
	private final static int taxiLimit=4;
	private static Set<Integer>customerIdGenerator=new HashSet<>();
	private static ArrayList<Taxi>taxiDetails=new ArrayList<>();
	private String PickupLocation;
	private String DropupLocation;
	private int totalEarnings;
	private Integer CustomerId;
	public TaxiBooking() {}
	static {
		generateTaxis();
	}
	
	public TaxiBooking(String PickupLocation, String DropupLocation, Integer PickUpTime) {
		this.PickupLocation=PickupLocation;
		this.DropupLocation=DropupLocation;
		this.PickupTime=PickUpTime;
		this.totalEarnings=generateServiceCharge(PickupLocation, DropupLocation);
		int finalTime=generateTravelTimings(PickupLocation, DropupLocation)+this.PickupTime;
		this.DropupTime=(finalTime<=24)?finalTime:finalTime-12;
		this.CustomerId=generateCustomerId();
	}
	public boolean taxiAvailabilityChecker() {
		Comparator<Taxi>comparator=(Taxi t1, Taxi t2) -> {
			int startPoint=(int) this.PickupLocation.charAt(0);
			int diff1=Math.abs((int)t1.getCurrentLocation().charAt(0)-startPoint);
			int diff2=Math.abs((int)t2.getCurrentLocation().charAt(0)-startPoint);
			return Integer.compare(diff1,diff2);
		};
		taxiDetails=(ArrayList<Taxi>) taxiDetails.stream().sorted(comparator).collect(Collectors.toList());
		Taxi allocatedTaxi=null;
		
		for(Taxi taxi:taxiDetails) {
			if(this.PickupLocation.equals(taxi.getCurrentLocation()) &&((taxi.getDropupTime()==null) || (this.PickupTime>=taxi.getDropupTime()))) {
				allocatedTaxi=taxi;
				break;
			}
		}
		Predicate<Taxi>filtering=(Taxi taxi) -> taxi.getDropupTime()==null || this.PickupTime>=taxi.getDropupTime();
		if(allocatedTaxi==null) {
			Optional<Taxi>selectTaxi=taxiDetails.stream().filter(filtering).findFirst();
			if(selectTaxi.isPresent()) {
				allocatedTaxi=selectTaxi.get();
			} else {
				System.out.println("Currently every Taxi is busy. And sorry for the inconvineance");
				return false;
			}
		}
		allocatedTaxi.setPickupLocation(this.PickupLocation);
		allocatedTaxi.setDropupLocaltion(this.DropupLocation);
		allocatedTaxi.setCurrentLocation(this.DropupLocation);
		allocatedTaxi.setPickupTime(this.PickupTime);
		allocatedTaxi.setDropupTime(this.DropupTime);
		allocatedTaxi.setTotalEarnings(this.totalEarnings);
		allocatedTaxi.setCustId(this.CustomerId);
		allocatedTaxi.setTaxiStatus(true);
		generateBill(allocatedTaxi);
		return true;
		
	}
	private void generateBill(Taxi taxi)    {
		System.out.println("Separate Taxi has allocated for you...");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Bill generating....");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String bills="Taxi Id: "+taxi.getTaxiId()+"\nYour Id: "+taxi.getCustId()+"\nPickupLocation: "+this.PickupLocation+"\nDropupLocation: "+this.DropupLocation+"\nPickupTime: "+this.PickupTime+"\n"
				+"DropupTime: "+this.DropupTime+"\nService charge: "+this.totalEarnings+"\n";
		System.out.println(bills);
		System.out.println("---------------\n");
	}
	private int generateCustomerId() {
		Random random=new Random();
		while(true) {
			int customerId=random.nextInt(9000)+1000;
			if(!customerIdGenerator.contains(customerId)) {
				customerIdGenerator.add(customerId);
				return customerId;
			}
		}
	}
	private int generateTravelTimings(String source, String destination) {
		int start=(int)source.charAt(0);
		int end=(int)destination.charAt(0);
		int distance=Math.abs(end-start);
		return distance;
	
	}
	private int generateServiceCharge(String source, String destination) {
		int start=(int)source.charAt(0);
		int end=(int)destination.charAt(0);
		int distance=Math.abs(end-start);
		return distance*200;
	}
	private static void generateTaxis() {
		for(int i=0; i<taxiLimit; i++) {
			taxiDetails.add(new Taxi());
		}
	}
	
	public void getTaxiDetails() {
		for(Taxi taxi:taxiDetails) {
			System.out.println(taxi);
			System.out.println("-------------------\n");
		}
	}
}
