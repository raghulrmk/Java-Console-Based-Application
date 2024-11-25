package PizzaBillGenerator;

import java.util.*;
import java.util.concurrent.locks.*;


public class BasicPizza  extends Pizza{
	
	
	public BasicPizza(boolean veg) {
		this.veg=veg;
		
		if(veg) this.price=300;
		if(!veg) this.price=400;
	}

	@Override
	protected void addExtraCheese() {
		
		this.price=this.price+100;
		System.out.println("Extra cheese has been added at the cost of 100");
		this.isExtraCheeseAdded=true;
	}

	@Override
	protected void addExtraToppins() {
		this.price=this.price+100;
		System.out.println("Extra Toppins has been added at the cost of 100");
		this.isExtraToppinsAdded=true;
		
	}

	@Override
	protected void takeAway() {
		this.price=this.price+30;
		System.out.println("Your snack has been packed up for extra cost of 30");
		this.isWrappedUp=true;
	}

	@Override
	protected String getBill() {
		String orderResult="";
		if(this.veg) orderResult+="Basic Veg Pizza: 300\n";
		if(!this.veg) orderResult+="Basic Non-veg Pizza: 400\n";
		if(this.isExtraCheeseAdded) orderResult+="Extra Cheese added: 100\n";
		if(this.isExtraToppinsAdded) orderResult+="Extra Toppins added: 100\n";
		if(this.isWrappedUp) orderResult+="Parsal cost: 30\n";
		String finalBill="Total Bill: "+this.price+"\n";
		orderResult+=finalBill;
		return orderResult;
	}
	

	
}
