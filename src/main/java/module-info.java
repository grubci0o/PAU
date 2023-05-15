module com.example.javafxgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencsv;


    opens com.example.javafxgui to javafx.fxml;
    exports com.example.javafxgui;
}