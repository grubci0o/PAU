import java.util.*;
import java.util.stream.Collectors;

public class Class {

    public int size = 0;
    public String className;
    private List<Student> studentList;
    public final int maxSize;

    public Class(String className, int maxSize) {
        this.className = className;
        this.studentList = new ArrayList<>();
        this.maxSize = maxSize;
    }

    public void addStudent(Student student) throws IllegalClassSize {
        boolean findStudent = studentList.contains(student);
        if(findStudent){
            System.err.println("Student already in class");
        }
        if(studentList.size() >= maxSize){
            throw new IllegalClassSize("Class is filled");
        }
        studentList.add(student);
        this.size++;
    }


    public void getStudent(Student student){
        if(student.getPoints() == 0 && studentList.contains(student)){
            studentList.remove(student);
            this.size--;
        }

    }


    public Student search(String surname){
        for(Student student: studentList){
            if (student.getSurname() == surname){
                return student;
            }
        }
        return null;
    }


    public List<Student> partialSearch(String pattern){

        return studentList.stream().filter((student) -> student.getSurname()
                .contains(pattern)).collect(Collectors.toList());
    }

    public int countByCondition(StudentCondition condition){

        int amount = studentList.stream().filter( student -> student.getCondition() == condition)
                .toList().size();
        return amount;
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

    public List<Student> sortByName(){
        return studentList.stream().
                sorted((firstStudent, secondStudent) -> firstStudent.compareTo(secondStudent)).toList();
    }

    public List<Student> sortByPointsDescending(){

        return studentList.stream().
                sorted(Comparator.comparingDouble(Student::getPoints).reversed()).toList();
    }


    public double maxPoints(){
        return Collections.max(studentList, Student::comparePoints).getPoints();
    }

}