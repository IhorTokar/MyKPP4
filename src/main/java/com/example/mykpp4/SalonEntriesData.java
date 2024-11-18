package com.example.mykpp4;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalonEntriesData {
    private static List<SalonEntriesData> entriesData = new ArrayList<>();
    private Integer ID;
    private Date entriesDate;
    private String userFullName;
    private String serviceName;
    private Double servicePrice;
    private SalonServicesData service;
    private SalonUsersData user;
    public SalonEntriesData(Integer id, Date entriesDate, SalonServicesData service, SalonUsersData user) {
        this.service = service;
        this.user = user;
        this.ID = id;
        this.entriesDate = entriesDate;
        this.userFullName = formatFullName(user);
        this.serviceName = service.getService_name();
        this.servicePrice = service.getService_price();
    }
    public static void initializeEntries() {
        entriesData.clear();

        String sqlEntries = "SELECT * FROM entries";
        try (Connection connection = DBConnectionLab2.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlEntries)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                Date entryDate = resultSet.getDate("entry_date");
                int userId = resultSet.getInt("client_ID");
                int serviceId = resultSet.getInt("service_ID");

                SalonUsersData user = SalonUsersData.getUserByID(userId);
                SalonServicesData service = SalonServicesData.getServiceByID(serviceId);

                if (user != null && service != null) {
                    entriesData.add(new SalonEntriesData(id, entryDate, service, user));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addEntry(Integer id, Date entriesDate, SalonUsersData user, SalonServicesData service) {
        String sqlInsert = "INSERT INTO entries (ID, entry_date, client_ID, service_ID) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnectionLab2.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {

            preparedStatement.setInt(1, id);
            preparedStatement.setDate(2, new java.sql.Date(entriesDate.getTime()));
            preparedStatement.setInt(3, user.getID());
            preparedStatement.setInt(4, service.getID());

            preparedStatement.executeUpdate();

            entriesData.add(new SalonEntriesData(id, entriesDate, service, user));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void removeEntryByID(Integer id) {
        String sqlDelete = "DELETE FROM entries WHERE ID = ?";
        try (Connection connection = DBConnectionLab2.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            entriesData.removeIf(entry -> entry.getID().equals(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateEntryByID(Integer id, Date entriesDate, SalonUsersData user, SalonServicesData service) {
        String sqlClients = "UPDATE entries SET client_ID = ?, service_ID = ?, entry_date = ? WHERE ID = ?";
        try (Connection connection = DBConnectionLab2.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlClients)) {

            preparedStatement.setInt(1, user.getID());
            preparedStatement.setInt(2, service.getID());
            preparedStatement.setDate(3, new java.sql.Date(entriesDate.getTime()));
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
            initializeEntries();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static String formatFullName(SalonUsersData userData) {
        return userData.getUser_surName() + " " +
                userData.getUser_name().charAt(0) + "." +
                userData.getUser_thirdName().charAt(0) + ".";
    }
    public static SalonEntriesData getEntryByID(Integer id) {
        for (SalonEntriesData entry : entriesData) {
            if (entry.getID().equals(id)) {
                return entry;
            }
        }
        return null;
    }
    public static List<SalonEntriesData> getAllEntries() {
        return new ArrayList<>(entriesData);
    }
    public Integer getID() {
        return ID;
    }
    public static List<SalonEntriesData> searchEntries(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return getAllEntries();
        }
        List<SalonEntriesData> filteredEntries = new ArrayList<>();
        String lowerCaseSearchTerm = searchTerm.toLowerCase();
        for (SalonEntriesData entry : entriesData) {
            if (entry.getID().toString().contains(lowerCaseSearchTerm) ||  entry.getUserFullName().toLowerCase().contains(lowerCaseSearchTerm) ||
                    entry.getServiceName().toLowerCase().contains(lowerCaseSearchTerm)||
                    entry.getServicePrice().toString().toLowerCase().contains(lowerCaseSearchTerm)) {
                filteredEntries.add(entry);
            }
        }
        return filteredEntries;
    }
    public Date getEntriesDate() {
        return entriesDate;
    }
    public String getUserFullName() {
        return userFullName;
    }
    public Double getServicePrice() {
        return servicePrice;
    }
    public String getServiceName() {
        return serviceName;
    }
    public SalonUsersData getUser() {
        return user;
    }
    public SalonServicesData getService() {
        return service;
    }
}
