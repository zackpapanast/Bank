package bank;

import java.util.ArrayList;

/**
 * @author Papanastasis Zacharias
 */
public abstract class CreditAccount extends Account {             

    private int timeInterest;

    public CreditAccount(int timeInterest, int acc, double balance, double rate) {
        super(acc, balance, rate);
        this.timeInterest = timeInterest;
    }

    public int getTimeInterest() {
        return timeInterest;
    }

    public void setTimeInterest(int timeInterest) {
        this.timeInterest = timeInterest;
    }

    @Override
    public abstract void deposit(Transaction trans) throws DepositException;      //εξαίρεση κατάθεσης

    @Override
    public abstract void withdraw(Transaction trans) throws WithdrawException;   //εξαίρεση ανάληψης 

    @Override
    public abstract void payInterest(int date);

    @Override
    public void close(int date) { //κλείσιμο λογαριασμού και αφαίρεση κατόχου απότη λίστα
        payInterest(date);
        Transaction trans = new Transaction(date, (0 - super.getBalance()));
        super.getTransaction().add(trans);
        super.setBalance((0 - super.getBalance()));
        for (int i = 0; i < super.getOwners().size(); i++) {
            super.getOwners().remove(i);
        }
    }
}
