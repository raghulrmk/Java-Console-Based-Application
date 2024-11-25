package TwoPlayerSwordGame;

import java.util.*;

public abstract class SwordGame {
	
	protected String PlayerName;
	protected int health;
	protected boolean armor;
	protected boolean headlingPortionStatus;
	
	protected  abstract void strikeTheOpponent();
	protected  abstract void getTheHit();
	
	protected abstract void getHealed();
	
}
