package repository;

import db.DataBase;
import dto.Profile;
import enums.Role;
import enums.Status;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProfileRepository {

    public void registration(Profile profile) {
        String sql = "insert into profile(name,surname,phone,password) " +
                "values(?,?,?,?)";
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, profile.getName());
            statement.setString(2, profile.getSurname());
            statement.setString(3, profile.getPhone());
            statement.setString(4, profile.getPassword());
            int n = statement.executeUpdate();
            if (n == 1) {
                System.out.println("Successfully");
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Profile getProfileByPhone(String phone) {
        String sql = "select * from profile " +
                "where phone = ?";
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, phone);
            ResultSet set = statement.executeQuery();
            List<Profile> profileList = getProfileByResultSet(set);
            if (profileList.size()==1){
                return profileList.get(0);
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;


    }

    private List<Profile> getProfileByResultSet(ResultSet set) {
        List<Profile> profileList = new LinkedList<>();
        try {
            while (set.next()) {
                Profile profile = new Profile();
                profile = new Profile();
                profile.setId(set.getInt("id"));
                profile.setName(set.getString("name"));
                profile.setSurname(set.getString("surname"));
                profile.setPhone(set.getString("phone"));
                profile.setPassword(set.getString("password"));
                profile.setLocalDateTime(set.getTimestamp("created_date").toLocalDateTime());
                profile.setRole(Role.valueOf(set.getString("role")));
                profile.setStatus(Status.valueOf(set.getString("status")));
                profileList.add(profile);
            }
            return profileList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Profile login(String phone, String password) {
        Profile profile = getProfileByPhone(phone);
        if (profile != null && profile.getPassword().equals(password)) {
            return profile;
        } else {
            return null;
        }
    }

    public List<Profile> profileList() {
        String sql = "select * from profile ";
        List<Profile> profileList = new LinkedList<>();
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet set = statement.executeQuery();
            profileList = getProfileByResultSet(set);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profileList;
    }

    public void updateProfileStatus(Profile profile) {
        String sql = "update   profile " +
                "set status = ? " +
                "where phone = ? and password =?";
        try {
            Connection connection = DataBase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, profile.getStatus().name());
            statement.setString(2, profile.getPhone());
            statement.setString(3, profile.getPassword());
            statement.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
