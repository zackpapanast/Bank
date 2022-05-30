package bank;

import java.text.DecimalFormat;

/**
 * @author Papanastasis Zacharias
 */
public class Transaction {                  // Υλοποίηση Συναλλαγής

    private int date;
    private double amount;
    
    public Transaction(int date,double amount) {
        this.date = date;
        this.amount = amount;
    }

    public int getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }
    
    public String toString() {
        return "Date: " + this.date + " Amount: " + new DecimalFormat("0.00").format(this.amount);  // χρησιμοποίηση της decimalformat για εμφάνιση του ποσού με 2 δεκαδικά ψηφία
    }
    
}
