package com.example.javafxgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import static com.example.javafxgui.HelloApplication.classes;
public class TeacherViewController {

    @FXML
    private Button addGradeBtn;

    @FXML
    private Button changeSceneBtn;

    @FXML
    private Button changeStudentConditionBtn;

    @FXML
    private TextField classMaxSizeField;

    @FXML
    private TextField classNameField;

    @FXML
    private TextField conditionTextField;

    @FXML
    private Button createClassBtn;

    @FXML
    private Button createStudentBtn;

    @FXML
    private TextField createSurnameField;

    @FXML
    private TextField gradeTextField;

    @FXML
    private TextField textFieldClassNameAddGrade;



    public void createStudent() {
        // musze stworzyc metode ktora sprawdzi liste wszystkich studentow i czy nazwisko sie nie powtarza ...
        if(createSurnameField.getText().equals("")) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Nie mozna stworzyc studenta o takim nazwisku");
            a.show();
            return;
        }
        Student student = classes.findStudent(createSurnameField.getText());
        if (student != null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Student o takim nazwisku juz istnieje");
            a.show();
            return;
        }

    }


    public void addGrade() {
        // jezeli student jest zapisany dodajesz ocene w przeciwnym wypadku alert box
        String className = textFieldClassNameAddGrade.getText();
        String strGrade = gradeTextField.getText();
        if (className.equals("") || strGrade.equals("")) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Nieprawidlwoe dane");
            a.show();
            return;
        }
        String surname = createSurnameField.getText();
        if (classes.groups.get(className).search(surname) != null && !surname.equals(""))
        try {
            Student student = classes.findStudent(surname);
            Double grade = Double.parseDouble(strGrade);
            classes.groups.get(className).addStudentMarks(student,grade);

        }
        catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Wystapil blad zweryfikuj dane");
            a.show();
        }
    }

    public void changeStudentCondition() {
        String className = textFieldClassNameAddGrade.getText();
        String surname = createSurnameField.getText();
        Student student = classes.findStudent(surname);
        if (surname.equals("") || student == null || conditionTextField.getText().equals("") || className.equals("")){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Wystapil blad zweryfikuj dane");
            a.show();
            return;
        }
        try {
            StudentCondition condition = null;
            for (StudentCondition cond : StudentCondition.values()) {
                if(cond.name().equals(conditionTextField.getText())){
                    condition = cond;
                }
            }
            student.setCondition(className, condition);
            System.out.println(student);
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Wystapil blad zweryfikuj dane");
            a.show();
            return;
        }
    }


    public void changeScene(ActionEvent e ) throws IOException {
        Parent helloViewParent = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene teachierViewScene = new Scene(helloViewParent);

        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

        window.setScene(teachierViewScene);
        window.show();
    }

    public void createClass() {
        String className = classNameField.getText();
        Integer maxSize = Integer.parseInt(classMaxSizeField.getText());

        try {
            classes.addClass(className, maxSize);
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Wystapil blad zweryfikuj dane");
            a.show();
        }
    }
}


