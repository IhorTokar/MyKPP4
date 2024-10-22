package com.example.mykpp4;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.Date;
import java.util.Optional;

public class Lab1Controller {
    @FXML
    private TextField Lab1_AprtamentNum;
    @FXML
    private Button Lab1_ClearBtn;
    @FXML
    private TextField Lab1_ID;
    @FXML
    private TextField Lab1_Seach;
    @FXML
    private Button Lab1_addBtn;
    @FXML
    private TableView<ApartamentOSBB_Data> Lab1_tableView;
    @FXML
    private TableColumn<ApartamentOSBB_Data, String> Lab1_col_AprtamentNum;
    @FXML
    private TableColumn<ApartamentOSBB_Data, String> Lab1_col_ID;
    @FXML
    private TableColumn<ApartamentOSBB_Data, String> Lab1_col_Join_Date;
    @FXML
    private TableColumn<ApartamentOSBB_Data, String> Lab1_col_Payment_amouth;
    @FXML
    private TableColumn<ApartamentOSBB_Data, String> Lab1_col_email;
    @FXML
    private TableColumn<ApartamentOSBB_Data, String> Lab1_col_fhoneNum;
    @FXML
    private TableColumn<ApartamentOSBB_Data, String> Lab1_col_sur_name;
    @FXML
    private TableColumn<ApartamentOSBB_Data, String> Lab1_col_user_name;
    @FXML
    private Button Lab1_deleteBtn;
    @FXML
    private TextField Lab1_email;
    @FXML
    private AnchorPane Lab1_form;
    @FXML
    private DatePicker Lab1_join_Date;
    @FXML
    private TextField Lab1_payment_amouth;
    @FXML
    private TextField Lab1_phoneNum;
    @FXML
    private TextField Lab1_sur_name;
    @FXML
    private Button Lab1_updateBtn;
    @FXML
    private TextField Lab1_user_name;
    @FXML
    private Button close;
    @FXML
    private AnchorPane main_form;
    @FXML
    private Button minimize;
    @FXML
    private Button backBtn;

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private ObservableList<ApartamentOSBB_Data> Lab1_list;

    public ObservableList<ApartamentOSBB_Data> Lab1Data() {
        ObservableList<ApartamentOSBB_Data> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Owners JOIN Payments ON Owners.OwnerID = Payments.OwnerID";

        connection = DBConnection.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            ApartamentOSBB_Data apartamentOSBB_D;
            while (resultSet.next()) {
                apartamentOSBB_D = new ApartamentOSBB_Data(
                        resultSet.getInt("OwnerID"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Email"),
                        resultSet.getString("Phone"),
                        resultSet.getString("ApartmentNumber"),
                        resultSet.getDate("JoinDate"),
                        resultSet.getDouble("Amount"));
                listData.add(apartamentOSBB_D);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public void Lab1_Add() {
        String ownerSql = "INSERT INTO Owners (FirstName, LastName, Email, Phone, ApartmentNumber, JoinDate) VALUES (?, ?, ?, ?, ?, ?)";
        String paymentSql = "INSERT INTO Payments (OwnerID, Amount, PaymentDate) VALUES (?, ?, ?)";

        connection = DBConnection.getConnection();
        try {
            if (Lab1_user_name.getText().isEmpty() ||
                    Lab1_sur_name.getText().isEmpty() ||
                    Lab1_email.getText().isEmpty() ||
                    Lab1_phoneNum.getText().isEmpty() ||
                    Lab1_AprtamentNum.getText().isEmpty() ||
                    Lab1_join_Date.getValue() == null ||
                    Lab1_payment_amouth.getText().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Будь ласка, заповніть усі поля перед додаванням.");
                alert.showAndWait();
                return;
            }

            statement = connection.createStatement();
            String checkSql = "SELECT OwnerID FROM Owners WHERE OwnerID = ?";
            preparedStatement = connection.prepareStatement(checkSql);
            preparedStatement.setString(1, Lab1_ID.getText());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Даний ID вже присутній.");
                alert.showAndWait();
                return;
            }

            preparedStatement = connection.prepareStatement(ownerSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, Lab1_user_name.getText());
            preparedStatement.setString(2, Lab1_sur_name.getText());
            preparedStatement.setString(3, Lab1_email.getText());
            preparedStatement.setString(4, Lab1_phoneNum.getText());
            preparedStatement.setString(5, Lab1_AprtamentNum.getText());
            preparedStatement.setDate(6, java.sql.Date.valueOf(Lab1_join_Date.getValue()));
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int ownerId = generatedKeys.getInt(1);
                    preparedStatement = connection.prepareStatement(paymentSql);
                    preparedStatement.setInt(1, ownerId);
                    preparedStatement.setDouble(2, Double.parseDouble(Lab1_payment_amouth.getText()));
                    preparedStatement.setDate(3, java.sql.Date.valueOf(LocalDate.now())); // Set current date as PaymentDate
                    preparedStatement.executeUpdate();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Інформація");
                    alert.setHeaderText(null);
                    alert.setContentText("Запис був успішно добавлений.");
                    alert.showAndWait();
                    clearFields();
                }
            }
            Lab1_ShowListData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Lab1_delete(){
        String deletePaymentsSql = "DELETE FROM Payments WHERE OwnerID ="+Lab1_ID.getText();
        String deleteOwnerSql = "DELETE FROM Owners WHERE OwnerID ="+Lab1_ID.getText();

        connection = DBConnection.getConnection();

        try{
            if (Lab1_user_name.getText().isEmpty() ||
                    Lab1_sur_name.getText().isEmpty() ||
                    Lab1_email.getText().isEmpty() ||
                    Lab1_phoneNum.getText().isEmpty() ||
                    Lab1_AprtamentNum.getText().isEmpty() ||
                    Lab1_join_Date.getValue() == null ||
                    Lab1_payment_amouth.getText().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Будь ласка, заповніть усі поля перед додаванням.");
                alert.showAndWait();
                return;
            }
            else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Підтвердження");
                alert.setHeaderText(null);
                alert.setContentText("Ви видалити цей запис?");
                Optional<ButtonType> optional = alert.showAndWait();
                if(optional.get().equals(ButtonType.OK)){
                    statement = connection.createStatement();
                    statement.executeUpdate(deletePaymentsSql);
                    statement.executeUpdate(deleteOwnerSql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Інформація");
                    alert.setHeaderText(null);
                    alert.setContentText("Запис був успішно видалений.");
                    alert.showAndWait();
                }
                Lab1_ShowListData();
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void Lab1_update() {
        String updateOwnerSql = "UPDATE Owners SET FirstName = ?, LastName = ?, Email = ?, Phone = ?, ApartmentNumber = ?, JoinDate = ? WHERE OwnerID = ?";
        String updatePaymentSql = "UPDATE Payments SET Amount = ? WHERE OwnerID = ?";

        connection = DBConnection.getConnection();

        try {
            // Check if all fields are filled before proceeding
            if (Lab1_ID.getText().isEmpty() ||
                    Lab1_user_name.getText().isEmpty() ||
                    Lab1_sur_name.getText().isEmpty() ||
                    Lab1_email.getText().isEmpty() ||
                    Lab1_phoneNum.getText().isEmpty() ||
                    Lab1_AprtamentNum.getText().isEmpty() ||
                    Lab1_join_Date.getValue() == null ||
                    Lab1_payment_amouth.getText().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Помилка");
                alert.setHeaderText(null);
                alert.setContentText("Будь ласка, заповніть усі поля перед оновленням.");
                alert.showAndWait();
                return;
            }

            // Confirmation alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Підтвердження");
            alert.setHeaderText(null);
            alert.setContentText("Ви впевнені, що хочете оновити цей запис?");
            Optional<ButtonType> optional = alert.showAndWait();

            if (optional.isPresent() && optional.get().equals(ButtonType.OK)) {
                // Update Owners table
                preparedStatement = connection.prepareStatement(updateOwnerSql);
                preparedStatement.setString(1, Lab1_user_name.getText());
                preparedStatement.setString(2, Lab1_sur_name.getText());
                preparedStatement.setString(3, Lab1_email.getText());
                preparedStatement.setString(4, Lab1_phoneNum.getText());
                preparedStatement.setString(5, Lab1_AprtamentNum.getText());
                preparedStatement.setDate(6, java.sql.Date.valueOf(Lab1_join_Date.getValue()));
                preparedStatement.setInt(7, Integer.parseInt(Lab1_ID.getText()));
                preparedStatement.executeUpdate();

                // Update Payments table
                preparedStatement = connection.prepareStatement(updatePaymentSql);
                preparedStatement.setDouble(1, Double.parseDouble(Lab1_payment_amouth.getText()));
                preparedStatement.setInt(2, Integer.parseInt(Lab1_ID.getText()));
                preparedStatement.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Інформація");
                alert.setHeaderText(null);
                alert.setContentText("Запис був успішно оновлений.");
                alert.showAndWait();

                clearFields();
                Lab1_ShowListData();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void Lab1_search() {
        FilteredList<ApartamentOSBB_Data> filter = new FilteredList<>(Lab1_list, e -> true);

        Lab1_Seach.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(oldValue)) {
                System.out.println("Old Value: " + oldValue + ", New Value: " + newValue);
                System.out.println("Searching for: " + newValue);

                filter.setPredicate(apartament -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String searchKey = newValue.toLowerCase();
                    return apartament.getID().toString().toLowerCase().contains(searchKey) ||
                            apartament.getFirstName().toLowerCase().contains(searchKey) ||
                            apartament.getLastName().toLowerCase().contains(searchKey) ||
                            apartament.getEmail().toLowerCase().contains(searchKey) ||
                            apartament.getPhoneNum().toLowerCase().contains(searchKey) ||
                            apartament.getApartamentNum().toLowerCase().contains(searchKey) ||
                            apartament.getJoinDate().toString().toLowerCase().contains(searchKey) ||
                            String.valueOf(apartament.getPaymentAmouth()).toLowerCase().contains(searchKey);
                });

            }
            Lab1_tableView.setItems(filter);
        });
    }


    public void Lab1_Select() {
        ApartamentOSBB_Data apartamentOSBB_use = Lab1_tableView.getSelectionModel().getSelectedItem();
        int num = Lab1_tableView.getSelectionModel().getSelectedIndex();


        Lab1_ID.setText(String.valueOf(apartamentOSBB_use.getID()));
        Lab1_user_name.setText(String.valueOf(apartamentOSBB_use.getFirstName()));
        Lab1_sur_name.setText(String.valueOf(apartamentOSBB_use.getLastName()));
        Lab1_email.setText(String.valueOf(apartamentOSBB_use.getEmail()));
        Lab1_phoneNum.setText(String.valueOf(apartamentOSBB_use.getPhoneNum()));
        Lab1_AprtamentNum.setText(String.valueOf(apartamentOSBB_use.getApartamentNum()));
        java.util.Date joinDateUtil = apartamentOSBB_use.getJoinDate();
        java.sql.Date joinDateSql = new java.sql.Date(joinDateUtil.getTime());
        Lab1_join_Date.setValue(joinDateSql.toLocalDate());
        Lab1_payment_amouth.setText(String.valueOf(apartamentOSBB_use.getPaymentAmouth()));
    }

    public void clearFields() {
        Lab1_ID.clear();
        Lab1_user_name.clear();
        Lab1_sur_name.clear();
        Lab1_email.clear();
        Lab1_phoneNum.clear();
        Lab1_AprtamentNum.clear();
        Lab1_join_Date.setValue(null);
        Lab1_payment_amouth.clear();
    }

    public void Lab1_ShowListData() {
        Lab1_list = Lab1Data();

        Lab1_col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Lab1_col_user_name.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        Lab1_col_sur_name.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        Lab1_col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        Lab1_col_fhoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        Lab1_col_AprtamentNum.setCellValueFactory(new PropertyValueFactory<>("apartamentNum"));
        Lab1_col_Join_Date.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
        Lab1_col_Payment_amouth.setCellValueFactory(new PropertyValueFactory<>("paymentAmouth"));

        Lab1_tableView.setItems(Lab1_list);
    }

    public void close(){
        System.exit(0);
    }

    public void minimize(){
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }
    public double x = 0;
    public double y = 0;
    public void backBtnevent() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Підтвердження");
        alert.setHeaderText(null);
        alert.setContentText("Ви хочете вийти в головне меню?");
        Optional<ButtonType> optional = alert.showAndWait();

        try{
            if(optional.get().equals(ButtonType.OK)){
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



        }
        catch (IOException e){
            System.out.println(e);
        }
    }
    @FXML
    public void initialize() {
        Lab1_ShowListData();
        Lab1_search();
    }
}
