package bank;

import java.util.ArrayList;

/**
 * @author Papanastasis Zacharias
 */
public class SuperCreditAccount extends CreditAccount {

    private static int withdrawFlag;

    public SuperCreditAccount(int timeInterest, int acc, double balance, double rate) {     // Υπολογισμός τόκου και υπολοίπου υπέρ-ταμιευτηρίου 
        super(timeInterest, acc, balance, rate);
        this.withdrawFlag = 0;
    }

    public static void setWithdrawFlag(int flag) {
        withdrawFlag = flag;
    }

    @Override
    public void deposit(Transaction trans) throws DepositException {
        super.setBalance(trans.getAmount());
        System.out.println("Καταθεση σε λογαριασμο Super Επιτυχης!");
    }

    @Override
    public void withdraw(Transaction trans) throws WithdrawException {
        if (super.getBalance() > trans.getAmount() && withdrawFlag < 3) {  //έλεγχος ανάληψης έως 3 φορές
            super.setBalance(trans.getAmount());
            System.out.println("Ανάληψη από λογαριασμο Super Επιτυχης!");
        } else {
            if (trans.getAmount() > 1000) {
                throw new WithdrawException("Σφαλμα. Εχετέ ξεπερασει το οριο αναληψεων. \nΟριο 3 αναληψεις / μήνα.");
            }
            throw new WithdrawException("Σφαλμα. Δεν επαρκει το υπολοιπο σας. \nΤρεχον Υπολοιπο Λογαριασμου: " + trans.getAmount());
        }
        this.withdrawFlag++;
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
            dateDiff = date - 30;
        } else {
            dateDiff = 30 - (super.getTimeInterest() - date);
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

            balance = Math.abs(sum - super.getBalance());// χρησμιμοποίηση της απόλυτης τιμής


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

        System.out.println("Τοκισμός σε λογαριασμο Super Επιτυχης!");

    }
}
