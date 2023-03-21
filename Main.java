import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalClassSize {


        Student firstStudent = new Student("Piotr", "Maciaszek", StudentCondition.ODRABIAJĄCY, 2005,
                15);

        Student secondStudent = new Student("Wojciech", "Mackowiak", StudentCondition.OBECNY, 2001,
                60);

        Student thirdStudent = new Student("Jan", "Krzanowski", StudentCondition.OBECNY, 2003,
                7.5);

        Student fourthStudent = new Student("Maciej", "Łabuz", StudentCondition.ZWOLNIONY, 2002,
                0);

        Student fifthStudent = new Student("Maksymilian", "Kubiczek", StudentCondition.CHORY, 2002,
                150);


        Class pau = new Class("Programowanie aplikacji uzytkowych", 30);
        Class fem = new Class("Metoda elementow skonczonych", 15);

        pau.addStudent(firstStudent);
        pau.addStudent(thirdStudent);
        pau.addStudent(fifthStudent);

        fem.addStudent(secondStudent);
        fem.addStudent(fourthStudent);

        pau.summary();
        fem.summary();


        fem.getStudent(fourthStudent);

        System.out.println(fem.size);

        System.out.println(pau.countByCondition(StudentCondition.OBECNY));


        ClassContainer agh = new ClassContainer();
        agh.addClass("PAU",pau);
        agh.addClass("FEM", fem);
        agh.summary();


    }
}