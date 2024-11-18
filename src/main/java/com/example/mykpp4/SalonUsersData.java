package com.example.mykpp4;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalonUsersData {

    private static final List<SalonUsersData> Users_data = new ArrayList<>();
    private Integer ID;
    private String user_name;
    private String user_surName;
    private String user_thirdName;
    private String gmail;
    private String phoneNum;

    public SalonUsersData(Integer ID, String user_name, String user_surName, String user_thirdName, String gmail, String phoneNum) {
        this.ID = ID;
        this.user_name = user_name;
        this.user_surName = user_surName;
        this.user_thirdName = user_thirdName;
        this.gmail = gmail;
        this.phoneNum = phoneNum;
    }

    public static void addUsertoClass(Integer ID, String user_name, String user_surName, String user_thirdName, String gmail, String phoneNum) {
        new SalonUsersData(ID, user_name, user_surName, user_thirdName, gmail, phoneNum);
    }
    public static List<SalonUsersData> getAllUsers() {
        return new ArrayList<>(Users_data);
    }
    public static void addUser(Integer ID, String user_name, String user_surName, String user_thirdName, String gmail, String phoneNum) {
        String sqlClients = "INSERT INTO clients (ID, first_name, sur_name, third_name, gmail, phone_num) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnectionLab2.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlClients)) {

            preparedStatement.setInt(1, ID);
            preparedStatement.setString(2, user_name);
            preparedStatement.setString(3, user_surName);
            preparedStatement.setString(4, user_thirdName);
            preparedStatement.setString(5, gmail);
            preparedStatement.setString(6, phoneNum);

            preparedStatement.executeUpdate();

            Users_data.add(new SalonUsersData(ID, user_name, user_surName, user_thirdName, gmail, phoneNum));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void initializeUsers() {
        Users_data.clear();

        String sqlClients = "SELECT * FROM clients";
        try (Connection connection = DBConnectionLab2.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlClients)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String firstName = resultSet.getString("first_name");
                String surName = resultSet.getString("sur_name");
                String thirdName = resultSet.getString("third_name");
                String email = resultSet.getString("gmail");
                String phoneNum = resultSet.getString("phone_num");

                Users_data.add(new SalonUsersData(id, firstName, surName, thirdName, email, phoneNum));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<String> getAllUserFullNames() {
        List<String> usersFullNames = new ArrayList<>();
        for (SalonUsersData users : Users_data) {
            usersFullNames.add(users.getUser_surName() + " " +
                    users.getUser_name().charAt(0) + "." +
                    users.getUser_thirdName().charAt(0) + ".");
        }
        return usersFullNames;
    }
    public static List<SalonUsersData> searchUsers(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return getAllUsers();
        }

        List<SalonUsersData> filteredUsers = new ArrayList<>();
        String lowerCaseSearchTerm = searchTerm.toLowerCase();

        for (SalonUsersData user : Users_data) {
            if (user.getUser_name().toLowerCase().contains(lowerCaseSearchTerm) ||
                    user.getUser_surName().toLowerCase().contains(lowerCaseSearchTerm) ||
                    user.getGmail().toLowerCase().contains(lowerCaseSearchTerm) ||
                    user.getPhoneNum().toLowerCase().contains(lowerCaseSearchTerm)) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }
    public static void removeUserByID(Integer id) {
        String sqlClients = "DELETE FROM clients WHERE ID = ?";
        try (Connection connection = DBConnectionLab2.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlClients)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            Users_data.removeIf(user -> user.getID().equals(id));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateUserByID(Integer id, String user_name, String user_surName, String user_thirdName, String gmail, String phoneNum) {
        String sqlClients = "UPDATE clients SET first_name = ?, sur_name = ?, third_name = ?, gmail = ?, phone_num = ? WHERE ID = ?";
        try (Connection connection = DBConnectionLab2.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlClients)) {

            preparedStatement.setString(1, user_name);
            preparedStatement.setString(2, user_surName);
            preparedStatement.setString(3, user_thirdName);
            preparedStatement.setString(4, gmail);
            preparedStatement.setString(5, phoneNum);
            preparedStatement.setInt(6, id);

            preparedStatement.executeUpdate();

            initializeUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static SalonUsersData getUserByID(Integer id) {
        for (SalonUsersData user : Users_data) {
            if (user.getID().equals(id)) {
                return user;
            }
        }
        return null;
    }
    public Integer getID() {
        return ID;
    }
    public String getUser_name() {
        return user_name;
    }
    public String getUser_surName() {
        return user_surName;
    }
    public String getUser_thirdName() {
        return user_thirdName;
    }
    public String getGmail() {
        return gmail;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
}