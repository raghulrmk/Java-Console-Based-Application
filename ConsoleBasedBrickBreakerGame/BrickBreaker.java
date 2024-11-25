package ConsoleBasedBrickBreakerGame;

import java.util.*;

public class BrickBreaker {
	
	private static String ballBreaker="o";
	private static String bricks="1";
	private static String walls="w";
	private static final int Ballife=10;
	private static String ground="g";
	private static String[][] gameBoard;
	private static HashMap<Integer,HashMap<Integer,Integer>>lifeTracker=new HashMap<>();
	private static int n; // dimension of the game board like n*n;
	private static int hitPoints=0;
	private static int[] ballPosition;
 
	public BrickBreaker(int n) throws InterruptedException {
		this.n=n;
		this.gameBoard=new String[n][n];
		ballPosition=new int[] {n-1,n/2};
		prepareGameBoard(n);
		
	}
	private void prepareGameBoard(int n) throws InterruptedException  {
		
		System.out.println("Game Board Generating....");
		Thread.sleep(1000);
		
		for(int i=0; i<this.n; i++) {
			for(int j=0; j<this.n; j++) {
				if(i==0 || j==0 || j==n-1) {
					this.gameBoard[i][j]=this.walls;
				} else if(i==n-1) {
					if(j==n/2) {
						this.gameBoard[i][j]=this.ballBreaker;
					} else {
						this.gameBoard[i][j]=this.ground;
					}
				} else if((i>=2 && i<=n-3) && (j>=2 && j<=n-3)) {
					this.gameBoard[i][j]=this.bricks;
				} else {
					this.gameBoard[i][j]=" ";
				}
			}
		}
		System.out.println("Game Board set up has finished\n");
		Thread.sleep(1000);
		printGameBoard();
		allocatingLifeToTheBall();
		System.out.println("\nGame Intructions Loading....");
		Thread.sleep(1000);
		System.out.println("\n----------------\n");
		String rules="Instructions To Know\n\n1. BallBreaker has only 10 life\n2. Every Wall has two life\n3. BallBreaker"
				+ "always move diagonally\n4. Scores will be generated based on hit points";
		System.out.println(rules);
		System.out.println("\n----------------\n");
		
		System.out.println("Game has been started....");
		System.out.println("Game has been started\n");
		PlayTheGame();
		
	}
	
	private void allocatingLifeToTheBall() {
		int lastBrickRow=n-3;
		
		for(int i=2; i<=lastBrickRow; i++) {
			HashMap<Integer,Integer>map=new HashMap<>();
			for(int j=2; j<=lastBrickRow; j++) {
				map.put(j,1);
			}
			lifeTracker.put(i, map);
		}
	}
	
	private void PlayTheGame() throws InterruptedException {
		int currentBallLife=this.Ballife;
		while(true) {
			printOptions();
			Scanner in=new Scanner(System.in);
			System.out.println("Enter the move code...");
			int move=in.nextInt();
			
			switch(move) {
				case 0:
					leftMove(ballPosition[0],ballPosition[1]);
					break;
				case 1:
					straightMove(ballPosition[0],ballPosition[1],0);
					break;
				case 2:
					rightMove(ballPosition[0],ballPosition[1]);
					break;
					
			}
			
			currentBallLife--;
			
			if(!isThereRemainingBricks()) {
				System.out.println("Wow!! You managed to destroy the entire bricks with in the given ball limit");
				break;
			} else if(!isThereRemainingBallLife(currentBallLife)) {
				System.out.println("You have crossed the given ball limit....");
				break;
			}
			
			
		}
		System.out.println(ballPosition[0]+" "+ballPosition[1]);
		
	}
	private void leftMove(int row,int column) throws InterruptedException {
		this.gameBoard[row][column]=this.ground;
		
		while(true) {
			
			row--;
			column--;
			if(this.gameBoard[row][column].equals(this.bricks)) {
				if(this.lifeTracker.get(row).get(column)==0) {
					gameBoard[row][column]=" ";
				} else {
					this.lifeTracker.get(row).put(column,0);
				}
				
				row++;
				this.gameBoard[row][column]=this.ballBreaker;
				printGameBoard();
				Thread.sleep(1000);
				straightMove(row,column,1);
				return;
			} else if(this.gameBoard[row][column].equals(this.walls)) {
				gameBoard[row][column]=this.ballBreaker;
			    printGameBoard();
			    Thread.sleep(1000);
			    gameBoard[row][column]=this.walls;
			    column++;
			    gameBoard[row][column]=this.ballBreaker;
			    printGameBoard();
			    Thread.sleep(1000);
			    horizontalMove(row,column);
			    return;
			}
			
			this.gameBoard[row][column]=this.ballBreaker;
			printGameBoard();
		
			this.gameBoard[row][column]=" ";
			Thread.sleep(1000);
			
			
		}
	}
	private void horizontalMove(int row,int column) throws InterruptedException {
		this.gameBoard[row][column]=" ";
		//int loop=0;
		while(true) {
			column++;
			int loop=0;
			while(this.gameBoard[row][column].equals(this.bricks)) {
				if(this.lifeTracker.get(row).get(column)==0) {
					this.gameBoard[row][column]=" ";
					
				} else {
					this.lifeTracker.get(row).put(column, 0);
				}
				loop++;
				row++;
			}
			if(loop!=0) {
				this.gameBoard[row][column]=this.ballBreaker;
				
				printGameBoard();
				Thread.sleep(1000);
				straightMove(row,column,1);
				return;
			}
			if(this.gameBoard[row][column].equals(this.walls)) {
				this.gameBoard[row][column]=this.ballBreaker;
				printGameBoard();
				Thread.sleep(1000);
				this.gameBoard[row][column]=this.walls;
				row++;
				column--;
				this.gameBoard[row][column]=this.ballBreaker;
				printGameBoard();
				Thread.sleep(1000);
				reverseDiagonal(row,column);
				return;
			}
			this.gameBoard[row][column]=this.ballBreaker;
			printGameBoard();
			this.gameBoard[row][column]=" ";
			Thread.sleep(1000);
		}
	}
	private void reverseDiagonal(int row, int column) throws InterruptedException {
		this.gameBoard[row][column]=" ";
		while(true) {
			row++;
			column--;
			int loop=0;
			while(this.gameBoard[row][column].equals(this.bricks)) {
				if(this.lifeTracker.get(row).get(column)==0) {
					this.gameBoard[row][column]=" ";
					
				} else {
					this.lifeTracker.get(row).put(column, 0);
				}
				loop++;
				row++;
			}
			if(loop!=0) {
				this.gameBoard[row][column]=this.ballBreaker;
				printGameBoard();
				Thread.sleep(1000);
				straightMove(row,column,1);
				return;
			}
			if(this.gameBoard[row][column].equals(this.ground)) {
				this.gameBoard[row][column]=this.ballBreaker;
				printGameBoard();
				Thread.sleep(1000);
				return;
			}
			this.gameBoard[row][column]=this.ballBreaker;
			printGameBoard();
			this.gameBoard[row][column]=" ";
			Thread.sleep(1000);
		}
	}
	private void rightMove(int row, int column) throws InterruptedException {
		this.gameBoard[row][column]=this.ground;
		
		while(true) {
			row--;
			column++;
			if(this.gameBoard[row][column].equals(this.bricks)) {
				if(this.lifeTracker.get(row).get(column)==0) {
					gameBoard[row][column]=" ";
				} else {
					this.lifeTracker.get(row).put(column,0);
				}
				
				row++;
				this.gameBoard[row][column]=this.ballBreaker;
				printGameBoard();
				Thread.sleep(1000);
				straightMove(row,column,1);
				return;
			} else if(this.gameBoard[row][column].equals(this.walls)) {
				gameBoard[row][column]=this.ballBreaker;
			    printGameBoard();
			    Thread.sleep(1000);
			    gameBoard[row][column]=this.walls;
			    row++;
			    column--;
			    this.gameBoard[row][column]=this.ballBreaker;
				printGameBoard();
				Thread.sleep(1000);
				reverseDiagonal(row,column);
				break;
			}
		}
	}
	private void straightMove(int row, int column,int check) throws InterruptedException {
		
		if(check==1) {
			this.gameBoard[row][column]=" ";
			while(true) {
				row++;
			    if(this.gameBoard[row][column].equals(this.ground)) {
			    	this.gameBoard[row][column]=this.ballBreaker;
			    	printGameBoard();
			    	Thread.sleep(1000);
			    	ballPosition=new int[] {row,column};
			    	return;
			    }
			    
			   this.gameBoard[row][column]=this.ballBreaker;
			   printGameBoard();
			   this.gameBoard[row][column]=" ";
			   Thread.sleep(1000);
			    
			}
			
		} else if(check==0) {
			this.gameBoard[row][column]=this.ground;
			
			while(true) {
				row--;
				if(this.gameBoard[row][column].equals(this.bricks)) {
					if(this.lifeTracker.get(row).get(column)==0) {
						this.gameBoard[row][column]=" ";
						
					} else {
						this.lifeTracker.get(row).put(column, 0);
					}
					row++;
					this.gameBoard[row][column]=this.ballBreaker;
					printGameBoard();
					Thread.sleep(1000);
					straightMove(row,column,1);
					return;
				}
				
				this.gameBoard[row][column]=this.ballBreaker;
				printGameBoard();
				this.gameBoard[row][column]=" ";
				Thread.sleep(1000);
		  }

		}
	}
	private boolean isThereRemainingBricks() {
		int loop=0;
		for(int i=0; i<n;i++) {
			for(int j=0; j<n; j++) {
				if(this.gameBoard[i][j].equals(this.bricks)) {
					loop++;
				}
			}
		}
		if(loop==0) return false;
		return true;
	}
	private boolean isThereRemainingBallLife(int life) {
		if(life==0) return false;
		return true;
	}
	private void printOptions() {
		System.out.println("--------------\n");
		System.out.println("Ball moves\n");
		String options="0.Move the Ball to left"
				+ "\n1.Move the Ball to straight"
				+ "\n2.Move the ball to right";
		System.out.println(options+"\n");
		System.out.println("--------------\n");
	}
	private void printGameBoard() {
	    System.out.println("\n----------------\n");
		for(String[] st:this.gameBoard) {
			for(String str:st) {
				System.out.print(str+" ");
			}
			System.out.print("\n");
		}
		
		System.out.println("------------------\n");
	}
	
}
