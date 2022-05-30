package bank;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @author Papanastasis Zacharias
 */
public class Client {   //Δημιουργία Πελάτη

    private String name;
    private int id;
    private ArrayList<Account> accounts;

    public Client(String name, int id) {
        this.name = name;
        this.id = id;
        accounts = new ArrayList();
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }
    
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
    
    public String toString() {
        return "ID: " + this.id + " Name: " + this.name + " Num Accounts: " + this.accounts.size();
    }
    
}
