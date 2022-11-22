public class Student {
    private String fullName = "";
    private boolean attendance = false;
    private int grade = -1;

    public Student() {
    }

    public Student(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public int getGrade() {
        return grade;
    }

    public boolean getAttendance() {
        return attendance;
    }

    public void setGrade(int grade) {
        if ((grade < 0) || (grade > 10)) throw new IllegalArgumentException("Оценка должна быть в отрезке [0;10]");
        this.grade = grade;
    }

    public void mark() {
        attendance = true;
    }

}
