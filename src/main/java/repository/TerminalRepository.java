package repository;

import db.DataBase;
import dto.Terminal;
import enums.TerminalStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TerminalRepository {

    public Terminal getTerminalByNumber(String number) {
        String sql = "select * from terminal " +
                "where number = ?";
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, number);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                Terminal terminal = new Terminal();
                terminal.setId(set.getInt("id"));
                terminal.setNumber(set.getString("number"));
                terminal.setAddress(set.getString("address"));
                terminal.setLocalDateTime(set.getTimestamp("created_date").toLocalDateTime());
                terminal.setStatus(TerminalStatus.valueOf(set.getString("status")));
                return terminal;
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;


    }

    public void createTerminal(String number, String address) {
        String sql = "insert into terminal (number,address)" +
                " values (?,?)";
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, number);
            statement.setString(2, address);
            statement.executeUpdate();
            System.out.println("Successfully");
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Terminal> terminalList() {
        String sql = "select * from terminal ";
        List<Terminal> terminalList = new LinkedList<>();
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Terminal terminal = new Terminal();
                terminal.setId(set.getInt("id"));
                terminal.setNumber(set.getString("number"));
                terminal.setAddress(set.getString("address"));
                terminal.setLocalDateTime(set.getTimestamp("created_date").toLocalDateTime());
                terminal.setStatus(TerminalStatus.valueOf(set.getString("status")));
                terminalList.add(terminal);
            }
            connection.close();
            return terminalList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void updateTerminalByNumber(Terminal terminal, String newTerminalNum, String address) {
        String sql = "update terminal " +
                "set number = ? , address = ? " +
                "where number = ? and address = ?";
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,newTerminalNum);
            statement.setString(2,address);
            statement.setString(3,terminal.getNumber());
            statement.setString(4,terminal.getAddress());
            statement.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTerminalStatus(Terminal terminal) {
        String sql = "update terminal " +
                "set status = ? " +
                " where number = ? and address = ?";
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,terminal.getStatus().name());
            statement.setString(2,terminal.getNumber());
            statement.setString(3,terminal.getAddress());
            statement.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTerminal(Terminal terminal) {
        String sql = "delete from  terminal " +
                "where number = ? and address = ?";
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,terminal.getNumber());
            statement.setString(2,terminal.getAddress());
            statement.executeUpdate();
            System.out.println("Successfully");
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
