package com.example.mykpp4;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalonServicesData {
    private static List<SalonServicesData> serviceData = new ArrayList<>();
    private Integer ID;
    private String service_name;
    private Double service_price;

    public SalonServicesData(Integer id, String service_name, Double service_price) {
        this.ID = id;
        this.service_name = service_name;
        this.service_price = service_price;
    }

    public static void initializeServices() {
        serviceData.clear();

        String sqlServices = "SELECT * FROM services";
        try (Connection connection = DBConnectionLab2.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlServices)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("service_name");
                Double price = resultSet.getDouble("price");

                serviceData.add(new SalonServicesData(id, name, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addService(Integer ID, String service_name, Double service_price) {
        String sqlClients = "INSERT INTO services (ID, service_name, price) VALUES (?, ?, ?)";
        try (Connection connection = DBConnectionLab2.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlClients)) {

            preparedStatement.setInt(1, ID);
            preparedStatement.setString(2, service_name);
            preparedStatement.setDouble(3, service_price);

            preparedStatement.executeUpdate();

            serviceData.add(new SalonServicesData(ID, service_name, service_price));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<SalonServicesData> searchServices(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return getAllServices(); // Return all services if the search term is empty
        }

        List<SalonServicesData> filteredServices = new ArrayList<>();
        String lowerCaseSearchTerm = searchTerm.toLowerCase();

        for (SalonServicesData service : serviceData) {
            if (service.getService_name().toLowerCase().contains(lowerCaseSearchTerm)) {
                filteredServices.add(service);
            }
        }

        return filteredServices; // Return the filtered list
    }

    public static void removeServiceByID(Integer id) {
        String sqlClients = "DELETE FROM services WHERE ID = ?";
        try (Connection connection = DBConnectionLab2.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlClients)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            serviceData.removeIf(service -> service.getID().equals(id));
            initializeServices();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<String> getAllServiceNames() {
        List<String> serviceNames = new ArrayList<>();
        for (SalonServicesData service : serviceData) {
            serviceNames.add(service.getService_name());
        }
        return serviceNames;
    }

    public static void updateServiceByID(Integer id, String service_name, Double service_price) {
        String sqlClients = "UPDATE services SET service_name = ?, price = ? WHERE ID = ?";
        try (Connection connection = DBConnectionLab2.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlClients)) {

            preparedStatement.setString(1, service_name);
            preparedStatement.setDouble(2, service_price);
            preparedStatement.setInt(3, id);

            preparedStatement.executeUpdate();
            initializeServices();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static SalonServicesData getServiceByID(Integer id) {
        for (SalonServicesData service : serviceData) {
            if (service.getID().equals(id)) {
                return service;
            }
        }
        return null;
    }

    public static List<SalonServicesData> getAllServices() {
        return new ArrayList<>(serviceData);
    }

    public Integer getID() {
        return ID;
    }

    public String getService_name() {
        return service_name;
    }

    public Double getService_price() {
        return service_price;
    }
}
