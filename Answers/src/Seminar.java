import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Seminar {
    private List<Student> students = new ArrayList<Student>();

    private int[] notMarked;

    private static int[] removeTheElement(int[] arr, int index) {
        if (arr == null || index < 0
                || index >= arr.length) {

            return arr;
        }

        int[] anotherArray = new int[arr.length - 1];

        for (int i = 0, k = 0; i < arr.length; i++) {

            if (i == index) {
                continue;
            }

            anotherArray[k++] = arr[i];
        }

        return anotherArray;
    }

    private String getAction() {
        while (true) {
            System.out.print("> ");
            Scanner myObj = new Scanner(System.in);
            String action = myObj.nextLine();
            if (action.equals("/r") || action.equals("/l")) {
                return action;
            }
            if (action.equals("/e")) {
                System.exit(0);
            }
            System.out.println("/r - выбрать случайного студента");
            System.out.println("/l - вывести список студентов");
            System.out.println("/e - завершить работу программы");
        }
    }

    private void getCorrectGrade(Student student) {
        while (true) {
            Scanner myObj = new Scanner(System.in);
            String grade = myObj.nextLine();
            try {
                student.setGrade(Integer.parseInt(grade));
                return;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.print("Введите оценку еще раз: ");
            }
        }
    }

    public Seminar() {
        try {
            File myObj = new File("students.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String fullName = myReader.nextLine();
                students.add(new Student(fullName));
            }
            myReader.close();
            notMarked = new int[students.size()];
            for (int i = 0; i < notMarked.length; ++i) {
                notMarked[i] = i;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Произошла ошибка. Проверьте файл 'students.txt'");
            e.printStackTrace();
        }
    }

    public void printStudents() {
        for (Student student : students) {
            System.out.print(student.getFullName() + " - ");
            if (student.getGrade() == -1) {
                System.out.println("не опрошен(-а)");
                continue;
            }
            if (!student.getAttendance()) {
                System.out.println("отсутствует");
                continue;
            }
            System.out.println("присутствует, оценка " + student.getGrade());
        }
    }

    public Student getStudent() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, notMarked.length);
        int index = notMarked[randomNum];
        notMarked = removeTheElement(notMarked, randomNum);
        return students.get(index);
    }

    public void askStudent(Student student) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Отвечает " + student.getFullName());
        System.out.println(student.getFullName() + " присутствует на паре? (введите yes, если присутствует)");
        System.out.print("> ");
        String input = myObj.nextLine();
        if (input.toLowerCase().equals("yes")) {
            student.mark();
            System.out.print("Введите оценку: ");
            getCorrectGrade(student);
            return;
        }
        student.setGrade(0);
    }

    public void startChecking() {
        while (true) {
            if (getAction().equals("/r")) {
                if (notMarked.length != 0) {
                    askStudent(getStudent());
                } else {
                    System.out.println("Все студенты опрошены.");
                }
            } else {
                printStudents();
            }
        }
    }
}
