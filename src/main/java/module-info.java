module com.example.simulation_platform {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.simulation_platform to javafx.fxml;
    opens com.example.simulation_platform.controllers to javafx.fxml;
    exports com.example.simulation_platform;
}