package com.example.javafxgui;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Class implements Serializable {

    public int size = 1;
    @Export
    public String className;
    private List<Student> studentList;
    @Export
    public int maxSize;

    public Class(String className, int maxSize) {
        this.className = className;
        this.studentList = new ArrayList<>();
        this.maxSize = maxSize;
    }
    public Double groupOccupancy() {
        return 100 * (double) size / (double) maxSize;
    }

    public void addStudent(Student student, StudentCondition condition) throws IllegalClassSize {
        boolean findStudent = studentList.contains(student);
        if(findStudent){
            return;
        }
        if(studentList.size() >= maxSize){
            throw new IllegalClassSize("Class is filled");
        }
        studentList.add(student);
        this.size++;
        student.studentMarks.put(className, new ArrayList<Double>());
        student.studentConditionMap.put(className, condition);

    }

    public void addStudentMarks(Student student, Double mark){
        List<Double> marks = student.studentMarks.get(className);
        if (marks == null) {
            marks = new ArrayList<Double>();
        }
        marks.add(mark);
    }

    public void setStudentCondition(Student student, StudentCondition cond) {
        student.setCondition(className, cond);
    }

    public void setMaxSize(int size) {
        this.maxSize = size;
    }


    public List<Student> getStudentList() {
        return studentList;
    }



    public Student search(String surname){
        for(Student student: studentList){
            if (Objects.equals(student.getSurname(), surname)){
                return student;
            }
        }
        return null;
    }


    public List<Student> partialSearch(String pattern){

        return studentList.stream().filter((student) -> student.getSurname()
                .contains(pattern)).collect(Collectors.toList());
    }

    public void summary(){
        for(Student stud : studentList){
            System.out.println(stud);
        }
    }

    @Override
    public String toString() {
        return "Class{" +
                "size=" + size +
                ", className='" + className + '\'' +
                ", maxSize=" + maxSize +
                ", percentage = " + maxSize/size +
                '}';
    }

    public Student findStudent(String surname) {
        for (Student student : studentList) {
            if (student.getSurname().equals(surname)) {
                return student;
            }
        }
        return null;
    }

    public List<Student> sortByName(){
        return studentList.stream().
                sorted(Student::compareTo).toList();
    }

    public String getClassName() {
        return className;
    }

    public Student getStudent(String surname){
        for (Student student : studentList) {
            if (Objects.equals(student.getSurname(), surname)) {
                return student;
            }
        }
        return null;
    }

}