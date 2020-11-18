import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SMPLauncher {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static ArrayList<Student> students = new ArrayList<>();

    public static ArrayList<School> schools = new ArrayList<>();

    public static final double MAX_GPA = 4;

    public static final int MAX_ES = 5;

    public static final double MAX_ALPHA = 1;

    public static void main(String[] args) throws Exception {

        String choice = "";

        while (true) {

            displayMenu();

            System.out.println();
            System.out.print("Enter choice: ");

            choice = reader.readLine().toUpperCase();

            System.out.println();

            switch (choice) {

                case "L":
                    try {
                        loadSchools(schools);

                        loadStudents(students, schools);

                    } catch (Exception e) {

                        System.out.println();
                        System.out.println(e.getMessage());
                        System.out.println();
                    }

                    break;

                case "E":
                    try {
                        editData(students, schools);

                    } catch (Exception e) {

                        System.out.println();
                        System.out.println(e.getMessage());
                        System.out.println();
                    }

                    break;

                case "P":
                    try {
                        printStudents(students, schools);

                        printSchools(students, schools);

                    } catch (Exception e) {

                        System.out.println();
                        System.out.println(e.getMessage());
                        System.out.println();
                    }

                    break;

                case "M":
                    break;

                case "D":
                    break;

                case "R":

                    schools.clear();
                    students.clear();

                    System.out.println();
                    System.out.println("Database cleared!");
                    System.out.println();
                    break;

                case "Q":
                    break;

                default:
                    System.out.println();
                    System.out.println("ERROR: Invalid menu choice!");
                    System.out.println();

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


    public static int loadStudents(ArrayList<Student> S, ArrayList<School> H) throws Exception {

        boolean isFile = false;
        String input = "";

        while (!isFile) {

            System.out.print("Enter student file name (0 to cancel): ");

            input = reader.readLine();

            if (input.equals("0")) {

                System.out.println();
                System.out.println("File loading process canceled.");
                System.out.println();

                return 0;
            }

            File f = new File(input);

            if (!f.exists() || f.isDirectory()) {

                System.out.println();
                System.out.println("ERROR: File not found!");
                System.out.println();

                continue;
            }

            isFile = true;
        }

        int newStudentsNumber = loadStudentsFile(input);

        // update school
        for (School h : H) {
            h.calcRankings(S);
        }

        return newStudentsNumber;
    }


    public static int loadSchools(ArrayList<School> H) throws Exception {

        boolean isFile = false;
        String input = "";

        while (!isFile) {

            System.out.print("Enter school file name (0 to cancel): ");

            input = reader.readLine();

            if (input.equals("0")) {

                System.out.println();
                System.out.println("File loading process canceled.");
                System.out.println();

                return 0;
            }

            File f = new File(input);

            if (!f.exists() || f.isDirectory()) {

                System.out.println();
                System.out.println("ERROR: File not found!");
                System.out.println();

                continue;
            }

            isFile = true;
        }

        int newSchoolsNumber = loadSchoolsFile(input);

        // if more schools, students must be cleared
        if (schools.size() != students.size()) {
            students.clear();
        }

        return newSchoolsNumber;
    }


    public static void editData(ArrayList<Student> S, ArrayList<School> H) throws Exception {

        while (true) {

            System.out.println();
            System.out.println("---------");
            System.out.println("S - Edit students");
            System.out.println("H - Edit high schools");
            System.out.println("Q - Quit");
            System.out.println();

            System.out.print("Enter choice: ");

            String input = reader.readLine().toUpperCase();

            switch (input) {

                case "S":
                    editStudents(S, H);
                    break;

                case "H":
                    editSchools(S, H);
                    break;

                case "Q":
                    break;

                default:
                    System.out.println();
                    System.out.println("ERROR: Invalid menu choice!");
                    System.out.println();

                    break;
            }

            if (input.equals("Q"))
                break;
        }

    }


    public static void editStudents(ArrayList<Student> S, ArrayList<School> H) {

        if (S.size() == 0) {
            System.out.println();
            System.out.println("ERROR: No students are loaded!");

            return;
        }

        while (true) {

            System.out.println();
            printStudents(S, H);

            try {
                int studentID = -1;

                while (true) {

                    System.out.print("Enter student (0 to quit): ");

                    String input = reader.readLine();

                    if (input.trim().equals("0")) {
                        return;
                    }

                    try {
                        // get the studentID
                        studentID = getInteger(input, 1, S.size()) - 1;

                    } catch (Exception e) {
                        System.out.println();
                        System.out.println(e.getMessage());
                        System.out.println();

                        continue;
                    }

                    break;
                }

                Student st = S.get(studentID);

                System.out.println();
                System.out.print("Name: ");
                String input = reader.readLine();

                st.setName(input);

                double GPA;

                while (true) {

                    System.out.print("GPA: ");
                    input = reader.readLine();

                    try {
                        GPA = getDouble(input, 0, MAX_GPA);

                    } catch (Exception e) {
                        System.out.println();
                        System.out.println(e.getMessage());
                        System.out.println();

                        continue;
                    }

                    break;
                }

                st.setGPA(GPA);

                int ES;

                while (true) {

                    System.out.print("Extracurricular score: ");
                    input = reader.readLine();

                    try {
                        ES = getInteger(input, 0, MAX_ES);

                    } catch (Exception e) {
                        System.out.println();
                        System.out.println(e.getMessage());
                        System.out.println();

                        continue;
                    }

                    break;
                }

                st.setES(ES);

                String editRanking;

                while (true) {

                    System.out.print("Edit rankings (y/n): ");
                    input = reader.readLine();

                    if (input.toUpperCase().equals("Y") || input.toUpperCase().equals("N")) {
                        editRanking = input.toUpperCase();
                        break;
                    }

                    System.out.println("ERROR: Choice must be 'y' or 'n'!");
                }

                if (editRanking.equals("Y")) {

                    // student reset the ranking
                    st.setNSchools(H.size());

                    for (int i = 0; i < H.size(); ++i) {

                        while (true) {

                            System.out.print(H.get(i).getName() + ": ");

                            input = reader.readLine();

                            try {
                                int rank = getInteger(input, 1, H.size());

                                st.setRanking(i, rank);

                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                System.out.println();

                                continue;
                            }

                            break;
                        }
                    }
                }

            } catch (Exception e) {

                System.out.println();
                System.out.println(e.getMessage());
            }
        }
    }


    public static void editSchools(ArrayList<Student> S, ArrayList<School> H) {

        if (H.size() == 0) {
            System.out.println();
            System.out.println("ERROR: No schools are loaded!");

            return;
        }

        while (true) {

            System.out.println();
            printSchools(S, H);

            try {
                int schoolID = 0;

                while (true) {

                    System.out.print("Enter school (0 to quit): ");

                    String input = reader.readLine();

                    if (input.trim().equals("0")) {
                        return;
                    }

                    try {
                        schoolID = getInteger(input, 1, H.size()) - 1;

                    } catch (Exception e) {

                        System.out.println();
                        System.out.println(e.getMessage());
                        System.out.println();

                        continue;
                    }

                    break;
                }

                School sc = H.get(schoolID);

                System.out.println();
                System.out.print("Name: ");
                String input = reader.readLine();

                sc.setName(input);

                double alpha;

                while (true) {

                    System.out.print("GPA weight: ");
                    input = reader.readLine();

                    try {
                        alpha = getDouble(input, 0, MAX_ALPHA);

                    } catch (Exception e) {
                        System.out.println();
                        System.out.println(e.getMessage());
                        System.out.println();

                        continue;
                    }

                    break;
                }

                sc.setAlpha(alpha);

            } catch (Exception e) {

                System.out.println(e.getMessage());
            }
        }
    }


    public static void printStudents(ArrayList<Student> S, ArrayList<School> H) {

        if (S.size() == 0) {
            System.out.println();
            System.out.println("ERROR: No students are loaded!");

            return;
        }

        System.out.println("STUDENTS:");
        System.out.println();
        System.out.println(" #  Name                            GPA  ES  Assigned school            Preferred school order");
        System.out.println("----------------------------------------------------------------------------------------------");

        for (int i = 1; i <= S.size(); ++i) {

            if (i < 10) {
                System.out.print(" " + i + ". ");
            } else {
                System.out.print(i + ". ");
            }

            Student s = S.get(i-1);

            s.print(H);
        }

        System.out.println("----------------------------------------------------------------------------------------------");
    }


    public static void printSchools(ArrayList<Student> S, ArrayList<School> H) {

        if (H.size() == 0) {
            System.out.println();
            System.out.println("ERROR: No schools are loaded!");
            System.out.println();

            return;
        }

        System.out.println("SCHOOLS:");
        System.out.println();
        System.out.println(" #  Name                          Weight  Assigned student           Preferred student order");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (int i = 1; i <= H.size(); ++i) {

            if (i < 10) {
                System.out.print(" " + i + ". ");
            } else {
                System.out.print(i + ". ");
            }


            School h = H.get(i-1);

            if (h.getStudent() == -1) {

                h.print(S, false);

            } else {

                h.print(S, true);
            }
        }

        System.out.println("--------------------------------------------------------------------------------------------");
    }


    public static int getInteger(String prompt, int LB, int UB) throws Exception {

        try {
            int value = Integer.parseInt(prompt.trim());

            if (value > UB || value < LB) {
                throw new Exception("ERROR: Input must be an integer in [" + LB + ", " + UB + "]!");
            }

            return value;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    public static double getDouble(String prompt, double LB, double UB) throws Exception {

        try {
            double value = Double.parseDouble(prompt.trim());

            if (value > UB || value < LB) {
                throw new Exception("ERROR: Input must be a real number in [" + LB + ", " + UB + "]!");
            }

            return value;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    private static int loadSchoolsFile(String filePath) throws Exception {

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            String line;
            int lineCount = 0;
            int addCount = 0;

            while ((line = br.readLine()) != null) {

                lineCount += 1;
                String[] schoolInfo = line.split(",");

                if (schoolInfo.length != 2)
                    continue;

                try {
                    String name = schoolInfo[0];
                    double alpha = getDouble(schoolInfo[1], 0, MAX_ALPHA);

                    School sc = new School(name, alpha, 0);

                    schools.add(sc);

                    addCount += 1;

                } catch (Exception e) {
                    continue;
                }
            }

            System.out.println();
            System.out.println(addCount + " of " + lineCount + " schools loaded!");
            System.out.println();

            return addCount;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    private static int loadStudentsFile(String filePath) throws Exception {

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            String line;
            int lineCount = 0;
            int addCount = 0;

            int nSchools = schools.size();
            int nItems = nSchools + 3;

            List<Integer> rankingBucket = new ArrayList<>();

            while ((line = br.readLine()) != null) {

                lineCount += 1;
                String[] studentInfo = line.split(",");

                if (studentInfo.length != nItems)
                    continue;

                try {
                    String name = studentInfo[0];
                    double GPA = getDouble(studentInfo[1], 0, MAX_GPA);
                    int ES = getInteger(studentInfo[2], 0, MAX_ES);

                    Student st = new Student(name, GPA, ES, nSchools);

                    rankingBucket.clear();

                    for (int i = 3; i < nItems; ++i) {
                        int rank = getInteger(studentInfo[i], 1, nSchools);

                        if (rankingBucket.contains(rank))
                            continue;

                        st.setRanking(i-3, rank);
                        rankingBucket.add(rank);
                    }

                    students.add(st);

                    addCount += 1;

                } catch (Exception e) {
                    continue;
                }
            }

            System.out.println();
            System.out.println(addCount + " of " + lineCount + " students loaded!");
            System.out.println();

            return addCount;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
