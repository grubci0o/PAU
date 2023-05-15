package com.example.javafxgui;

import java.io.Serializable;
import java.util.*;

public final class Student implements Comparable<Student>, Serializable {

    @Export
    private  String name;

    @Export
    private  String surname;
    public Map<String, ArrayList<Double>> studentMarks;
    public Map<String, StudentCondition> studentConditionMap;
    @Export
    private  int dob;

    public Student(
            String name,
            String surname,
            int dob
    ) {
        this.name = name;
        this.surname = surname;
        this.studentConditionMap = new HashMap<>();
        this.studentMarks = new HashMap<>();
        this.dob = dob;
    }

    @Override
    public int compareTo(Student student) {
        return this.surname.compareTo(student.surname);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public StudentCondition getCondition(String className) {
        return studentConditionMap.get(className);
    }

    public void setCondition(String className, StudentCondition condition) {
        studentConditionMap.put(className, condition);
    }


    public int getDob() {
        return dob;
    }

    public void setDob(int dob) {
        this.dob = dob;
    }

    public Double getAverage(String className){
        List<Double> marks = studentMarks.get(className);
        if (marks == null || marks.size() == 0){
            return 0.0;
        }
        return marks.stream().reduce(0.0, (subtotal, element) -> subtotal + element) / marks.size();
    }

    public Double getAverageAllClasses() {
        Double avg = 0.0;
        Integer classNum = 0;
        for (String clsName : studentMarks.keySet()) {
            avg += getAverage(clsName);
            classNum += 1;
        }
        if (classNum == 0) {
            return 0.0;
        }
        return avg / classNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return dob == student.dob && name.equals(student.name) && surname.equals(student.surname) && studentMarks.equals(student.studentMarks) && studentConditionMap.equals(student.studentConditionMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, studentMarks, studentConditionMap, dob);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", studentMarks=" + studentMarks +
                ", studentConditionMap=" + studentConditionMap +
                ", dob=" + dob +
                '}';
    }
}
