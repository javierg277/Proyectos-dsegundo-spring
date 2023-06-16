package com.example.pruebalogin;

import java.sql.*;

import com.example.pruebalogin.Domain.Event;
import com.example.pruebalogin.Domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;

public class AdminController implements Initializable {
    private final String bd = "eventos_programacion";
    private final String url = "jdbc:mysql://localhost:3306/";
    private final String login = "root";
    private final String password = "";

    private Connection c = null;

    @FXML
    private Button Createbtt;
    @FXML
    private Button backbtt3;
    @FXML
    private Button BanUsersBtt;
    @FXML
    private TextField Nametxt;
    @FXML
    private TextField Datetxt;
    @FXML
    private TextField Descriptiontxt;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public AdminController() {
        conectar();
    }
    public void conectar() {
        try {
            c = DriverManager.getConnection(url + bd, login, password);
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public void close() {
        try {
            if (c != null) {
                c.close();
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    @FXML
    public void BackLogin1(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        LoginController controlador = fxmlLoader.getController();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();

    }
    @FXML
    public void goBanUsers(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("BanUsers.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        BanUserController controlador = fxmlLoader.getController();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();

    }

    public void CreateEvent(MouseEvent mouseEvent) throws IOException, SQLException{

            if (Nametxt.getText().length() > 0 && Datetxt.getText().length() > 0 && Descriptiontxt.getText().length() > 0) {
                Event b = new Event(Nametxt.getText(), Datetxt.getText(), Descriptiontxt.getText());
                insertar(b);
            } else {
                Nametxt.setText("puta");
            }
    }
        public void insertar(Event b) throws SQLException {

            PreparedStatement stat = null;
            stat = c.prepareStatement("insert into events(Event_name,Event_Date,Event_Description) values(?,?,?)");
            stat.setString(1, b.getEvent_name());
            stat.setString(2, b.getEvent_Date());
            stat.setString(3, b.getEvent_Description());
            stat.executeUpdate();

        }



}
