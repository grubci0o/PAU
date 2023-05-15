package com.example.javafxgui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {

    public static void main(String args[]) throws IllegalClassSize, IOException, ClassNotFoundException {
        System.out.println("Hello");

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

        ArrayList<String> names = classContainer.getClassName();
        for(String s: names) {
            System.out.println(s);
        }
        //classContainer.classesSummary();
        /*classContainer.fillHashSet();
        for (Student student : classContainer.studentSet) {
            for (String className : student.studentConditionMap.keySet()) {
                student.studentConditionMap.put(className, StudentCondition.OCZEKUJACY);
            }
        }
        System.out.println(classContainer.studentSet);
        System.out.println(firstStudent);
         */

        classContainer.serializeData();
        ClassContainer testConn = HelloApplication.deserializeData();
        System.out.println(testConn);
    }
}
