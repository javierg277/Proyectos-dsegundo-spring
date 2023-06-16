package com.example.pruebalogin;

import javafx.fxml.Initializable;

import java.sql.*;

import com.example.pruebalogin.Domain.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;

public class PasswordForgotController implements Initializable {

    private final String bd = "eventos_programacion";
    private final String url = "jdbc:mysql://localhost:3306/";
    private final String login = "root";
    private final String password = "";

    private Connection c = null;

    @FXML
    private Button Checkboxbtt;
    @FXML
    private TextField UserNametxt;
    @FXML
    private TextField Agetxt;
    @FXML
    private Label Errorlbl;
    @FXML
    private TextField Passwordtxt;
    @FXML
    private Label Correctlbl;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public PasswordForgotController() {
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
    public void CheckData(MouseEvent mouseEvent) throws IOException , SQLException{
        ArrayList<String> nombreusuarios = RecogeUser();
        String nombreintroducido = (String) UserNametxt.getText();
        for(String nombrearraylist : nombreusuarios){
            if(nombrearraylist.equals(nombreintroducido)){
                if(Agetxt.getText().equals(getAge(nombreintroducido))){
                    AbrirPassword(nombreintroducido);
                }else{
                    Errorlbl.setText("Error: Datos Inválidos");
                }
            }
        }
    }

    private void AbrirPassword(String nombreintroducido) throws SQLException {
        String Password = recogePassword(nombreintroducido);
        Passwordtxt.setText(Password);
        Correctlbl.setText("Su contraseña es:");
    }

    private String recogePassword(String nombre) throws SQLException{
        String contraseña = "";

        PreparedStatement stat = c.prepareStatement("SELECT Password from users where UserName = ?");
        stat.setString(1 , nombre);
        ResultSet res = stat.executeQuery();
        while(res.next()){
            contraseña = res.getString("password");
        }
        return contraseña;
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
    public String getAge(String usuario) throws SQLException {
        int AgeI = 0;

        PreparedStatement stat = c.prepareStatement("SELECT Age from users where UserName = ?");
        stat.setString(1 , usuario);
        ResultSet res = stat.executeQuery();
        while(res.next()){
            AgeI = res.getInt("Age");
        }
        String Age = Integer.toString(AgeI);
        return Age;
    }
}
