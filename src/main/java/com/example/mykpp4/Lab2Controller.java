package com.example.mykpp4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.SplittableRandom;

public class Lab2Controller {
    @FXML
    private TableColumn<SalonUsersData, String> Lab2_client_ID_table;
    @FXML
    private TableView<SalonUsersData> Lab2_client_TableWiew;
    @FXML
    private ChoiceBox<SalonUsersData> Lab2_client_choiseBox;
    @FXML
    private TableColumn<SalonUsersData, String> Lab2_client_gmail_table;
    @FXML
    private TableColumn<SalonUsersData, String> Lab2_client_name_table;
    @FXML
    private TableColumn<SalonUsersData, String> Lab2_client_phoneNum_table;
    @FXML
    private TableColumn<SalonUsersData, String> Lab2_client_surName_table;
    @FXML
    private TableColumn<SalonUsersData, String> Lab2_client_thirdName_table;
    @FXML
    private TableColumn<SalonEntriesData, Integer> Lab2_entries_ID_table;
    @FXML
    private TableView<SalonEntriesData> Lab2_entries_TableWiew;
    @FXML
    private TableColumn<SalonEntriesData, String> Lab2_entries_User_table;
    @FXML
    private TableColumn<SalonEntriesData, Date> Lab2_entries_date_table;
    @FXML
    private TableColumn<SalonEntriesData, Double> Lab2_entries_fullPrice_table;
    @FXML
    private TableColumn<SalonEntriesData, String> Lab2_entries_services_table;
    @FXML
    private TableColumn<SalonServicesData, Integer> Lab2_service_ID_table;
    @FXML
    private TableView<SalonServicesData> Lab2_service_TableWiew;
    @FXML
    private TableColumn<SalonServicesData, Double> Lab2_service_price_table;
    @FXML
    private TableColumn<SalonServicesData, String> Lab2_service_table;
    @FXML
    private ChoiceBox<SalonServicesData> Lab2_servise_choiseBox;
    @FXML
    private TextField Lab2_Gmail_textField;
    @FXML
    private TextField Lab2_Name_textField;
    @FXML
    private TextField Lab2_PhoneNum_textField;
    @FXML
    private TextField Lab2_Sur_Name_textField;
    @FXML
    private TextField Lab2_Third_name_textField;
    @FXML
    private TextField Lab2_client_ID_textField;
    @FXML
    private TextField Lab2_entries_ID_textField;
    @FXML
    private Label Lab2_gmail_textDisplay;
    @FXML
    private Label Lab2_payment_sum;
    @FXML
    private Label Lab2_phone_textDisplay;
    @FXML
    private TextField Lab2_service_ID_textField;

    @FXML
    private TextField enriesSearch;
    @FXML
    private TextField serviceSearch;
    @FXML
    private TextField client_Search;
    @FXML
    private TextField Lab2_service_name_textField;
    @FXML
    private TextField Lab2_service_price_textField1;
    @FXML
    private TabPane Lab2_tablePane;
    @FXML
    private DatePicker Lab2_entries_date;
    @FXML
    private Button add_clientBtn;
    @FXML
    private Button add_entriesBtn;
    @FXML
    private Button add_serviceBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button clear_clientBtn;
    @FXML
    private Button clear_entriesBtn;
    @FXML
    private Button clear_serviceBtn;
    @FXML
    private Tab client_tab;
    @FXML
    private Button close;
    @FXML
    private Button delete_clientBtn;
    @FXML
    private Button delete_entriesBtn;
    @FXML
    private Button delete_serviceBtn;
    @FXML
    private Tab entries_tab;
    @FXML
    private AnchorPane main_form;
    @FXML
    private Button minimize;
    @FXML
    private Tab service_tab;
    @FXML
    private Button update_clientBtn;
    @FXML
    private Button update_entriesBtn;
    @FXML
    private Button update_serviceBtn;
    //////////////////////////////////////////////////////////////////////

    private ObservableList<SalonUsersData> usersList;
    private ObservableList<SalonServicesData> servicesList;
    private ObservableList<SalonEntriesData> entriesList;

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    ////////////////////////////////////////////
    public void dataInitialize() {
        SalonUsersData.initializeUsers();
        SalonServicesData.initializeServices();
        SalonEntriesData.initializeEntries();
    }
    public void loadClientData() {
        usersList = FXCollections.observableArrayList(SalonUsersData.getAllUsers());
        Lab2_client_ID_table.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Lab2_client_name_table.setCellValueFactory(new PropertyValueFactory<>("user_name"));
        Lab2_client_surName_table.setCellValueFactory(new PropertyValueFactory<>("user_surName"));
        Lab2_client_thirdName_table.setCellValueFactory(new PropertyValueFactory<>("user_thirdName"));
        Lab2_client_gmail_table.setCellValueFactory(new PropertyValueFactory<>("gmail"));
        Lab2_client_phoneNum_table.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        Lab2_client_TableWiew.setItems(usersList);
    }
    public void loadServiceData() {
        servicesList = FXCollections.observableArrayList(SalonServicesData.getAllServices());
        Lab2_service_ID_table.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Lab2_service_table.setCellValueFactory(new PropertyValueFactory<>("service_name"));
        Lab2_service_price_table.setCellValueFactory(new PropertyValueFactory<>("service_price"));
        Lab2_service_TableWiew.setItems(servicesList);
    }
    public void loadEntriesData() {
        entriesList = FXCollections.observableArrayList(SalonEntriesData.getAllEntries());

        Lab2_entries_ID_table.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Lab2_entries_User_table.setCellValueFactory(new PropertyValueFactory<>("userFullName"));
        Lab2_entries_date_table.setCellValueFactory(new PropertyValueFactory<>("entriesDate"));
        Lab2_entries_services_table.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        Lab2_entries_fullPrice_table.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));

        Lab2_entries_TableWiew.setItems(entriesList);
    }
    public void clientSearch() {
        client_Search.textProperty().addListener((observable, oldValue, newValue) -> {
            List<SalonUsersData> filteredUsers = SalonUsersData.searchUsers(newValue);
            Lab2_client_TableWiew.setItems(FXCollections.observableArrayList(filteredUsers));
        });
    }
    public void serviceSearch() {
        serviceSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            List<SalonServicesData> filteredServices = SalonServicesData.searchServices(newValue);
            Lab2_service_TableWiew.setItems(FXCollections.observableArrayList(filteredServices));
        });
    }
    public void entriesSearch() {
        enriesSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            List<SalonEntriesData> filteredEntries = SalonEntriesData.searchEntries(newValue);
            Lab2_entries_TableWiew.setItems(FXCollections.observableArrayList(filteredEntries));
        });
    }
    public void clearClientFields(){
        Lab2_client_ID_textField.setText("");
        Lab2_Name_textField.setText("");
        Lab2_Sur_Name_textField.setText("");
        Lab2_Third_name_textField.setText("");
        Lab2_Gmail_textField.setText("");
        Lab2_PhoneNum_textField.setText("");
    }
    public void clearServiceFields(){
        Lab2_service_ID_textField.setText("");
        Lab2_service_name_textField.setText("");
        Lab2_service_price_textField1.setText("");
    }
    public void clearEntriesFields(){
        Lab2_entries_ID_textField.setText("");
        Lab2_gmail_textDisplay.setText("");
        Lab2_phone_textDisplay.setText("");
        Lab2_payment_sum.setText("");
    }
    public void clientChoise(){
        SalonUsersData salonUser = Lab2_client_TableWiew.getSelectionModel().getSelectedItem();
        Integer userIndex = Lab2_client_TableWiew.getSelectionModel().getSelectedIndex();

        Lab2_client_ID_textField.setText(String.valueOf(salonUser.getID()));
        Lab2_Name_textField.setText(String.valueOf(salonUser.getUser_name()));
        Lab2_Sur_Name_textField.setText(String.valueOf(salonUser.getUser_surName()));
        Lab2_Third_name_textField.setText(String.valueOf(salonUser.getUser_thirdName()));
        Lab2_Gmail_textField.setText(String.valueOf(salonUser.getGmail()));
        Lab2_PhoneNum_textField.setText(String.valueOf(salonUser.getPhoneNum()));
    }
    public void serviceChoise(){
        SalonServicesData salonService = Lab2_service_TableWiew.getSelectionModel().getSelectedItem();
        Integer serviceIndex = Lab2_service_TableWiew.getSelectionModel().getSelectedIndex();


        Lab2_service_ID_textField.setText(String.valueOf(salonService.getID()));
        Lab2_service_name_textField.setText(String.valueOf(salonService.getService_name()));
        Lab2_service_price_textField1.setText(String.valueOf(salonService.getService_price()));
    }
    public void entriesChoise(){
        SalonEntriesData salonEntrie = Lab2_entries_TableWiew.getSelectionModel().getSelectedItem();
        Integer entriesIndex = Lab2_entries_TableWiew.getSelectionModel().getSelectedIndex();

        Lab2_entries_ID_textField.setText(String.valueOf(salonEntrie.getID()));
        Lab2_servise_choiseBox.setValue(salonEntrie.getService());
        Lab2_client_choiseBox.setValue(salonEntrie.getUser());
        Lab2_gmail_textDisplay.setText(String.valueOf(salonEntrie.getUser().getGmail()));
        Lab2_phone_textDisplay.setText(String.valueOf(salonEntrie.getUser().getPhoneNum()));
        Lab2_payment_sum.setText(String.valueOf(salonEntrie.getService().getService_price()));
        java.util.Date joinDateUtil = salonEntrie.getEntriesDate();
        java.sql.Date joinDateSql = new java.sql.Date(joinDateUtil.getTime());
        Lab2_entries_date.setValue(joinDateSql.toLocalDate());
    }
    public void addClient(){
        if (Lab2_client_ID_textField.getText().isEmpty() ||
                Lab2_Name_textField.getText().isEmpty() ||
                Lab2_Sur_Name_textField.getText().isEmpty() ||
                Lab2_Third_name_textField.getText().isEmpty() ||
                Lab2_Gmail_textField.getText().isEmpty() ||
                Lab2_PhoneNum_textField.getText().isEmpty()
        ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Будь ласка, заповніть усі поля перед додаванням.");
            alert.showAndWait();
            return;
        }
        int clientId = Integer.parseInt(Lab2_client_ID_textField.getText());
        for (SalonUsersData user : usersList) {
            if (user.getID() == clientId) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Даний ID вже присутній.");
                alert.showAndWait();
                return;
            }
        }
        int clientID =  Integer.parseInt(Lab2_client_ID_textField.getText());
        String userName = Lab2_Name_textField.getText();
        String usersurName = Lab2_Sur_Name_textField.getText();
        String userThirdName = Lab2_Third_name_textField.getText();
        String userGmail = Lab2_Gmail_textField.getText();
        String userPhoneNum = Lab2_PhoneNum_textField.getText();

        SalonUsersData.addUser(clientID, userName, usersurName, userThirdName, userGmail, userPhoneNum);
        loadClientData();
        initializeChoiceBoxes();

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Інформація");
        alert.setHeaderText(null);
        alert.setContentText("Запис успішно добавлений");
        alert.showAndWait();
    }
    public void addService(){
        if (Lab2_service_ID_textField.getText().isEmpty() || Lab2_service_price_textField1.getText().isEmpty() ||
                Lab2_service_name_textField.getText().isEmpty()
        ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Будь ласка, заповніть усі поля перед додаванням.");
            alert.showAndWait();
            return;
        }
        int serviceID = Integer.parseInt(Lab2_service_ID_textField.getText());
        for (SalonServicesData service : servicesList) {
            if (service.getID() == serviceID) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Даний ID вже присутній.");
                alert.showAndWait();
                return;
            }
        }

        serviceID =  Integer.parseInt(Lab2_service_ID_textField.getText());
        String serviceName = Lab2_service_name_textField.getText();
        Double servicePrice = Double.parseDouble(Lab2_service_price_textField1.getText());

        SalonServicesData.addService(serviceID, serviceName, servicePrice);
        loadServiceData();
        initializeChoiceBoxes();
    }
    public void addEntries(){
        if (Lab2_client_choiseBox.getValue() == null ||
                Lab2_servise_choiseBox.getValue() == null ||
                Lab2_entries_date.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Будь ласка, заповніть усі поля перед додаванням.");
            alert.showAndWait();
            return;
        }
        int entriesId = Integer.parseInt(Lab2_entries_ID_textField.getText());
        for (SalonEntriesData user : entriesList) {
            if (user.getID() == entriesId) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Даний ID вже присутній.");
                alert.showAndWait();
                return;
            }
        }
        int entryID =  Integer.parseInt(Lab2_entries_ID_textField.getText());
        LocalDate localDate = Lab2_entries_date.getValue();
        java.sql.Date entryDate = java.sql.Date.valueOf(localDate);

        SalonUsersData userData = Lab2_client_choiseBox.getValue();
        SalonServicesData servicesData = Lab2_servise_choiseBox.getValue();

        SalonEntriesData.addEntry(entryID, entryDate,userData, servicesData);
        loadEntriesData();
    }
    public void deleteClient(){
        if (Lab2_client_ID_textField.getText().isEmpty() ||
                Lab2_Name_textField.getText().isEmpty() ||
                Lab2_Sur_Name_textField.getText().isEmpty() ||
                Lab2_Third_name_textField.getText().isEmpty() ||
                Lab2_Gmail_textField.getText().isEmpty() ||
                Lab2_PhoneNum_textField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Будь ласка, заповніть усі поля перед видаленням.");
            alert.showAndWait();
            return;
        }
        else{
            Boolean isValid = false;
            Integer userId = Integer.parseInt(Lab2_client_ID_textField.getText());
            for(SalonUsersData user : usersList){
                if(user.getID() == userId){
                    isValid = true;
                }
            }
            Boolean isUsedNow = false;
            for(SalonEntriesData entrie : entriesList){
                if(userId == entrie.getUser().getID()){
                    isUsedNow = true;
                }
            }
            if(isValid && !isUsedNow){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Підтвердження");
            alert.setHeaderText(null);
            alert.setContentText("Ви видалити цей запис?");
            Optional<ButtonType> optional = alert.showAndWait();
            if(optional.get().equals(ButtonType.OK)){
                SalonUsersData.removeUserByID(Integer.parseInt(Lab2_client_ID_textField.getText()));
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Інформація");
                alert.setHeaderText(null);
                alert.setContentText("Запис був успішно видалений.");
                alert.showAndWait();
                loadClientData();
                initializeChoiceBoxes();
            }

            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Введено невірний ID користувача який відсутній в таблиці або він використовується і в таблиці записей.");
                alert.showAndWait();
                return;
            }
        }
    }
    public void deleteService() {
        if (Lab2_service_ID_textField.getText().isEmpty() ||
                Lab2_service_name_textField.getText().isEmpty() ||
        Lab2_service_price_textField1.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Будь ласка, заповніть усі поля перед видаленням.");
            alert.showAndWait();
            return;
        } else {
            Boolean isValid = false;
            Integer serviceId = Integer.parseInt(Lab2_service_ID_textField.getText());
            for (SalonServicesData service : servicesList) {
                if (service.getID() == serviceId) {
                    isValid = true;
                }
            }
            Boolean isUsedNow = false;
            for(SalonEntriesData entrie : entriesList){
                if(serviceId == entrie.getService().getID()){
                    isUsedNow = true;
                }
            }
            if (isValid && !isUsedNow) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Підтвердження");
                alert.setHeaderText(null);
                alert.setContentText("Ви бажаєте видалити цей сервіс?");
                Optional<ButtonType> optional = alert.showAndWait();
                if (optional.get().equals(ButtonType.OK)) {
                    SalonServicesData.removeServiceByID(serviceId);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Інформація");
                    alert.setHeaderText(null);
                    alert.setContentText("Сервіс був успішно видалений.");
                    alert.showAndWait();
                    loadServiceData();
                    initializeChoiceBoxes();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Введено невірний ID сервісу який відсутній в таблиці або він використовується і в таблиці записей.");
                alert.showAndWait();
                return;
            }
        }
    }
    public void deleteEntry() {
        if (Lab2_entries_ID_textField.getText().isEmpty()||
                Lab2_client_choiseBox.getValue() == null ||
                Lab2_servise_choiseBox.getValue() == null ||
                Lab2_entries_date.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Будь ласка, заповніть усі поля перед видаленням.");
            alert.showAndWait();
            return;
        } else {
            Boolean isValid = false;
            Integer entryId = Integer.parseInt(Lab2_entries_ID_textField.getText());
            for (SalonEntriesData entry : entriesList) {
                if (entry.getID() == entryId) {
                    isValid = true;
                }
            }
            if (isValid) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Підтвердження");
                alert.setHeaderText(null);
                alert.setContentText("Ви бажаєте видалити цей запис?");
                Optional<ButtonType> optional = alert.showAndWait();
                if (optional.get().equals(ButtonType.OK)) {
                    SalonEntriesData.removeEntryByID(entryId);
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Інформація");
                    alert.setHeaderText(null);
                    alert.setContentText("Запис був успішно видалений.");
                    alert.showAndWait();
                    loadEntriesData();
                    initializeChoiceBoxes();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Введено невірний ID запису, якого немає в таблиці.");
                alert.showAndWait();
                return;
            }
        }
    }
    public void EditClient(){
        if (Lab2_client_ID_textField.getText().isEmpty() ||
                Lab2_Name_textField.getText().isEmpty() ||
                Lab2_Sur_Name_textField.getText().isEmpty() ||
                Lab2_Third_name_textField.getText().isEmpty() ||
                Lab2_Gmail_textField.getText().isEmpty() ||
                Lab2_PhoneNum_textField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Будь ласка, заповніть усі поля перед видаленням.");
            alert.showAndWait();
            return;
        }
        else{
            Boolean isValid = false;
            Integer userId = Integer.parseInt(Lab2_client_ID_textField.getText());
            for(SalonUsersData user : usersList){
                if(user.getID() == userId){
                    isValid = true;
                }
            }
            if(isValid){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Підтвердження");
                alert.setHeaderText(null);
                alert.setContentText("Ви змінити цей запис?");
                Optional<ButtonType> optional = alert.showAndWait();
                if(optional.get().equals(ButtonType.OK)){
                    int clientID =  Integer.parseInt(Lab2_client_ID_textField.getText());
                    String userName = Lab2_Name_textField.getText();
                    String usersurName = Lab2_Sur_Name_textField.getText();
                    String userThirdName = Lab2_Third_name_textField.getText();
                    String userGmail = Lab2_Gmail_textField.getText();
                    String userPhoneNum = Lab2_PhoneNum_textField.getText();

                    SalonUsersData.updateUserByID(clientID, userName, usersurName, userThirdName, userGmail, userPhoneNum);
                    loadClientData();
                    initializeChoiceBoxes();
                    loadEntriesData();
                }
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Інформація");
                alert.setHeaderText(null);
                alert.setContentText("Запис був успішно змінений.");
                alert.showAndWait();
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Введено невірний ID ористувача який відсутній в таблиці.");
                alert.showAndWait();
                return;
            }
        }
    }
    public void EditService(){
        if (Lab2_service_ID_textField.getText().isEmpty() ||
                Lab2_service_name_textField.getText().isEmpty() ||
                Lab2_service_price_textField1.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Будь ласка, заповніть усі поля перед видаленням.");
            alert.showAndWait();
            return;
        } else {
            Boolean isValid = false;
            Integer serviceId = Integer.parseInt(Lab2_service_ID_textField.getText());
            for (SalonServicesData service : servicesList) {
                if (service.getID() == serviceId) {
                    isValid = true;
                }
            }
            if (isValid) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Підтвердження");
                alert.setHeaderText(null);
                alert.setContentText("Ви бажаєте змінений цей сервіс?");
                Optional<ButtonType> optional = alert.showAndWait();
                if (optional.get().equals(ButtonType.OK)) {
                    int serviceID =  Integer.parseInt(Lab2_service_ID_textField.getText());
                    String serviceName = Lab2_service_name_textField.getText();
                    Double servicePrice = Double.parseDouble(Lab2_service_price_textField1.getText());

                    SalonServicesData.updateServiceByID(serviceID, serviceName, servicePrice);
                    loadServiceData();
                    initializeChoiceBoxes();
                    loadEntriesData();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Інформація");
                    alert.setHeaderText(null);
                    alert.setContentText("Сервіс був успішно змінений.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Введено невірний ID сервісу, якого немає в таблиці.");
                alert.showAndWait();
                return;
            }
        }
    }
    public void EditEntries(){
        if (Lab2_entries_ID_textField.getText().isEmpty()||
                Lab2_client_choiseBox.getValue() == null ||
                Lab2_servise_choiseBox.getValue() == null ||
                Lab2_entries_date.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Помилка");
            alert.setHeaderText(null);
            alert.setContentText("Будь ласка, заповніть усі поля перед видаленням.");
            alert.showAndWait();
            return;
        } else {
            Boolean isValid = false;
            Integer entryId = Integer.parseInt(Lab2_entries_ID_textField.getText());
            for (SalonEntriesData entry : entriesList) {
                if (entry.getID() == entryId) {
                    isValid = true;
                }
            }
            if (isValid) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Підтвердження");
                alert.setHeaderText(null);
                alert.setContentText("Ви бажаєте змінити цей запис?");
                Optional<ButtonType> optional = alert.showAndWait();
                if (optional.get().equals(ButtonType.OK)) {
                    int entryID =  Integer.parseInt(Lab2_entries_ID_textField.getText());
                    LocalDate localDate = Lab2_entries_date.getValue();
                    java.sql.Date entryDate = java.sql.Date.valueOf(localDate);

                    SalonUsersData userData = Lab2_client_choiseBox.getValue();
                    SalonServicesData servicesData = Lab2_servise_choiseBox.getValue();

                    SalonEntriesData.updateEntryByID(entryID, entryDate,userData, servicesData);
                    loadEntriesData();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Інформація");
                    alert.setHeaderText(null);
                    alert.setContentText("Запис був успішно видалений.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Введено невірний ID запису, якого немає в таблиці.");
                alert.showAndWait();
                return;
            }
        }
    }

    private void initializeChoiceBoxes() {
        Lab2_servise_choiseBox.setItems(FXCollections.observableArrayList(SalonServicesData.getAllServices()));
        Lab2_client_choiseBox.setItems(FXCollections.observableArrayList(SalonUsersData.getAllUsers()));

        Lab2_servise_choiseBox.setConverter(new StringConverter<SalonServicesData>() {
            @Override
            public String toString(SalonServicesData service) {
                return service != null ? service.getService_name() : "";
            }
            @Override
            public SalonServicesData fromString(String string) {
                return Lab2_servise_choiseBox.getItems().stream()
                        .filter(service -> service.getService_name().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        Lab2_client_choiseBox.setConverter(new StringConverter<SalonUsersData>() {
            @Override
            public String toString(SalonUsersData user) {
                return user != null ? user.getUser_surName() + "." + user.getUser_name().charAt(0) + "." + user.getUser_thirdName().charAt(0) + "." : "";
            }

            @Override
            public SalonUsersData fromString(String string) {
                return Lab2_client_choiseBox.getItems().stream()
                        .filter(user -> (user.getUser_surName() + "." + user.getUser_name().charAt(0) + "." + user.getUser_thirdName().charAt(0) + ".").equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

////////////////////////////////////////////////
    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public double x = 0;
    public double y = 0;

    public void backBtnEvent() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Підтвердження");
        alert.setHeaderText(null);
        alert.setContentText("Ви хочете вийти в головне меню?");
        Optional<ButtonType> optional = alert.showAndWait();
        try {
            if (optional.get().equals(ButtonType.OK)) {
                backBtn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("KPP4_GUI.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });
                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                    stage.setOpacity(.8);
                });
                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @FXML
    public void initialize() {
        dataInitialize();
        loadClientData();
        loadServiceData();
        loadEntriesData();
        initializeChoiceBoxes();
    }
}
