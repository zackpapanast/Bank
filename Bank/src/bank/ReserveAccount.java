package bank;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Papanastasis Zacharias
 */
public class ReserveAccount extends Account{     // υλοποίηση προθεσμιακού λογαριασμού

    private int timeReserved;
    private int expectClose;
    private CreditAccount connected;
    
    public ReserveAccount(int acc, double balance, double rate, int timeReserved, int date, CreditAccount connected) {
        super(acc, balance, rate);
        this.timeReserved = timeReserved;
        this.expectClose = date + timeReserved;
        this.connected = connected;
    }

    public void setTimeReserved(int timeReserved) {
        this.timeReserved = timeReserved;
    }

    public int getTimeReserved() {
        return timeReserved;
    }

    @Override
    public void deposit(Transaction trans) throws DepositException {
        throw new DepositException("Δεν επιτρέπονται καταθέσεις. Προθεσμιακος Λογαριασμος.");
    }

    @Override
    public void withdraw(Transaction trans) throws WithdrawException {
        throw new WithdrawException("Δεν επιτρέπονται αναλήψεις. Προθεσμιακος Λογαριασμος.");
    }

    @Override
    public void close(int date) {                //μέθοδος για το κλείσιμο του λογαριασμού
        if (date >= this.expectClose) {
            this.payInterest(date);
        } else {
            Transaction trans = new Transaction(date, (super.getBalance()));
            connected.getTransaction().add(trans);
            connected.setBalance((super.getBalance()));
        }
        for (int i = 0; i < super.getOwners().size(); i++) {
            super.getOwners().remove(i);
        }
    }

    @Override
    public void payInterest(int date) {        // τοκισμός λογαριασμού
        double interest = (super.getBalance() * super.getRate()) / (double)(360 * this.timeReserved);
        Transaction trans = new Transaction(date, interest);
        connected.getTransaction().add(trans);
        try {
            this.connected.deposit(trans);
        } catch (DepositException ex) {
            System.out.println(ex.getMessage());
        }
        
        System.out.println("Τοκισμός σε λογαριασμο Reserve Επιτυχης!");
    }
    
}
