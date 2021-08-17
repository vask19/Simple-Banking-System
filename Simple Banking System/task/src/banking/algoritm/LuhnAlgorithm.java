package banking.algoritm;

public  class LuhnAlgorithm {

    public static  int createChekSum(String cardNumber){
        int sum = 0;
        int chekSum = 0;
        for (int position =2;position<cardNumber.length()+2;position++){
            int digit = (cardNumber.charAt(position-2)-'0');
            if (position %2 != 0) sum += digit;
            else {
                digit *=2;
                if (digit>9) sum+= (digit-9);
                else sum += digit;
            }
        }

        for (int i=0;i<10;i++){
            if ((sum + i)%10 == 0){
                chekSum = i;
                break;
            }
        }
        return chekSum;
    }
    public static boolean chekValidNumberForCreditCard(String cardNumber){
        if (cardNumber.length() == 16){
            return ((cardNumber.charAt(cardNumber.length()-1))-'0' ==
                    (createChekSum(cardNumber.substring(0, cardNumber.length() - 1))));
        }
        return false;





    }


}