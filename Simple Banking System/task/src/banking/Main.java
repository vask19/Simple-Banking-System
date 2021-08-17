package banking;

import banking.algoritm.LuhnAlgorithm;

public class Main {
    public static void main(String[] args) {

       String url = "jdbc:sqlite:" + args[1];
      //  String url = "jdbc:sqlite:C:/SQLite/bankSystem.db";
        BankSystem bankSystem = new BankSystem(url);
        bankSystem.startSystem();

    }

}