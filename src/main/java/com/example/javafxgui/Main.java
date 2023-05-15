package com.example.javafxgui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String args[]) throws IllegalClassSize, IOException, ClassNotFoundException {

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

        //z class iteracja po student list i tam robic writeln(class name, studentSurname)
        //potem iteracja po student list wczesniejszej znalezc studentow z takim nazwiskiem i dodawac do odpowiednich klas


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


        for (Field f : Student.class.getDeclaredFields()) {
            f.setAccessible(true);
            System.out.println(f.getName());
        }


        List<Field> fList = new ArrayList<>(Arrays.asList(Student.class.getFields()));
        //fList.removeIf(field -> !field.isAnnotationPresent(Export.class));

        for (Field f : fList) {
            System.out.println(f.getName());
        }

    }
}
