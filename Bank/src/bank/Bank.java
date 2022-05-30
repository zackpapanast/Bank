package bank;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Papanastasis Zacharias
 */
public class Bank {

    private final int YEAR = 360;
    private final double RATE_SIMMPLE = 0.01;
    private final double RATE_SUPER = 0.02;
    private final double RATE_RESERVE = 0.04;
    private int date;
    private ArrayList<Client> clients;
    private ArrayList<Account> accounts;

    public static void main(String[] args) {    

        Bank bank = new Bank();         //Υλοποίηση της προσομοίωσης
        bank.sim();

    }

    public void sim() {

        Random random = new Random();
        String[] names = new String[]{"Eumaios", "Melpomeni", "Klitaimnistra", "Priamos", "Aiantas", "Arkantos"};   //default names

        clients = new ArrayList<>();

        date = 1;

        int id = -1;
        int name = -1;

        clients.add(new Client(names[0], 678990));          //προσθήκη client με default id
        clients.add(new Client(names[1], 678991));
        clients.add(new Client(names[2], 678992));
        clients.add(new Client(names[3], 678993));
        clients.add(new Client(names[4], 678994));
        clients.add(new Client(names[5], 678995));

        clients.get(0).getAccounts().add(new SimpleCreditAccount(180, 101, 2500.00, RATE_SIMMPLE));                                                                       //Υλοποίηση λογαριασμών για 5 πελάτες 
        clients.get(0).getAccounts().add(new ReserveAccount(301, 500.00, (RATE_RESERVE + 0.02), 60, 1, (CreditAccount) clients.get(0).getAccounts().get(0)));
        clients.get(0).getAccounts().get(0).getOwners().add(clients.get(0));
        clients.get(0).getAccounts().get(1).getOwners().add(clients.get(0));
        
        clients.get(1).getAccounts().add(new SuperCreditAccount(30, 202, 2500.00, RATE_SUPER));
        clients.get(1).getAccounts().add(new ReserveAccount(302, 2500.00, RATE_RESERVE, 30, 1, (CreditAccount) clients.get(1).getAccounts().get(0)));
        clients.get(1).getAccounts().add(new ReserveAccount(303, 300.00, (RATE_RESERVE + 0.04), 270, 1, (CreditAccount) clients.get(1).getAccounts().get(0)));
        clients.get(1).getAccounts().get(0).getOwners().add(clients.get(1));
        clients.get(1).getAccounts().get(1).getOwners().add(clients.get(1));
        clients.get(1).getAccounts().get(2).getOwners().add(clients.get(1));
        
        clients.get(2).getAccounts().add(new SimpleCreditAccount(180, 103, 1000.00, RATE_SIMMPLE));
        clients.get(2).getAccounts().get(0).getOwners().add(clients.get(2));
        
        clients.get(3).getAccounts().add(new SimpleCreditAccount(180, 104, 10000.00, RATE_SIMMPLE));
        clients.get(3).getAccounts().add(new SimpleCreditAccount(180, 104, 5000.00, RATE_SIMMPLE));
        clients.get(3).getAccounts().get(0).getOwners().add(clients.get(3));
        clients.get(3).getAccounts().get(1).getOwners().add(clients.get(3));
        
        clients.get(4).getAccounts().add(new SuperCreditAccount(30, 204, 800.00, RATE_SUPER));
        clients.get(4).getAccounts().get(0).getOwners().add(clients.get(4));

        clients.get(5).getAccounts().add(new SuperCreditAccount(30, 205, 15000.00, RATE_SUPER));
        clients.get(5).getAccounts().get(0).getOwners().add(clients.get(5));

        SimpleCreditAccount simp = new SimpleCreditAccount(180, 105, 600.00, RATE_SIMMPLE);
        SuperCreditAccount sup = new SuperCreditAccount(30, 206, 1000.00, RATE_SUPER);
        ReserveAccount reserv = new ReserveAccount(304, 1500.00, RATE_RESERVE, 30, 1, (CreditAccount) sup);

        reserv.getOwners().add(clients.get(3));                                                                 // Προσθήκη κατόχων προθεσμιακού λογαριασμού
        reserv.getOwners().add(clients.get(4));
        clients.get(3).getAccounts().add(reserv);

        sup.getOwners().add(clients.get(3));                                                                    // Προσθήκη κατόχων υπερταμιευτηρίου
        sup.getOwners().add(clients.get(4));
        clients.get(3).getAccounts().add(sup);

        simp.getOwners().add(clients.get(2));                                                                   // Προσθήκη κατόχων απλού λογαριασμού
        simp.getOwners().add(clients.get(5));
        clients.get(5).getAccounts().add(simp);

        accounts = new ArrayList<>();

        accounts.add(sup);
        accounts.add(reserv);
        accounts.add(simp);

        int transNum = 0;
        int amount = 0;
        int type = -1;
        ArrayList<Transaction> trans = null;

        System.out.println("\t\t\t\t\tClients Info");                                                          //Εμφάνιση στοιχείων πελάτών
        for (int i = 0; i < this.clients.size(); i++) {
            System.out.println("\t\t\t" + clients.get(i).toString());
        }
        System.out.println();
        System.out.println();


        while (date <= YEAR) {

            if (date % 30 == 0) {
                for (int i = 0; i < clients.size(); i++) {
                    for (int j = 0; j < clients.get(i).getAccounts().size(); j++) {
                        if (clients.get(i).getAccounts().get(j) instanceof SuperCreditAccount) {  //Ελγέχεται αν το getAccounts αντιστοιχεί στην κλάση SuperCreditAccount 
                            SuperCreditAccount.setWithdrawFlag(0);
                        }
                    }
                }
            }


            transNum = random.nextInt(6);

            for (int i = 0; i < clients.size(); i++) {
                for (int j = 0; j < clients.get(i).getAccounts().size(); j++) {
                    if (clients.get(i).getAccounts().get(j) instanceof CreditAccount) {            //Ελγέχεται αν το getAccounts αντιστοιχεί στην κλάση CreditAccount
                        CreditAccount acc = (CreditAccount) clients.get(i).getAccounts().get(j);
                        if (date == acc.getTimeInterest()) {
                            clients.get(i).getAccounts().get(j).payInterest(date);
                        }
                    } else if (clients.get(i).getAccounts().get(j) instanceof ReserveAccount) {   //Ελγέχεται αν το getAccounts αντιστοιχεί στην κλάση ReserveAccount
                        ReserveAccount acc = (ReserveAccount) clients.get(i).getAccounts().get(j);
                        if (date == acc.getTimeReserved()) {
                            clients.get(i).getAccounts().get(j).payInterest(date);
                        }
                    }

                }
            }

            if (date % 7 == 0) {                                                        // Κάθε φορά που το υπόλοιπο της διαίρεσης των μερών με το 7 δημιουργεί συναλλαγές με τυχαία στοιχεία

                trans = new ArrayList<>();                                          
                for (int i = 0; i < transNum; i++) {
                    amount = 50 + random.nextInt(100);
                    type = random.nextInt(2);
                    if (type == 1) {
                        amount = (0 - amount);
                    }
                    trans.add(new Transaction(date, amount));
                }

                System.out.println("\t\t\t\t\tTransactions Info - Date: " + date);      //Εμφάνιση Συναλλαγών
                for (int i = 0; i < trans.size(); i++) {
                    System.out.println("\t\t\t" + trans.get(i).toString());
                }
                System.out.println();

                try {
                    for (int i = 0; i < trans.size(); i++) {
                        int j = random.nextInt(6);
                        int size = clients.get(j).getAccounts().size();
                        int accNum = random.nextInt(size);

                        if (trans.get(i).getAmount() >= 0) {
                            clients.get(j).getAccounts().get(accNum).deposit(trans.get(i));
                            clients.get(j).getAccounts().get(accNum).getTransaction().add(trans.get(i));
                        } else {
                            clients.get(j).getAccounts().get(accNum).withdraw(trans.get(i));
                            clients.get(j).getAccounts().get(accNum).getTransaction().add(trans.get(i));
                        }
                    }
                } catch (DepositException ex) {
                    System.out.println(ex.getMessage());
                } catch (WithdrawException ex) {
                    System.out.println(ex.getMessage());
                } catch (IndexOutOfBoundsException ex) {
                    System.out.println("\t\t\t\t\t   Zero Transactions Today!");      //Εμφάνιση όταν δεν μπορεί να γίνει συναλλαγή
                    System.out.println();
                    System.out.println();
                    date++;
                    continue;
                }

                System.out.println();

                System.out.println("\t\t\t\t\tAccounts Info");                          //Εμφάνιση λογαριασμών
                for (int i = 0; i < this.clients.size(); i++) {
                    for (int j = 0; j < clients.get(i).getAccounts().size(); j++) {
                        System.out.println("\t\t\t" + clients.get(i).getAccounts().get(j).toString());
                    }
                }
                System.out.println();
                System.out.println();



            }

            date++;

        }
    }
}
