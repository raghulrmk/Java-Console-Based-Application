package ConsoleBasedSnakeGame;

import java.util.*;

public class SnakeBoard {
	
	private String snake=".";
	private String fruit="X";
	private String wall="w";
	private String ground="o";
	private String[][] gameBoard;
	private Queue<Node>trackPosition=new LinkedList<>();
	private int boardLen;
	private static int snakeLen=1;
	private int row;
	private int column=0;
	public SnakeBoard(int boardLen) throws InterruptedException {
		this.boardLen=boardLen;
		this.gameBoard=new String[this.boardLen][this.boardLen];
		prepareGameBoard();
	}
	private void prepareGameBoard() throws InterruptedException {
		System.out.println("Preparing GameBoard....");
		Thread.sleep(2000);
		for(int i=0; i<this.boardLen; i++) {
			for(int j=0; j<boardLen; j++) {
				if(i==0 || i==this.boardLen-1 || j==0 || j==this.boardLen-1) {
					this.gameBoard[i][j]=this.wall;
				} else if(i==j && i!=1 && i!=0) {
					this.gameBoard[i][j]=this.fruit;
				} else {
					this.gameBoard[i][j]=this.ground;
				}
			}
		}
		this.gameBoard[1][1]=this.snake;
		this.row=1;
		this.column=1;
		this.trackPosition.add(new Node(this.row,this.column));
		System.out.println("GameSetUp has been finished...");
		Thread.sleep(1000);
		System.out.println("GameBoard Loading....");
		Thread.sleep(1000);
		printGameBoard();
		System.out.println("Rules Loading....");
		System.out.print("\n--------------\n");
		System.out.println("Game Over Rules to Know...");
		
		System.out.println("\n1.Player looses when snake either hits wall or bites it own tail\n2.Player win when snake eats all the fruits");
		System.out.print("\n--------------\n");
		System.out.println("\n Game starts now\n");
		playTheGame();
		
	}
	private void playTheGame() throws InterruptedException {
		while(true) {
			printGameControls();
			Scanner in=new Scanner(System.in);
			System.out.println("Guide your snake");
			int dir=in.nextInt();
			boolean fruit,snakeBite = false,wallHit=false;
			switch(dir) {
				case 1: 
					fruit=checkNextFruit(this.row-1, this.column);
					snakeBite=checkTailBite(this.row-1, this.column);
					wallHit=checkTheWallHit(this.row-1,this.column);
					this.gameBoard[--this.row][this.column]=this.snake;
					trackTheBoard(fruit);
					break;
				case 2: 
					fruit=checkNextFruit(this.row+1, this.column);
					snakeBite=checkTailBite(this.row+1, this.column);
					wallHit=checkTheWallHit(this.row+1,this.column);
					this.gameBoard[++this.row][this.column]=this.snake;
					trackTheBoard(fruit);
					break;
				case 3: 
					fruit=checkNextFruit(this.row, this.column-1);
					snakeBite=checkTailBite(this.row, this.column-1);
					wallHit=checkTheWallHit(this.row,this.column-1);
					this.gameBoard[this.row][--this.column]=this.snake;
					trackTheBoard(fruit);
					break;
				case 4: 
					fruit=checkNextFruit(this.row, this.column+1);
					snakeBite=checkTailBite(this.row, this.column+1);
					wallHit=checkTheWallHit(this.row,this.column+1);
					this.gameBoard[this.row][++this.column]=this.snake;
					trackTheBoard(fruit);
					break;
			}
			if(snakeBite || wallHit) {
				if(snakeBite) System.out.println("Snake has bitten its own tail");
				if(wallHit)   System.out.println("Snake has hit the wall");
				System.out.println("Result loading....");
				Thread.sleep(1000);
				System.out.println("You loose...");
				break;
			} else if(!checkFruitAvailability()) {
				System.out.println("No fruits left to eat....");
				System.out.println("Result Loading....");
				Thread.sleep(1000);
				System.out.println("You win....");
				break;
			}
		}
	}
	private boolean checkFruitAvailability() {
		for(int i=0; i<this.boardLen; i++) for(int j=0; j<this.boardLen; j++) if(this.gameBoard[i][j].equals(this.fruit)) return true;
		return false;
	}
	private boolean checkTheWallHit(int row,int column) {
		if(this.gameBoard[row][column].equals(this.wall)) return true;
		return false;
	}
	private boolean checkTailBite(int row, int column) {
		if(this.gameBoard[row][column].equals(this.snake)) return true;
		return false;
	}
	
	private void trackTheBoard(boolean fruit) {
		this.trackPosition.add(new Node(this.row,this.column));
		if(fruit) {
			this.snakeLen++;
		} else {
			Node temp=this.trackPosition.remove();
			this.gameBoard[temp.row][temp.column]=this.ground;
		}
		printGameBoard();
		
	}
	private boolean checkNextFruit(int pos1, int pos2) {
		if(this.gameBoard[pos1][pos2].equals(fruit)) return true;
		return false;
	}
	private void printGameBoard() {
		System.out.print("\n--------------\n");
		for(int i=0; i<this.boardLen; i++) {
			for(int j=0; j<this.boardLen; j++) {
				System.out.print(this.gameBoard[i][j]+" ");
			} 
			System.out.print("\n");
		}
		System.out.print("--------------\n");
	}
	private void printGameControls() {
		System.out.println("Game Controls");
		System.out.print("\n--------------\n");
		String options="1.Up\n2.Down\n3.Left\n4.Right";
		System.out.println(options);
		System.out.print("\n--------------\n");
	}
	
}
