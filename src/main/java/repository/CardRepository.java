package repository;

import db.DataBase;
import dto.Card;
import dto.Profile;
import enums.CardStatus;


import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CardRepository {

    public Card getCard(String numCard) {
        String sql = "select * from card " +
                "where number = ? ";

        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, numCard);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                Card card = new Card();
                card.setId(set.getInt("id"));
                card.setNumber(set.getString("number"));
                card.setExp_date(set.getString("exp_date"));
                card.setAmount(set.getDouble("amount"));
                card.setProfile_id(set.getInt("profile_id"));
                card.setCreated_date(set.getTimestamp("created_date").toLocalDateTime());
                card.setStatus(CardStatus.valueOf(set.getString("status")));
                return card;
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;


    }

    public void addCardToUser(Profile profile, Card card) {
        String sql = "update card  " +
                "set profile_id = ? , added_date = now()," +
                "status ='ACTIVE' " +
                "where id = ?";
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, profile.getId());
            statement.setInt(2, card.getId());
            statement.executeUpdate();
            connection.close();
            System.out.println("Successfully added card");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createCard(String number, String exp_date) {
        String sql = "insert into card (number,exp_date)" +
                "values(?,?)";
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, number);
            statement.setString(2, exp_date);
            statement.executeUpdate();
            connection.close();
            System.out.println("Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public List<Card> cardList() {
        String sql = "select * from card ";
        List<Card> cardList = new LinkedList<>();
        try {
            Connection connection = DataBase.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            cardList = getCardByResultSet(resultSet);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cardList;
    }

    public void updateCardByNumber(Card card, String newCardNum, String new_exp_date) {
        String sql = "update card " +
                "set number = ? , exp_date = ? " +
                "where number = ? and exp_date = ?";
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newCardNum);
            statement.setString(2, new_exp_date);
            statement.setString(3, card.getNumber());
            statement.setString(4, card.getExp_date());
            statement.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCardStatus(Card card) {
        String sql = "update card " +
                "set status = ? " +
                " where number = ? and exp_date = ?";
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, card.getStatus().name());
            statement.setString(2, card.getNumber());
            statement.setString(3, card.getExp_date());
            statement.executeUpdate();
            connection.close();
            System.out.println("Successfully update card");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCard(Card card) {
        String sql = "delete from card " +
                "where number = ? and exp_date = ?";
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, card.getNumber());
            statement.setString(2, card.getExp_date());
            statement.executeUpdate();
            System.out.println("Successfully");
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Card> cardListUser(Profile profile) {
        String sql = "select * from card " +
                "where profile_id = ? ";
        List<Card> cardList = new LinkedList<>();
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,profile.getId());
            ResultSet resultSet = statement.executeQuery();
            cardList = getCardByResultSet(resultSet);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cardList;

    }

    private List<Card> getCardByResultSet(ResultSet resultSet) {
        List<Card> cardList = new LinkedList<>();
        try {
            while (resultSet.next()) {
                Card card = new Card();
                card.setId(resultSet.getInt("id"));
                card.setNumber(resultSet.getString("number"));
                card.setExp_date(resultSet.getString("exp_date"));
                card.setAmount(resultSet.getDouble("amount"));
                card.setProfile_id(resultSet.getInt("profile_id"));
                card.setCreated_date(resultSet.getTimestamp("created_date").toLocalDateTime());
                if(resultSet.getTimestamp("added_date")!=null){
                    card.setAdded_date(resultSet.getTimestamp("added_date").toLocalDateTime());
                }
                card.setStatus(CardStatus.valueOf(resultSet.getString("status")));
                cardList.add(card);

            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return cardList;

    }

    public void updateCardBalance(Card card) {
        String sql = "update card " +
                "set amount = ?" +
                " where number = ? and exp_date = ?";
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, card.getAmount());
            statement.setString(2, card.getNumber());
            statement.setString(3, card.getExp_date());
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
