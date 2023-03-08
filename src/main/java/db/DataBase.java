package db;

import java.sql.*;

public class DataBase {
    public static void init() {
        initProfile();
        initAdmin("ravshan","ahmedov","222222","22");
        initCard();
        initCompanyCard("7777","12/23",0.0d);
        terminalInit();
        initTransaction();
    }
    public static void initAdmin(String name,String surname,String password,String phone){
        String sql = "insert into profile(id,name,surname,password,phone,status,role) " +
                "values (-1,?,?,?,?,'ACTIVE','ADMIN')";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,name);
            statement.setString(2,surname);
            statement.setString(3,password);
            statement.setString(4,phone);
            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void initCompanyCard(String number,String exp_date,Double amount){
        String sql = "insert into card(number,exp_date,amount,profile_id,added_date,status) " +
                "values (?,?,?,-1,now(),'ACTIVE')";
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,number);
            statement.setString(2,exp_date);
            statement.setDouble(3,amount);
            statement.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void initProfile() {
        String sql = "create table if not exists  profile(id serial primary key," +
                "name varchar ," +
                "surname varchar," +
                "password varchar ," +
                "phone  varchar unique  ," +
                "created_date timestamp default now()," +
                "status varchar default 'REGISTRATION'," +
                "role varchar default 'USER');";
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void initCard() {
        String sql = "create table if not exists  card(id serial primary key," +
                "number  varchar unique," +
                "exp_date varchar," +
                "amount  decimal(10,4) default 0 ," +
                "profile_id  int ," +
                "created_date timestamp default now()," +
                "added_date timestamp ," +
                "status varchar default 'NOACTIVE')";
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void terminalInit() {
        String sql = "create table if not exists  terminal(id serial primary key," +
                "number   varchar unique ," +
                "address varchar," +
                "created_date timestamp default now()," +
                "status varchar default 'ACTIVE')";
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void initTransaction() {
        String sql = "create table if not exists  transaction(id serial primary key," +
                "profile_id int, " +
                "card_id int  ," +
                "terminal_id int, " +
                "amount decimal(10,2) default 1400.00," +
                "created_date timestamp default now()," +
                "type varchar default 'PAYMENT'," +
                "foreign key (card_id) references card(id)," +
                "foreign key (profile_id) references profile(id)," +
                "foreign key (terminal_id) references terminal(id))";
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/db_lesson", "postgres", "12345");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }


}
