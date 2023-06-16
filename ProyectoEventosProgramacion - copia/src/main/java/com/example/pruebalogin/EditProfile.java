package com.example.pruebalogin;

import java.sql.*;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditProfile implements Initializable {
    private final String bd = "eventos_programacion";
    private final String url = "jdbc:mysql://localhost:3306/";
    private final String login = "root";
    private final String password = "";

    private Connection c = null;
    @FXML
    private Button backbtt4;
    @FXML
    private Button EventsUbtt;
    @FXML
    private Button Editbtt;
    @FXML
    private TextField Usernametxt;
    @FXML
    private PasswordField Passwordtxt;
    @FXML
    private TextField Agetxt;
    @FXML
    private Label Errorlbl;
    @FXML
    private Label Editlbl;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public EditProfile() {
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
    public void BackLogin4(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        LoginController controlador = fxmlLoader.getController();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();

    }

    @FXML
    public void ShowEvents(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("UserView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        UserController controlador = fxmlLoader.getController();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();

    }

    public void Editprofiles() throws IOException, SQLException {
        User a = null;
        if (Usernametxt.getText().length() > 0 && Passwordtxt.getText().length() > 0 && Agetxt.getText().length() > 0) {
            a = new User(Usernametxt.getText(), Passwordtxt.getText(), Agetxt.getText());
            Editlbl.setText("usuario Modificado correctamente");
            Errorlbl.setText("");
            Eliminar();
            insertar(a);
            close();
        } else {
            Errorlbl.setText("Complete todos los campos");
            Editlbl.setText("");
        }
    }
    public void Eliminar() throws SQLException {
        PreparedStatement pstat = null;
        String castro = String.valueOf(Usernametxt);
        System.out.print(castro);
        pstat = c.prepareStatement("DELETE FROM users Where UserName = ? ");
        pstat.setString(1, Usernametxt.getText());
        pstat.executeUpdate();
    }

    public void insertar(User a) throws SQLException {
        PreparedStatement stat = null;
        stat = c.prepareStatement("insert into users(UserName,password,Age) values(?,?,?)");
        stat.setString(1, a.getUserName());
        stat.setString(2, a.getPassword());
        stat.setString(3, a.getAge());
        stat.executeUpdate();

    }
    public void updatear(User a) throws SQLException {
        PreparedStatement stat = null;
        stat = c.prepareStatement("UPDATE users" + "SET users(UserName,password,Age) = values(?,?,?) " + "WHERE UserName = ?;");
        stat.setString(1, a.getUserName());
        stat.setString(2, a.getPassword());
        stat.setString(3, a.getAge());
        stat.setString(4, String.valueOf(Usernametxt));
        stat.executeUpdate();

    }
    public ArrayList RecogeUser() throws SQLException {
        ArrayList<String> usuarios = new ArrayList<>();

        Statement stat = c.createStatement();
        ResultSet rs = stat.executeQuery("SELECT UserName from users");
        while(rs.next()){
            String nombre = rs.getString("UserName");
            usuarios.add(nombre);
        }
        return usuarios;
    }
    @FXML
    private void UserValidates(MouseEvent mouseEvent) throws SQLException, IOException {
        ArrayList<String> nombreusuarios = RecogeUser();
        String nombreintroducido = (String) Usernametxt.getText();
        for(String nombrearraylist : nombreusuarios){
            if(nombrearraylist.equals(nombreintroducido)){
                Editprofiles();
            }
        }

    }
}
