package com.example.javafxgui;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import static com.example.javafxgui.HelloApplication.main;
import static com.example.javafxgui.HelloApplication.classes;
public class StudentView implements Initializable {

    @FXML
    private Button backBtn;
    public String studentSurname;

    @FXML
    private Label HelloNameStudentLabel;

    @FXML
    private TableColumn<TableViewClass, Double> averageColumn;

    @FXML
    private Button btnEnroll;

    @FXML
    private Button btnWithdraw;

    @FXML
    private ListView<String> coursesListView;

    @FXML
    private TableView<TableViewClass> enrolledCoursesTable;

    @FXML
    private TableColumn<TableViewClass, ArrayList<Double>> gradesColumn;

    @FXML
    private Button listCoursesButton;

    @FXML
    private TableColumn<TableViewClass, String> nameColumn;

    @FXML
    private Button refreshTableView;

    @FXML
    private Tooltip summaryTooltip;

    public void displayName(String username) {
        studentSurname = username;
    }

    public void displayClasses(ActionEvent e) {
        ArrayList<String> classNames = classes.getClassName();
        coursesListView.getItems().clear();
        coursesListView.getItems().addAll(classNames);
        coursesListView.getItems().add("Default");

    }

    public void loadRelevantClasses() {
        ArrayList<Class> classList = classes.classArrayList();
        ArrayList<TableViewClass> tableViewClasses = new ArrayList<>();
        // ObservableList<TableViewClass> classObservableList = enrolledCoursesTable.getItems();
        for (Class cls : classList) {
            Student stud = cls.findStudent(studentSurname);
            if (stud != null) {
                if (stud.studentConditionMap.get(cls.className) != StudentCondition.WYPISANY){
                Double average = stud.getAverage(cls.className);
                ArrayList<Double> grades = stud.studentMarks.get(cls.className);
                //classObservableList.add(new TableViewClass(cls.className, grades, average));
                tableViewClasses.add(new TableViewClass(cls.className, grades, average));
                }
            }
        }
        enrolledCoursesTable.getItems().clear();
        enrolledCoursesTable.setItems(FXCollections.observableArrayList(tableViewClasses));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<TableViewClass, String>("name"));
        gradesColumn.setCellValueFactory(new PropertyValueFactory<TableViewClass, ArrayList<Double>>("grades"));
        averageColumn.setCellValueFactory(new PropertyValueFactory<TableViewClass, Double>("average"));

        //listview

        coursesListView.setOnMouseClicked(event -> {
            String test = coursesListView.getSelectionModel().getSelectedItems().toString();
            //System.out.println(prettySummary(test));
            summaryTooltip.setText(prettySummary(coursesListView.getSelectionModel().getSelectedItems().toString()));
        });
    }

    public String prettySummary(String className) {
        String cleanedClassName = className.substring(1, className.length() - 1);
        System.out.println(cleanedClassName);
        Student student = classes.findStudent(studentSurname);
        if (student != null) {
            if (cleanedClassName.equals("Default")) {
                return student.getAverageAllClasses().toString();
            }
            StudentCondition condition = student.studentConditionMap.get(cleanedClassName);
            try {
                String prettyString = String.format("Oceny: %s, Średnia: %f, Stan Studenta: %s", student.studentMarks.get(cleanedClassName),
                        student.getAverage(cleanedClassName), condition.name());
                return prettyString;
            } catch (Exception e) {

            }
        }
        return null;
    }

    public void enroll(ActionEvent e) {
        //sprawdzic czy student nie znajduje sie juz w danym przedmiocie
        if (!coursesListView.getSelectionModel().selectionModeProperty().getValue().equals("")) {
            try {
                Student stud = classes.findStudent(studentSurname);
                classes.getGroups().get(coursesListView.getSelectionModel().selectedItemProperty().getValue()).addStudent(stud, StudentCondition.OCZEKUJACY);
            } catch (Exception ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Nalezy wybrac kurs z listy");
                a.show();
            }
        }
    }

    public void refreshTable(ActionEvent e) {
        loadRelevantClasses();
    }

    public void withdraw(ActionEvent e) {
        if (!coursesListView.getSelectionModel().selectionModeProperty().getValue().equals("")) {
            try {
                Student stud = classes.findStudent(studentSurname);
                stud.studentConditionMap.put(coursesListView.getSelectionModel().selectedItemProperty().getValue(), StudentCondition.WYPISANY);
            } catch (Exception ex) {
                //popup window ze nie jest zapisany
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.show();
            }

        }
    }

    public void changeScene(ActionEvent e ) throws IOException {
        Parent helloViewParent = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene teachierViewScene = new Scene(helloViewParent);

        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

        window.setScene(teachierViewScene);
        window.show();
    }
}
