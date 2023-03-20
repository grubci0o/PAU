import java.util.Objects;

public final class Student implements Comparable<Student> {
    private  String name;
    private  String surname;
    private  StudentCondition condition;
    private  int dob;
    private  double points;

    public Student(
            String name,
            String surname,
            StudentCondition condition,
            int dob,
            double points
    ) {
        this.name = name;
        this.surname = surname;
        this.condition = condition;
        this.dob = dob;
        this.points = points;
    }

    @Override
    public int compareTo(Student student) {
        return this.surname.compareTo(student.surname);
    }

    public int comparePoints(Student student){
        return Double.compare(this.points, student.points);
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

    public StudentCondition getCondition() {
        return condition;
    }

    public void setCondition(StudentCondition condition) {
        this.condition = condition;
    }

    public int getDob() {
        return dob;
    }

    public void setDob(int dob) {
        this.dob = dob;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Student) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.surname, that.surname) &&
                Objects.equals(this.condition, that.condition) &&
                this.dob == that.dob &&
                Double.doubleToLongBits(this.points) == Double.doubleToLongBits(that.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, condition, dob, points);
    }

    @Override
    public String toString() {
        return "Student[" +
                "name=" + name + ", " +
                "surname=" + surname + ", " +
                "condition=" + condition + ", " +
                "dob=" + dob + ", " +
                "points=" + points + ']';
    }

    public void addPoints(double pts){
        this.points += pts;
    }


    public void changeCondition(StudentCondition condition){
        this.condition = condition;

    }


    public void removePoints(double pts){
        this.points -= pts;
    }

}
