package com.example.javafxgui;

import java.io.*;
import java.util.*;

public class ClassContainer implements Serializable {
    Map<String, Class> groups;
    public HashSet<Student> studentSet;

    @Override
    public String toString() {
        return "ClassContainer{" +
                "groups=" + groups +
                ", studentSet=" + studentSet +
                '}';
    }

    public void addClass(String name, int size){
        Class c = new Class(name, size);
        groups.put(name, c);
    }

    public ArrayList<String> getClassName(){
        ArrayList<String> classNames = new ArrayList<>();
        classNames.addAll(groups.keySet());
        return classNames;
    }
    public void fillHashSet() {
        for (Class cls : groups.values()) {
            for (Student student : cls.getStudentList()) {
                if (!studentSet.contains(student)) {
                    studentSet.add(student);
                }
            }
        }
    }
    public void serializeData() throws IOException {
        FileOutputStream fos = new FileOutputStream("students.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        fillHashSet();
        oos.writeObject(this);
        oos.close();
        fos.close();
    }

    public Map<String, Class> getGroups(){
        return groups;
    }

    public Student findStudent(String surname) {
        for (Class cls : groups.values()) {
            Student stud = cls.findStudent(surname);
            if (stud != null) {
                return stud;
            }
        }
        return null;
    }

    public void addClass(String name, Class c){
        groups.put(name, c);
    }

    public ClassContainer(){
        this.groups = new HashMap<>();
        this.studentSet = new HashSet<>();
    }

    public void removeClass(String name){
        groups.remove(name);
    }

    public List<Class> findEmpty(){
        List<Class> emptyList = new ArrayList<>();
        for (String key : groups.keySet()){
            if (groups.get(key).size == 0) {
                emptyList.add(groups.get(key));
            }

        }
        return emptyList;
    }

    public void summary(){
        for (String key : groups.keySet()){
            System.out.println(groups.get(key));
        }
    }

    public ArrayList<Class> classArrayList() {
        ArrayList<Class> classArrayList = new ArrayList<>();
        for (Class cls : groups.values()) {
            classArrayList.add(cls);
        }
        return classArrayList;
    }
    public void classesSummary() {
        for (Class cls : groups.values()){
            System.out.println("Class name " + cls.getClassName() );
            cls.summary();
        }
    }


    public List<String> summaryGroups(){
        List<String> summaryList = new ArrayList<>();
        for (String key : groups.keySet()) {
            double perc = 100 * groups.get(key).groupOccupancy();
            summaryList.add("Nazwa grupy  " + groups.get(key).getClassName() + " stopien zapelnienia " + perc);
        }
        return summaryList;
    }

}
