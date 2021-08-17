package banking;

import banking.entity.CreditCard;

import java.util.Random;

public class CreditCardBuilder {
    private static int positionAccountIdentifier = 0;
    private static int numberPositionAccountIdentifier = 1;
    private static String bankNumber = "400000";


    public static CreditCard createCreditCard(){
        return new CreditCard(createAccountIdentifier(),bankNumber,createPassword());

    }


    private static String createAccountIdentifier(){
        StringBuilder accountIdentifier = new StringBuilder();
        if (numberPositionAccountIdentifier == 9) {
            positionAccountIdentifier++;
            numberPositionAccountIdentifier = 0;
        }
        for (int i=0;i<9;i++){
            if (i< positionAccountIdentifier)
                accountIdentifier.append("9");
            else if (i== positionAccountIdentifier)
                accountIdentifier.append(numberPositionAccountIdentifier++);//
            else
                accountIdentifier.append("0");
        }
        return accountIdentifier.toString();
    }

    private static String createPassword(){
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        password.append(random.nextInt(7)+1);
        for (int i =0;i<3;i++){
            int r = random.nextInt(9);
            password.append(r);
        }
        return password.toString();
    }
}