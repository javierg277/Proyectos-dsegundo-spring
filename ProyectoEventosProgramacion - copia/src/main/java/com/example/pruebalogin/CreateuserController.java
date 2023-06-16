package com.example.pruebalogin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.example.pruebalogin.Domain.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateuserController implements Initializable {
    private final String bd = "eventos_programacion";
    private final String url = "jdbc:mysql://localhost:3306/";
    private final String login = "root";
    private final String password = "";

    private Connection c = null;
    @FXML
    private Label Errorlbl;
    @FXML
    private TextField Usernamefield;
    @FXML
    private PasswordField Passwordfield;
    @FXML
    private TextField Agefield;
    @FXML
    private Button Donebtt;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public CreateuserController() {
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
    public void BackLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        LoginController controlador = fxmlLoader.getController();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();

    }
    public void DoneA(MouseEvent mouseEvent) throws SQLException, IOException {
        User a = null;
        if (Usernamefield.getText().length() > 0 && Passwordfield.getText().length() > 0 && Agefield.getText().length() > 0) {
            a = new User(Usernamefield.getText(), Passwordfield.getText(), Agefield.getText());
            insertar(a);
            Errorlbl.setText("");
            BackLogin();
            close();
        } else {
            Errorlbl.setText("Complete todos los campos");
            Errorlbl.setText("");
        }
    }

    public void insertar(User a) throws SQLException {

        PreparedStatement stat = null;
        stat = c.prepareStatement("insert into users(UserName,password,Age) values(?,?,?)");
        stat.setString(1, a.getUserName());
        stat.setString(2, a.getPassword());
        stat.setString(3, a.getAge());
        stat.executeUpdate();

    }
}