package TwoPlayerSwordGame;

import java.util.*;


public class Player1 extends SwordGame{
	
	public Player1(String PlayerName,boolean armor) {
		this.armor=armor;
		this.PlayerName=PlayerName;
		this.health=100;
		this.headlingPortionStatus=false;
	}

	@Override
	protected void strikeTheOpponent() {
		
		if(this.health<=90) this.health=this.health+10;
		
		System.out.println(Thread.currentThread().getName()+" striked his opponent with the blood shot sword..."+" Current health status: "+this.health);
		
	}

	@Override
	protected void getTheHit() {
		if(armor) this.health=this.health-20;
		if(!armor) this.health=this.health-30;
		
		System.out.println(Thread.currentThread().getName()+" got a deadly blow from his opponnet..."+" Current health status: "+this.health);
		
	}

	@Override
	protected void getHealed() {
		if((!this.headlingPortionStatus) && this.health<=20) {
			this.health=100;
			this.headlingPortionStatus=true;
			System.out.println(Thread.currentThread().getName()+" got healed and regained his full strength....");
		}
		
	}
}
