package banking;

import banking.algoritm.LuhnAlgorithm;
import banking.database.DataBase;
import banking.entity.CreditCard;
import java.util.Scanner;

public class BankSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private String guessAnswer = "-";
    private final DataBase dataBase;

    public BankSystem(String url){
        this.dataBase = new DataBase(url);
    }
    //1
    public void startSystem(){
        do{
            if (guessAnswer.equals("0")){
                return;
            };
            System.out.println("\n1. Create an account\n" +
                    "2. Log into account\n" +
                    "0. Exit\n");
            guessAnswer = scanner.next();

            switch (guessAnswer){
                case "1":
                    createNewAccount();
                    break;
                case "2":
                    loggingInAccount();
                    break;
            }
        }while(true);
    }
    //2.1
    private void createNewAccount(){
        CreditCard creditCard = CreditCardBuilder.createCreditCard();
        dataBase.createNewAccount(creditCard);
        System.out.println( "Your card has been created" + "\nYour card number:\n" + creditCard.getCreditCardNumber() +
                "\nYour card PIN:\n" + creditCard.getPassword());
    }
    //2.2
    private void loggingInAccount() {
        System.out.println("Enter your card number:");
        String guessCardNumber = scanner.next();    //
        System.out.println("Enter your PIN:");
        String guessPassword = scanner.next();       //
        CreditCard guessCreditCard =
                dataBase.loggingInAccount(new CreditCard(guessCardNumber,guessPassword,0));

        if (guessCreditCard != null &
                LuhnAlgorithm.chekValidNumberForCreditCard(guessCardNumber))
            workInPersonalAccount(guessCreditCard);
        else
            System.out.println("Wrong card number or PIN!");
    }
    //3
    private void workInPersonalAccount(CreditCard creditCard){
        System.out.println("You have successfully logged in!");
        do {
            if (guessAnswer.equals("0")) return;
            System.out.println(
                    "1. Balance\n" +
                            "2. Add income\n" +
                            "3. Do transfer\n" +
                            "4. Close account\n" +
                            "5. Log out\n" +
                            "0. Exit");
            guessAnswer = scanner.next();
            switch (guessAnswer){
                case "1":
                    System.out.println("Balance: "+ dataBase.getBalance(creditCard.getCreditCardNumber()));
                    break;
                case "2":
                    income(creditCard);
                    break;
                case "3":
                    transfer(creditCard);
                    break;
                case "4":
                    closeAccount(creditCard);
                    return;

                case  "5":
                    System.out.println("You have successfully logged out!");
                    return;
                default:
                    System.out.println("Bye");
                    break;
            }
        }while(true);

    }

    private void transfer(CreditCard creditCard){
        System.out.println("Transfer\nEnter card number:");
        String toCard = scanner.next();
        int money = 0;

        if (!LuhnAlgorithm.chekValidNumberForCreditCard(toCard)){
            System.out.println("Probably you made a mistake in the card number. Please try again!");
        }
        else if (!dataBase.findCreditCard(toCard)){
            System.out.println("Such a card does not exist");
        }
        else{
            System.out.println("Enter how much money you want to transfer:");
            money  = scanner.nextInt();}

        if (creditCard.getBalance() < money){
            System.out.println("Not enough money!");
        }
        else if (creditCard.getCreditCardNumber().equals(toCard)){
            System.out.println("You can't transfer money to the same account!");
        }

        else {
            dataBase.transfer(creditCard.getCreditCardNumber(),toCard,money);
            System.out.println("Success!");
        }

    }

    private void closeAccount(CreditCard creditCard){
        dataBase.closeAccount(creditCard);
        System.out.println("The account has been closed!");


    }

    private void income(CreditCard creditCard){
        System.out.println("Enter income:");
        int money = scanner.nextInt();
        dataBase.income(creditCard,money);
        System.out.println("Income was added!");

    }
}