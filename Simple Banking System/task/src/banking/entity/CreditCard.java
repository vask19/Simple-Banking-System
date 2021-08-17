package banking.entity;
import banking.algoritm.LuhnAlgorithm;

public class CreditCard {
    private String password;
    private String creditCardNumber;
    private long balance = 0;

    public CreditCard(){
    }
    public CreditCard(String creditCardNumber,String password,int balance){
        this.creditCardNumber = creditCardNumber;
        this.password = password;
        this.balance = balance;

    }
    public CreditCard(String accountIdentifier,String bankNumber,String password) {

        this.password = password;
        this.creditCardNumber = (bankNumber
                +accountIdentifier
                + LuhnAlgorithm.createChekSum(bankNumber + accountIdentifier));

    }
    public String getCreditCardNumber(){
        return creditCardNumber;
    }
    public String getPassword(){
        return password;
    }
    public long getBalance(){
        return balance;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public void setBalance(long balance) {
        this.balance += balance;
    }
}