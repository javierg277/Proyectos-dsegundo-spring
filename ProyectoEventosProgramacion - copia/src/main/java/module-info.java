module com.example.pruebalogin {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
    requires java.desktop;
    requires java.sql;
    requires java.xml.bind;

    opens com.example.pruebalogin to javafx.fxml;
    exports com.example.pruebalogin;
}