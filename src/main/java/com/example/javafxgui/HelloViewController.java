package com.example.javafxgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import static com.example.javafxgui.HelloApplication.classes;
public class HelloViewController {

    @FXML
    private Button btnWidokNauczyciela;

    @FXML
    private Button btnWidokStudenta;

    @FXML
    private Label surnameLabel;

    @FXML
    private TextField surnameTextField;


    public void changeSceneTeacherView(ActionEvent e) throws IOException {
        Parent helloViewParent = FXMLLoader.load(getClass().getResource("teacher-view.fxml"));
        Scene teachierViewScene = new Scene(helloViewParent);

        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

        window.setScene(teachierViewScene);
        window.show();
    }

    public void changeSceneStudentView(ActionEvent e) throws IOException {
        String surname = surnameTextField.getText();
        if (surname.equals("")) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Podaj odpowiednie nazwisko");
            a.show();
            return;
        }
        if (classes.findStudent(surname) == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Nie stnieje student o takim nazwisku");
            a.show();
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("student-view.fxml"));
        Parent helloViewParent = loader.load();
        Scene helloViewScene = new Scene(helloViewParent);

        //access student view controller
        StudentView studentViewController = loader.getController();
        studentViewController.displayName(surname);
        studentViewController.loadRelevantClasses();

        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

        window.setScene(helloViewScene);
        window.show();

    }

}

