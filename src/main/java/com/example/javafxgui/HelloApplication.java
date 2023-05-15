package com.example.javafxgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

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
        stage.setTitle("Dziennik");
        stage.setScene(scene);
        stage.show();

        List<Student>  students= readStudentCSV();
        System.out.println(students);
        List<Class> cls = readClassesCSV();
        System.out.println(cls);
        readStudentGradesCSV(students);
        System.out.println(students);

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
            saveStudentsCSV();
            saveClassesCSV();
            saveGradesToCSV();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public List<Student> readStudentCSV() {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("students.csv"))) {
            var headerLine = br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                students.add(new Student(values[0], values[1], Integer.parseInt(values[2])));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return students;
    }


   public void readStudentGradesCSV(List<Student> students) throws IOException {
       try (BufferedReader br = new BufferedReader(new FileReader("grades.csv"))) {
           var headerLine = br.readLine();
           String line;
           while ((line = br.readLine()) != null) {
               String[] values = line.split(",");
               if (values.length >= 3){
                    Student student = null;
                    for (var stud : students) {
                        if (stud.getSurname().equals(values[0])) {
                            student = stud;
                        }
                    }
                   if (student != null) {
                       ArrayList<Double> gradeList = new ArrayList<>();
                       for (var markString : values[2].split(" ")) {
                           Double mark = Double.parseDouble(markString);
                           gradeList.add(mark);

                       }
                       student.studentMarks.put(values[1], gradeList);

                   }
            }
           }
   }
    }

   public void saveGradesToCSV() throws IOException {
       var fw = new FileWriter("grades.csv");
       var bw = new BufferedWriter(fw);
       var pw = new PrintWriter(bw);
       classes.fillHashSet();
       HashSet<Student> students = classes.studentSet;
       for (var student : students) {
           for (var className : student.studentMarks.keySet() ) {
               String marks = "";
               for (var mark : student.studentMarks.get(className)) {
                   marks = marks.concat(mark.toString() + ' ');
               }
               pw.println(student.getSurname() + ',' + className + ',' + marks);
           }
       }
       pw.flush();
       pw.close();
   }

   public List<Class> readClassesCSV() throws FileNotFoundException {
       List<Class> classesList = new ArrayList<>();
       try (BufferedReader br = new BufferedReader(new FileReader("classes.csv"))) {
           var headerLine = br.readLine();
           String line;
           while ((line = br.readLine()) != null) {
               String[] values = line.split(",");
               classesList.add(new Class(values[0], Integer.parseInt(values[1])));
           }
   } catch (IOException e) {
           throw new RuntimeException(e);
       }
       return classesList;
    }

       public void saveClassesCSV() throws IOException {
        List<Field> fList = new ArrayList<>(Arrays.asList(Class.class.getFields()));
        fList.removeIf(field -> !field.isAnnotationPresent(Export.class));
        var fw = new FileWriter("classes.csv");
        var bw = new BufferedWriter(fw);
        var pw = new PrintWriter(bw);

        String fields = "";
        for (Field f : fList) {
            fields = fields.concat(f.getName() + ',');
        }
        fields = fields.substring(0, fields.length() - 1);
        pw.println(fields);
        for (var cls : classes.groups.values()){
            pw.println(cls.className + ',' + cls.maxSize);
        }
        pw.flush();
        pw.close();
    }

    public void saveStudentsCSV() throws IOException {
        var fw = new FileWriter("students.csv");
        var bw = new BufferedWriter(fw);
        var pw = new PrintWriter(bw);


        String fields = "";
        for (Field f : Student.class.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(Export.class))
            {
                fields = fields.concat(f.getName() + ',');
            }
        }
        fields = fields.substring(0, fields.length() - 1);
        pw.println(fields);
        classes.fillHashSet();
        HashSet<Student> students = classes.studentSet;
        for (var student : students) {
            pw.println(student.getName() + ',' + student.getSurname() + ',' + student.getDob());
        }
        pw.flush();
        pw.close();
    }
}