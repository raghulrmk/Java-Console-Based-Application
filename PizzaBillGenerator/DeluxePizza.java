package PizzaBillGenerator;

import java.util.*;


public class DeluxePizza extends Pizza {
	
	public DeluxePizza(Boolean veg) {
		this.veg=veg;
		
		if(veg) this.price=500;
		if(!veg) this.price=900;
	}
	@Override
	protected void addExtraCheese() {
		System.out.println("No need, in deluxe edition extra cheese availabe by deafult....");
		
	}

	@Override
	protected void addExtraToppins() {
		System.out.println("No need, in deluxe edition extra toppins availabe by deafult....");
		
	}

	@Override
	protected void takeAway() {
		this.price=this.price+100;
		System.out.println("Parsaled your package");
		this.isWrappedUp=true;
	}

	@Override
	protected String getBill() {
		String orderResult="";
		if(veg) orderResult+="Deluxe Veg Pizza: 500\n";
		if(!veg) orderResult+="Deluxe Non-Veg Pizza: 900\n";
		orderResult+="Extra Cheese added at no extra cost\nExtra Toppins added at no extra cost\n";
		if(this.isWrappedUp) orderResult+="Parsal cost: 100\n";
		orderResult+="Total Bill: "+this.price+"\n";
		return orderResult;
	}
	
	
}
