package bank;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * @author Papanastasis Zacharias
 */
public abstract class Account {    // Δημιουργία Λογαριασμού

    private int acc;                              
    private double rate;
    private double balance;
    private ArrayList<Client> owners;
    private ArrayList<Transaction> transaction;

    public Account(int acc, double balance, double rate) {  
        this.acc = acc;
        this.balance = balance;
        this.rate = rate;
        this.owners = new ArrayList<>();
        this.transaction = new ArrayList<>();
    }

    public int getAcc() {
        return acc;
    }

    public double getBalance() {
        return balance;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setBalance(double balance) {
        this.balance = this.balance + balance;
    }
    
    public double getRate() {
        return rate;
    }

    public ArrayList<Client> getOwners() {
        return owners;
    }

    public ArrayList<Transaction> getTransaction() {
        return transaction;
    }

    public abstract void deposit(Transaction trans) throws DepositException;

    public abstract void withdraw(Transaction trans) throws WithdrawException;

    public abstract void payInterest(int date);

    public abstract void close(int date);
    
    public String toString() {
        StringBuilder string = new StringBuilder();  //Χρησιμοποίηση της stringbuilder για ευανάγνωστη εμφάνιση
        
        for (int i = 0; i < this.owners.size(); i++) {
            string.append("ID: ");
            string.append(this.owners.get(i).getId());
            string.append(", Name: ");
            string.append(this.owners.get(i).getName());
            string.append(" -> ");
        }
        
        if (string.length() > 0) {
            string.delete(string.length() - 4, string.length()); 
        }
        
        StringBuilder stringT = new StringBuilder();
        
        for (int i = 0; i < this.transaction.size(); i++) {
            stringT.append("Date: ");
            stringT.append(this.transaction.get(i).getDate());
            stringT.append(", $");
            stringT.append(new DecimalFormat("0.00").format(this.transaction.get(i).getAmount()));
            stringT.append(" -> ");
        }
        
        if (stringT.length() > 0) {
            stringT.delete(stringT.length() - 4, stringT.length());
        }
        
        
        return "#Acc: " + acc + " \t\t\t#Balance: $" + new DecimalFormat("#.##").format((double)balance) + " \t\t\t#Rate: " + rate + " \t\t\t\t#Owners: " + string.toString() + " \t\t\t\t\t#Transactions: " + stringT.toString();
    }
    //χρησιμοποίηση της decimalformat για την εμφάνιση
}
