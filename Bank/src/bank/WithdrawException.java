package bank;

/**
 * @author Papanastasis Zacharias
 */
public class WithdrawException extends Exception {    // εξαίρεση ανάληψης

    public WithdrawException(String message) {       
        super(message);
    }

}