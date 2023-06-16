package com.example.pruebalogin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;



public class LoginController implements Initializable {

    private final String bd = "eventos_programacion";
    private final String url = "jdbc:mysql://localhost:3306/";
    private final String login = "root";
    private final String password = "";

    private Connection c = null;

    @FXML
    private Button Loginbtt;
    @FXML
    private Button SignUpbtt;
    @FXML
    private TextField UserNametxt;
    @FXML
    private PasswordField Passwordtxt;
    @FXML
    private ImageView Logo;

    @FXML
    private Label Errorlbl;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public LoginController() {
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
    private void UserValidates(MouseEvent mouseEvent) throws SQLException, IOException {
        CheckAdmin();
        ArrayList<String> nombreusuarios = RecogeUser();
        String nombreintroducido = (String) UserNametxt.getText();
        for(String nombrearraylist : nombreusuarios){
            if(nombrearraylist.equals(nombreintroducido)){
                if(Passwordtxt.getText().equals(recogecontraseña(nombreintroducido))){
                    UserMenu();
                }
            }
        }

    }

    private void CheckAdmin() throws SQLException, IOException {
        ArrayList<String> nombreusuarios = recogeAdmin();
        String nombreintroducido = (String) UserNametxt.getText();
        for(String nombrearraylist : nombreusuarios){
            if(nombrearraylist.equals(nombreintroducido)){
                if(Passwordtxt.getText().equals(recogecontraseñaA(nombreintroducido))){
                    if(CheckPermision(nombreintroducido) == 1) {
                        AdminMenu();
                    }else{
                        AdminMenuB();
                    }
                }
            }
        }
    }

    private int CheckPermision(String nombreintroducido) throws SQLException {
        int a = 0;
        PreparedStatement pstat = null;
        String castro = String.valueOf(UserNametxt);
        System.out.print(castro);
        pstat = c.prepareStatement("SELECT Permisos from Admin where userName = ?");
        pstat.setString(1, UserNametxt.getText());
        pstat.executeQuery();
        ResultSet rs = pstat.executeQuery();
        while(rs.next()){
            a = rs.getInt("Permisos");
        }

        return a;
    }

    private void AdminMenuB() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AdminB.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        AdminController controlador = fxmlLoader.getController();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void AdminMenu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Admin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        AdminController controlador = fxmlLoader.getController();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }

    private ArrayList<String> recogeAdmin() throws SQLException {
        ArrayList<String> Admins = new ArrayList<>();

        Statement stat = c.createStatement();
        ResultSet rs = stat.executeQuery("SELECT UserName from Admin");
        while(rs.next()){
            String nombre = rs.getString("UserName");
            Admins.add(nombre);
        }
        return Admins;
    }

    @FXML
    public void UserMenu() throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("UserView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            UserController controlador = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
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
    public String recogecontraseña(String usuario) throws SQLException {
        String contraseña = "";

        PreparedStatement stat = c.prepareStatement("SELECT Password from users where UserName = ?");
        stat.setString(1 , usuario);
        ResultSet res = stat.executeQuery();
        while(res.next()){
            contraseña = res.getString("password");
        }
        return contraseña;
    }
    @FXML
    public void Singup(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Createuser.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 306, 400);
        CreateuserController controlador = fxmlLoader.getController();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }
    @FXML
    public void PasswordF(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ForgotP.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 363, 322);
        PasswordForgotController controlador = fxmlLoader.getController();
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }

    public String recogecontraseñaA(String usuario) throws SQLException {
        String contraseña = "";

        PreparedStatement stat = c.prepareStatement("SELECT Password from admin where UserName = ?");
        stat.setString(1 , usuario);
        ResultSet res = stat.executeQuery();
        while(res.next()){
            contraseña = res.getString("password");
        }
        return contraseña;
    }


}

