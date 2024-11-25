package ConsoleBankApplication;

import java.util.concurrent.locks.*;
import java.util.*;
import java.io.*;

class IncorrectUPIKey extends Exception {
	public IncorrectUPIKey(String message) {
		super(message);
	}
}

class EmptyBankVallet extends Exception {
	public EmptyBankVallet(String message) {
		super(message);
	}
}
class TransActionFailed extends Exception {
	public TransActionFailed(String message) {super(message);}
}

public class IndianBank {
	
	private String DepositorName;
	private int accountNumber;
	private double availableBalance;
	private String password;
	private String BranchName;
	private Map<Integer,Double>TransactionSummary;
	private static Set<Integer>DuplicateAccChecker=new HashSet<>();
	private Set<Integer>DuplicateTransChecker;
	private ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
	private ReentrantReadWriteLock.ReadLock readLock=lock.readLock();
	private ReentrantReadWriteLock.WriteLock writeLock=lock.writeLock();
	private Object TransactionLock=new Object();
	private static Object InputLock=new Object();
	
	
	public IndianBank(String name,double initialAmount, String password, String BranchName) {
		this.DepositorName=name;
		this.accountNumber=AccNumberGenerator();
		this.availableBalance=initialAmount;
		this.password=password;
		this.BranchName=BranchName;
		this.TransactionSummary=new HashMap<>();
		this.DuplicateTransChecker=new HashSet<>();
		
	}
	
    private static int AccNumberGenerator() {
    	Random random=new Random();
    	int randomAccNumber=0;
    	while(true) {
    		randomAccNumber=random.nextInt(9000000)+1000000;
    		
    		if(!DuplicateAccChecker.contains(randomAccNumber)) {
    			DuplicateAccChecker.add(randomAccNumber);
    			break;
    		}
    	}
    	return randomAccNumber;
    }
    
    private  int TransNumberGenerator() {
    	Random random=new Random();
    	int randomTransNumber=0;
    	while(true) {
    		randomTransNumber=random.nextInt(9000)+1000;
    		
    		if(!this.DuplicateTransChecker.contains(randomTransNumber)) {
    			this.DuplicateTransChecker.add(randomTransNumber);
    			break;
    		}
    	}
    	return randomTransNumber;
    }
    
    public boolean withDraw(double amount,boolean transaction,IndianBank receiever)  {
    	writeLock.lock();
	    	try {
	    		String str="";
	    		synchronized(InputLock) {
		    		System.out.println(Thread.currentThread().getName()+" needs password to access the "+this.DepositorName+"'s account");
			    	Scanner in=new Scanner(System.in);
			    	str=in.nextLine();
	    		}
		    	try {
		    		if(!str.equals(this.password)) throw new IncorrectUPIKey("Enter the valid UPIKey...");
		    		if(this.availableBalance<amount) throw new EmptyBankVallet("Not enough money to withdraw or to tranfer..");
		    		if(transaction) {
		    			int transId=TransNumberGenerator();
		    			this.TransactionSummary.put(transId, -(amount));
		    			this.availableBalance=this.availableBalance-amount;
		    			System.out.println("Amount: "+amount+" has been transferring to "+receiever.getAccountHolderName()+" from the "+this.DepositorName+"'s account");
		    			
		    			return true;
		    		}
		    		System.out.println(amount+" has been withdrwing from "+this.DepositorName+"'s account....");
		    		try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		this.availableBalance=this.availableBalance-amount;
		    		System.out.println("Amount "+amount+" withdrawn from the"+this.DepositorName+"'s account");
		    		
		    		
		    		
		    	} catch(IncorrectUPIKey e) {
		    		System.out.println(e);
		    		return false;
		    	} catch(EmptyBankVallet e) {
		    		System.out.println(e);
		    		return false;
		    	}
    	} finally {
    		writeLock.unlock();
    	}
	    return true;
	    
    	
    }

    
    public boolean deposit(double amount,boolean transaction)  {
    	writeLock.lock();
    	
    	try {
    		
    		try {
    			if(transaction) {
    				int transId=this.TransNumberGenerator();
    				this.TransactionSummary.put(transId, amount);
    				this.availableBalance=this.availableBalance+amount;
    				System.out.println("Amount "+amount+" has been deposited to the "+this.DepositorName+" from the transaction ");
    				return true;
    			}
    			String str="";
    			synchronized(InputLock) {
	    			System.out.println(Thread.currentThread().getName()+" needs password to access the "+this.DepositorName+"'s account");
	    			Scanner in=new Scanner(System.in);
	    	    	str=in.nextLine();
    			}
	    		if(!str.equals(this.password)) throw new IncorrectUPIKey("Enter the valid UPIKey...");
	    		System.out.println(amount+" has been depositing  to "+this.DepositorName+"'s account....");
	    		try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		this.availableBalance=this.availableBalance+amount;
	    		System.out.println("Amount "+amount+" has deposited to  the "+this.DepositorName+"'s account");
	    		
	    		
	    		
	    	} catch(IncorrectUPIKey e) {
	    		System.out.println(e);
	    		return false;
	    	}
    		
    	} finally {
    		writeLock.unlock();
    	}
    	return true;
    }
    
    public void toDoTransaction(IndianBank obj,double amount) {
    	
    	synchronized(TransactionLock) {
	    	System.out.println("Transfering money between "+this.DepositorName+" and "+obj.getAccountHolderName()+" in on progress....");
	    	try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	try {
	    		if(this.availableBalance<amount) throw new TransActionFailed("oops,sory! not enough amount to start the transaction...");
	    		boolean result=this.withDraw(amount, true,obj);
	    		if(result) obj.deposit(amount, result);
	    		if(result) System.out.println("Transaction between "+this.DepositorName+" and "+obj.getAccountHolderName()+" is completed");
	    		if(!result) System.out.println("Transaction between "+this.DepositorName+" and "+obj.getAccountHolderName()+" is failed! Try again...");
	    		
	    	} catch(TransActionFailed e) {System.out.println(e);}
    	}
    	
    	
    	
    }
    
    public void getTransactionSummary() {
    	readLock.lock();
    	try {
    		double sum=0;
    		for(int i:this.TransactionSummary.keySet()) {
    			System.out.print("Transaction id: "+i+" Amount: "+this.TransactionSummary.get(i)+"\n");
    			sum+=this.TransactionSummary.get(i);
    		}
    		System.out.println("Total: "+sum);
    	} finally {
    		readLock.unlock();
    	}
    }
	
    public double getBankBalance() {
    	readLock.lock();
    	double BankBalance=0;
    	try {
    		BankBalance=this.availableBalance;
    	} finally {
    		readLock.unlock();
    	} 
    	return BankBalance;
    }
    
    public String getAccountHolderName() {
    	return this.DepositorName;
    }
    
    @Override 
    public String toString() {
    	return "DepositorName: "+this.DepositorName+"\nAccount Number: "+this.accountNumber+"\nBank Balance: "+this.availableBalance+"\nBank Branch Name: "+this.BranchName+"\n";
    }
	
}
