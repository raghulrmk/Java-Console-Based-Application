package PizzaBillGenerator;

import java.util.*;


public abstract class Pizza {
	
	protected double price;
	protected boolean veg;
	protected boolean isExtraCheeseAdded=false;
	protected boolean isExtraToppinsAdded=false;
	protected boolean isWrappedUp=false;
	
	protected abstract void addExtraCheese();
	protected abstract void  addExtraToppins();
	
	protected abstract void takeAway();
	
	protected abstract String getBill();
}
