package bank;

import java.util.ArrayList;

/**
 * @author Papanastasis Zacharias
 */
public class SimpleCreditAccount extends CreditAccount {             // Υπολογισμός τόκου και υπολοίπου απλού ταμιευτηρίου

    public SimpleCreditAccount(int timeInterest, int acc, double balance, double rate) {
        super(timeInterest, acc, balance, rate);
    }

    @Override
    public void deposit(Transaction trans) throws DepositException {
        super.setBalance(trans.getAmount());
        System.out.println("Καταθεση σε λογαριασμο Simple Επιτυχης!");
    }

    @Override
    public void withdraw(Transaction trans) throws WithdrawException {
        if (super.getBalance() > trans.getAmount() && trans.getAmount() <= 1000) {  
            super.setBalance(trans.getAmount());
            System.out.println("Ανάληψη από λογαριασμο Simple Επιτυχης!");
        } else {
            if (trans.getAmount() > 1000) {
                throw new WithdrawException("Σφαλμα. Μη επιτρεπτό ποσό ανάληψης. \nΑνωτατο όριο 1000 ευρώ.");
            }
            throw new WithdrawException("Σφαλμα. Δεν επαρκει το υπολοιπο σας. \nΤρεχον Υπολοιπο Λογαριασμου: " + trans.getAmount());
        }
    }

    @Override
    public void payInterest(int date) {
        double interest = 0;
        double balance = 0;
        double sum = 0;
        int dateDiff = 0;
        double mux = 0;
        ArrayList<Transaction> trans = new ArrayList<>();

        if (date == super.getTimeInterest()) {
            dateDiff = date - 180;
        } else {
            dateDiff = 180 - (super.getTimeInterest() - date);
        }

        if (super.getTransaction().size() > 0) {
            for (int i = 0; i < super.getTransaction().size(); i++) {
                if (super.getTransaction().get(i).getDate() > dateDiff) {
                    int diff = super.getTransaction().get(i).getDate() - dateDiff + 1;
                    sum = sum + super.getTransaction().get(i).getAmount();
                    trans.add(new Transaction(diff, super.getTransaction().get(i).getAmount()));
                    dateDiff = dateDiff + diff;
                }
            }

            balance = Math.abs(sum - super.getBalance()); // χρησμιμοποίηση της απόλυτης τιμής


            int i;
            for (i = 0; i < trans.size(); i++) {
                mux = mux + (trans.get(i).getDate() * balance);
                balance = balance + trans.get(i).getAmount();
            }
            mux = mux + ((super.getTimeInterest() - trans.get(i - 1).getDate()) * balance);
        } else {
            mux = super.getBalance();
        }
        
        mux = mux / (double)dateDiff;
        
        interest = mux * super.getRate() / (double) 360 * dateDiff;

        Transaction pay = new Transaction(date, interest);

        super.getTransaction().add(pay);

        try {
            this.deposit(pay);
        } catch (DepositException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("Τοκισμός σε λογαριασμο Simple Επιτυχης!");

    }
}
