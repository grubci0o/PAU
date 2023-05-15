package com.example.javafxgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class HelloApplication extends Application {
    public static ClassContainer classes;
    @Override
    public void start(Stage stage) throws IOException, IllegalClassSize, ClassNotFoundException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        try {
            classes = deserializeData();
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Nie udalo sie wczytac danych - plik nie zostal odnaleziony");
            a.show();
        }
        stage.setOnCloseRequest(e -> {
            try {
                closeTest();
            } catch (IOException ignored) {

            }
        });
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    public void loadFakeData() throws IllegalClassSize {

        ClassContainer classContainer = new ClassContainer();


        Student firstStudent = new Student("Piotr", "Maciaszek", 2005
        );

        Student secondStudent = new Student("Wojciech", "Mackowiak", 2001
        );

        Student thirdStudent = new Student("Jan", "Krzanowski", 2003
        );

        Student fourthStudent = new Student("Maciej", "Łabuz", 2002
        );

        Student fifthStudent = new Student("Maksymilian", "Kubiczek", 2002
        );


        Class pau = new Class("Programowanie aplikacji uzytkowych", 30);
        Class fem = new Class("Metoda elementow skonczonych", 15);


        pau.addStudent(firstStudent, StudentCondition.OBECNY);
        pau.addStudent(secondStudent, StudentCondition.CHORY);
        pau.addStudent(thirdStudent, StudentCondition.NIEOBECNY);
        fem.addStudent(fourthStudent, StudentCondition.ODRABIAJĄCY);
        fem.addStudent(fifthStudent, StudentCondition.ODRABIAJĄCY);
        fem.addStudent(firstStudent, StudentCondition.NIEOBECNY);

        //dodanie punktow
        pau.addStudentMarks(firstStudent, 5.0);
        pau.addStudentMarks(firstStudent, 2.0);
        pau.addStudentMarks(secondStudent, 3.0);
        fem.addStudentMarks(firstStudent, 1.0);


        System.out.println(firstStudent.getAverage("Programowanie aplikacji uzytkowych"));

        classContainer.addClass("Programowanie aplikacji uzytkowych", pau);
        classContainer.addClass("Metoda elementow skonczonych", fem);

        classes = classContainer;

        classContainer.classesSummary();
    }
    public static ClassContainer deserializeData() throws IOException, ClassNotFoundException {
        ClassContainer cc = new ClassContainer();
        FileInputStream fis = new FileInputStream("students.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        cc = (ClassContainer) ois.readObject();
        return cc;
    }
    public void closeTest() throws IOException {
        boolean ans = ConfirmDialog.display("Zamkniecie programu","Czy zapisac dane?");
        if (ans) {
            classes.serializeData();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}