package banking.database;


import banking.entity.CreditCard;
import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class DataBase {
    private String url;
    private Connection connection;
    private String  createNewDB = "CREATE TABLE IF NOT EXISTS card(" +
            "id INTEGER PRIMARY KEY," +
            "number TEXT," +
            "pin TEXT," +
            "balance INTEGER DEFAULT 0)";
    private final String insert = "INSERT INTO card(number,pin) VALUES(? , ?)";
    private final String select = "SELECT * FROM card where number = ? AND pin = ?";
    private final String updateBalance = "UPDATE card SET balance = balance + ? where number = ?";
    private final String deleteCard = "DELETE FROM card WHERE number = ?";
    private final String selectCardNumber = "SELECT number FROM card WHERE number = ?";
    private final String selectBalance = "SELECT balance FROM card WHERE number = ?";


    private final String awayForCard = "UPDATE card SET balance = balance - ? WHERE number = ?";
    private final String addForCard = "UPDATE card SET balance = balance + ? WHERE number = ?";



    public DataBase(String url){
        this.url = url;

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        try {
            connection = dataSource.getConnection();
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createNewDB);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public CreditCard loggingInAccount(CreditCard guessCreditCard){
        try (PreparedStatement preparedStatement = connection.prepareStatement(select)){
            preparedStatement.setString(1,guessCreditCard.getCreditCardNumber());
            preparedStatement.setString(2,guessCreditCard.getPassword());

            ResultSet resultSet = preparedStatement
                    .executeQuery();

            if (resultSet.next()){
                return new CreditCard(resultSet.getString("number"),
                        resultSet.getString("pin"),
                        resultSet.getInt("balance"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createNewAccount(CreditCard creditCard){
        try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setString(1,creditCard.getCreditCardNumber());
            preparedStatement.setString(2,creditCard.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void income(CreditCard creditCard,int money){
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateBalance)){
            preparedStatement.setInt(1,money);
            preparedStatement.setString(2,creditCard.getCreditCardNumber());

            preparedStatement.executeUpdate();
            creditCard.setBalance(money);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void closeAccount(CreditCard creditCard){
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteCard)){
            preparedStatement.setString(1,creditCard.getCreditCardNumber());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void transfer(String fromCreditCard,String toCreditCardNumber,int money){
        try (
                PreparedStatement awayForCardStatement = connection.prepareStatement(awayForCard);
                PreparedStatement addForCardStatement = connection.prepareStatement(addForCard)

        ){

            connection.setAutoCommit(false);
            awayForCardStatement.setString(2,fromCreditCard);
            awayForCardStatement.setInt(1,money);

            addForCardStatement.setString(2,toCreditCardNumber);
            addForCardStatement.setInt(1,money);

            awayForCardStatement.executeUpdate();
            addForCardStatement.executeUpdate();

            connection.commit();




        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public boolean findCreditCard(String cardNumber){
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectCardNumber)){
            preparedStatement.setString(1,cardNumber);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return true;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public int getBalance(String cardNumber){
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectBalance)){
            preparedStatement.setString(1,cardNumber);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) return rs.getInt("balance");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }



}