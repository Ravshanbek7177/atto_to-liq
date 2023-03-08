package repository;

import db.DataBase;
import dto.Card;
import dto.Profile;
import dto.Terminal;
import dto.Transaction;

import enums.TransactionType;


import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class TransactionRepository {
    public void transaction(Profile profile, Card card, Terminal terminal) {
        String sql = "insert into transaction (profile_id,card_id,terminal_id)" +
                "values(?,?,?)";
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, profile.getId());
            statement.setInt(2, card.getId());
            statement.setInt(3, terminal.getId());
            statement.executeUpdate();
            connection.close();
            System.out.println("Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> transactionList(Profile profile) {
        String sql = "select * from transaction " +
                "where profile_id = ? " +
                "order by  created_date desc";
        List<Transaction> transactionList = new LinkedList<>();
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, profile.getId());
            ResultSet resultSet = statement.executeQuery();
            transactionList = getTransactionByResultSet(resultSet);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    private List<Transaction> getTransactionByResultSet(ResultSet resultSet) {
        List<Transaction> transactionList = new LinkedList<>();
        try {
            while (resultSet.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(resultSet.getInt("id"));
                transaction.setProfile_id(resultSet.getInt("profile_id"));
                transaction.setCard_id(resultSet.getInt("card_id"));
                transaction.setTerminal_id(resultSet.getInt("terminal_id"));
                transaction.setAmount(resultSet.getDouble("amount"));
                transaction.setCreated_date(resultSet.getTimestamp("created_date").toLocalDateTime());
                transaction.setTransactionType(TransactionType.valueOf(resultSet.getString("type")));
                transactionList.add(transaction);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return transactionList;

    }

    public List<Transaction> transactionListAllProfile() {
        String sql = "select * from transaction " +
                "order by  created_date desc";
        List<Transaction> transactionList = new LinkedList<>();
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            transactionList = getTransactionByResultSet(resultSet);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    public List<Transaction> paymentCurrentDay() {
        String sql = "select * from transaction " +
                "where created_date::date = now()::date " +
                "order by  created_date desc";
        List<Transaction> transactionList = new LinkedList<>();
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            transactionList = getTransactionByResultSet(resultSet);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    public List<Transaction> paymentDay(LocalDate localDate) {

        String sql = "select * from transaction " +
                "where created_date::date = ? " +
                "order by  created_date desc";
        List<Transaction> transactionList = new LinkedList<>();
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(localDate));
            ResultSet resultSet = statement.executeQuery();
            transactionList = getTransactionByResultSet(resultSet);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    public List<Transaction> intermediatePayment(LocalDate dateFrom, LocalDate dateTo) {
        String sql = "select * from transaction " +
                "where created_date >=  ? " +
                "and created_date <=? " +
                "order by  created_date desc ";
        List<Transaction> transactionList = new LinkedList<>();
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(dateFrom));
            statement.setDate(2, Date.valueOf(dateTo));
            ResultSet resultSet = statement.executeQuery();
            transactionList = getTransactionByResultSet(resultSet);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;

    }

    public List<Transaction> transactionByTerminal(Terminal terminal) {
        String sql = "select * from transaction " +
                "where terminal_id =  ? " +
                "order by  created_date desc";
        List<Transaction> transactionList = new LinkedList<>();
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, terminal.getId());
            ResultSet resultSet = statement.executeQuery();
            transactionList = getTransactionByResultSet(resultSet);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    public List<Transaction> transactionByCard(Card card) {
        String sql = "select * from transaction " +
                "where card_id =  ? " +
                "order by  created_date desc";
        List<Transaction> transactionList = new LinkedList<>();
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, card.getId());
            ResultSet resultSet = statement.executeQuery();
            transactionList = getTransactionByResultSet(resultSet);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;

    }

    public void refill(Profile profile, Card card, Double amount) {
        String sql = "insert into transaction (profile_id,card_id,amount,type)" +
                "values(?,?,?,'REFILL')";
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, profile.getId());
            statement.setInt(2, card.getId());
            statement.setDouble(3, amount);
            statement.executeUpdate();
            connection.close();
            System.out.println("Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
