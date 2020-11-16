import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SMPLauncher {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static ArrayList<Student> students = new ArrayList<>();

    public static ArrayList<School> schools = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        String choice = "";

        while (true) {

            displayMenu();

            System.out.println();
            System.out.print("Enter choice: ");
            choice = reader.readLine().toUpperCase();

            switch (choice) {

                case "L":
                    try {
                        int newStudentNumber = loadStudents(students, schools);

                        int newSchoolNumber = loadSchools(schools);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "E":
                    break;

                case "P":
                    break;

                case "M":
                    break;

                case "D":
                    break;

                case "R":
                    break;

                case "Q":
                    break;

                default:
                    System.out.println("ERROR: Invalid menu choice!");
                    break;
            }

            if (choice.equals("Q"))
                break;
        }
    }


    public static void displayMenu() {

        System.out.println("JAVA STABLE MARRIAGE PROBLEM v2");
        System.out.println();

        System.out.println("L - Load students and schools from file");
        System.out.println("E - Edit students and schools");
        System.out.println("P - Print students and schools");
        System.out.println("M - Match students and schools using Gale-Shapley algorithm");
        System.out.println("D - Display matches and statistics");
        System.out.println("R - Reset database");
        System.out.println("Q - Quit");
    }


    public static int loadStudents(ArrayList<Student> S, ArrayList<School> H) {

        return 0;
    }


    public static int loadSchools(ArrayList<School> H) {

        return 0;
    }


    public static void editData(ArrayList<Student> S, ArrayList<School> H) {

    }


    public static void editStudents(ArrayList<Student> S, ArrayList<School> H) {

    }


    public static void editSchools(ArrayList<Student> S, ArrayList<School> H) {

    }


    public static void printStudents(ArrayList<Student> S, ArrayList<School> H) {

    }


    public static void printSchools(ArrayList<Student> S, ArrayList<School> H) {

    }


    public static int getInteger(String prompt, int LB, int UB) {

        return 0;
    }


    public static double getDouble(String prompt, double LB, double UB) {

        return 0;
    }
}
