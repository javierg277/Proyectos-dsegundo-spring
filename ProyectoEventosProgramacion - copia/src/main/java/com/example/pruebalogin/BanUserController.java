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

public class BanUserController implements Initializable {
    private final String bd = "eventos_programacion";
    private final String url = "jdbc:mysql://localhost:3306/";
    private final String login = "root";
    private final String password = "";

    private Connection c = null;

    @FXML
    private Button backbtt3;
    @FXML
    private Button Eventsbtt;
    @FXML
    private Button Banbtt;
    @FXML
    private TextField Nametxt;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public BanUserController() {
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

    public void ShowEvent(MouseEvent mouseEvent) throws IOException, SQLException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Admin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        AdminController controlador = fxmlLoader.getController();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }
    public void BanUser(MouseEvent mouseEvent) throws IOException, SQLException{
        Eliminar();
    }

    public void Eliminar() throws SQLException {
        PreparedStatement pstat = null;
        String castro = String.valueOf(Nametxt);
        System.out.print(castro);
        pstat = c.prepareStatement("DELETE FROM users Where UserName = ? ");
        pstat.setString(1, Nametxt.getText());
        pstat.executeUpdate();
    }

}