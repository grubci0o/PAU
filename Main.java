import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalClassSize {

        Student stud1 = new Student("piotr", "czubat",StudentCondition.OBECNY, 2001, 550);
        Student stud2 = new Student("konrad", "wyka",StudentCondition.OBECNY, 2001, 350);
        Student stud3 = new Student("pawel", "cdsa",StudentCondition.OBECNY, 2001, 250);

        Class klasa3F = new Class("klasa3F",40);

        klasa3F.addStudent(stud1);
        klasa3F.addStudent(stud2);
        klasa3F.addStudent(stud3);


        List<Student> part = klasa3F.partialSearch("czu");
        for (Student stud : part){
            System.out.println(stud);
        }
        System.out.println(klasa3F.countByCondition(StudentCondition.NIEOBECNY));

        List<Student> sortedListName = klasa3F.sortByName();
        List<Student> sortedListPoints = klasa3F.sortByPointsDescending();

        System.out.println("Punktami");
        for (Student stud : sortedListPoints){
            System.out.println(stud);
        }

        System.out.println("Imionami");
        for (Student stud : sortedListName){
            System.out.println(stud);
        }

        System.out.println(klasa3F.maxPoints());
    }
}