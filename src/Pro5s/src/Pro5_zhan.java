import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Pro5_zhan {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static final double MAX_GPA = 4;
    public static final int MAX_ES = 5;
    public static final double MAX_ALPHA = 1;

    public static void main(String[] args) throws IOException {

        String choice = "";

        ArrayList<Student> students = new ArrayList<>();
        ArrayList<School> schools = new ArrayList<>();

        SMPSolver studentSolver = new SMPSolver();
        SMPSolver schoolSolver = new SMPSolver();

        studentSolver.setSuitorFirst(true);
        schoolSolver.setSuitorFirst(false);

        for (;;) {
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
                    try {
                        System.out.println("STUDENT-OPTIMAL MATCHING");
                        System.out.println();

                        ArrayList<School> H1 = copySchools(schools);
                        ArrayList<Student> S1 = copyStudents(students);

                        studentSolver.reset();
                        studentSolver.setParticipants(S1, H1);
                        studentSolver.match();

                        System.out.println("SCHOOL-OPTIMAL MATCHING");
                        System.out.println();

                        ArrayList<School> H2 = copySchools(schools);
                        ArrayList<Student> S2 = copyStudents(students);

                        schoolSolver.reset();
                        schoolSolver.setParticipants(H2, S2);
                        schoolSolver.match();

                        // assign the matches to the current students and schools
                        studentSolver.updateParticipants(students, S1);
                        studentSolver.updateParticipants(schools, H1);

                    } catch (Exception e) {
                        System.out.println();
                        System.out.println(e.getMessage());
                        System.out.println();

                    }
                    break;

                case "D":
                    try {
                        studentSolver.printMatches();

                        schoolSolver.printMatches();

                    } catch (Exception e) {
                        System.out.println();
                        System.out.println(e.getMessage());
                        System.out.println();

                    }
                    break;

                case "X":

                    try {
                        printComparison(studentSolver, schoolSolver);

                    } catch (Exception e) {
                        System.out.println();
                        System.out.println(e.getMessage());
                        System.out.println();
                    }
                    break;

                case "R":

                    students.clear();
                    schools.clear();

                    System.out.println();
                    System.out.println("Database cleared!");
                    System.out.println();
                    break;

                case "Q":
                    System.out.println("Arrivederci!");
                    break;

                default:
                    System.out.println("ERROR: Invalid menu choice!");
                    System.out.println();

                    break;
            }
            if (choice.equals("Q"))
                break;
        }
    }


    public static void displayMenu() {

        System.out.println("JAVA STABLE MARRIAGE PROBLEM v3");
        System.out.println();

        System.out.println("L - Load students and schools from file");
        System.out.println("E - Edit students and schools");
        System.out.println("P - Print students and schools");
        System.out.println("M - Match students and schools using Gale-Shapley algorithm");
        System.out.println("D - Display matches");
        System.out.println("X - Compare student-optimal and school-optimal matches");
        System.out.println("R - Reset database");
        System.out.println("Q - Quit");
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

        return loadSchoolsFile(input, H);
    }


    private static int loadSchoolsFile(String filePath, ArrayList<School> H) throws Exception {

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            String line;
            int lineCount = 0;
            int addCount = 0;

            while ((line = br.readLine()) != null) {

                lineCount += 1;
                String[] schoolInfo = line.split(",");

                if (schoolInfo.length != 3)
                    continue;

                try {
                    String name = schoolInfo[0];
                    double alpha = getDouble(schoolInfo[1], 0, MAX_ALPHA);
                    int maxMatches = getInteger(schoolInfo[2], 1, Integer.MAX_VALUE);

                    School sc = new School(name, alpha, maxMatches, 0);

                    H.add(sc);

                    addCount += 1;

                } catch (Exception e) {
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

        return loadStudentsFile(input, S, H);
    }


    private static int loadStudentsFile(String filePath, ArrayList<Student> S, ArrayList<School> H) throws Exception {

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            String line;
            int lineCount = 0;
            int addCount = 0;

            int nSchools = H.size();
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

                    S.add(st);

                    addCount += 1;

                } catch (Exception e) {
                }
            }

            // update H nStudents and update the school's rankings
            for (School school : H) {
                school.setNParticipants(S.size());

                school.calcRankings(S);
            }

            System.out.println();
            System.out.println(addCount + " of " + lineCount + " students loaded!");
            System.out.println();

            return addCount;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    public static void editData(ArrayList<Student> S, ArrayList<School> H) throws IOException {

        String input = "";

        while (true) {

            System.out.println();
            System.out.println("Edit data");
            System.out.println("---------");
            System.out.println("S - Edit students");
            System.out.println("H - Edit high schools");
            System.out.println("Q - Quit");
            System.out.println();

            System.out.print("Enter choice: ");

            input = reader.readLine().toUpperCase();

            //respond to users' choice
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

        int StuID = -1;
        if (S.size() == 0) {
            System.out.println();
            System.out.println("ERROR: No students are loaded!");

            return;
        }

        //print student info and make user decide what to edit
        while (true) {

            System.out.println();
            printStudents(S, H);

            try {

                //Enter student to be edited
                while (true) {

                    String input = "";

                    System.out.print("Enter student (0 to quit): ");
                    input = reader.readLine();

                    if (input.trim().equals("0")) {
                        return;
                    }
                    // get the studentID
                    StuID = getInteger(input, 1, S.size()) - 1;

                    break;
                }

                Student Stu = S.get(StuID);

                System.out.println();
                System.out.print("Name: ");
                String input = reader.readLine();

                Stu.setName(input);

                while (true) {
                    System.out.print("GPA: ");
                    input = reader.readLine();
                    try {
                        double GPA = getDouble(input, 0, 4);
                        Stu.setGPA(GPA);
                        break;

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println();
                    }
                }

                while (true) {
                    System.out.print("Extracurricular score: ");
                    input = reader.readLine();
                    try {
                        int ES = getInteger(input, 0, 5);
                        Stu.setES(ES);
                        break;

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println();
                    }
                }

                while (true) {
                    System.out.print("Maximum number of matches: ");
                    input = reader.readLine();
                    try {
                        int maxMatches = getInteger(input, 1, Integer.MAX_VALUE);
                        Stu.setMaxMatches(maxMatches);
                        break;

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println();
                    }
                }

                boolean canEditRankings = true;

                while (true) {
                    System.out.print("Edit rankings (y/n): ");
                    input = reader.readLine();

                    if (input.toUpperCase().equals("Y")) {
                        break;
                    } else if (input.toUpperCase().equals("N")) {
                        canEditRankings = false;
                        break;
                    } else {
                        System.out.println("ERROR: Choice must be 'y' or 'n'!");
                        System.out.println();
                    }
                }

                if (canEditRankings) {

                    System.out.println();
                    System.out.println("Participant " + Stu.getName() + "'s rankings:");

                    ArrayList<Integer> enteredRankings = new ArrayList<>();

                    for (int i = 0; i < H.size(); ++i) {

                        School school = H.get(i);

                        while (true) {
                            System.out.print(school.getName() + ": ");
                            input = reader.readLine();
                            try {
                                int ranking = getInteger(input, 1, H.size());

                                if (enteredRankings.contains(ranking)) {
                                    System.out.println("ERROR: Rank " + ranking + " already used!");
                                }
                                Stu.setRanking(i, ranking);
                                enteredRankings.add(ranking);
                                break;

                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                System.out.println();
                            }
                        }
                    }
                }

                Stu.editInfo(H, canEditRankings);

                Stu.editRankings(H);

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

        //print schools and for users to decide what to edit
        while (true) {

            System.out.println();
            printSchools(S, H);

            try {
                int SchID = 0;

                //Which school users want to edit
                while (true) {

                    System.out.print("Enter school (0 to quit): ");

                    String input = reader.readLine();

                    if (input.trim().equals("0")) {
                        return;
                    }else {
                        SchID = getInteger(input, 1, H.size()) - 1;
                    }

                    break;
                }
                School Sch = H.get(SchID);

                System.out.println();
                System.out.print("Name: ");
                String input = reader.readLine();

                Sch.setName(input);

                while (true) {
                    System.out.println("GPA weight: ");
                    input = reader.readLine();

                    try {
                        double alpha = getDouble(input, 0, 1);
                        Sch.setAlpha(alpha);
                        break;

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println();
                    }
                }

                while (true) {
                    System.out.println("Maximum number of matches: ");
                    input = reader.readLine();

                    try {
                        int maxMatches = getInteger(input, 1, Integer.MAX_VALUE);
                        Sch.setMaxMatches(maxMatches);
                        break;

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println();
                    }
                }

                Sch.calcRankings(S);

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
        System.out.println(" #  Name                            GPA  ES  Assigned school                         Preferred school order");
        System.out.println("----------------------------------------------------------------------------------------------");

        for (int i = 1; i <= S.size(); ++i) {

            if (i < 10) {
                System.out.print(" " + i + ". ");
            } else {
                System.out.print(i + ". ");
            }

            Student student = S.get(i-1);

            student.print(H);
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
        System.out.println(" #  Name                                       # spots  Weight  Assigned students                       Preferred student order");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (int i = 1; i <= H.size(); ++i) {

            if (i < 10) {
                System.out.print(" " + i + ". ");
            } else {
                System.out.print(i + ". ");
            }

            School school = H.get(i-1);

            school.print(S);
        }

        System.out.println("--------------------------------------------------------------------------------------------");
    }


    public static void printComparison(SMPSolver GSS, SMPSolver GSH) {

        System.out.println("Solution              Stable    Avg school regret   Avg student regret     Avg total regret       Comp time (ms)");
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        GSS.printStatsRow("Student optimal");
        GSH.printStatsRow("School optimal");
        System.out.println("----------------------------------------------------------------------------------------------------------------");

        StringBuilder summary = new StringBuilder("WINNER");
        String winner = "";
        int leftSpace = 0;

        if ((GSS.isStable() && GSH.isStable()) || (!GSS.isStable() && !GSH.isStable())) {
            winner = "Tie";
        } else if (GSS.isStable()) {
            winner = "Student";
        } else {
            winner = "School";
        }
        leftSpace = 22 - winner.length();
        for (int i = 0; i < leftSpace; ++i)
            summary.append(" ");
        summary.append(winner);

        if (GSS.getAvgReceiverRegret() < GSH.getAvgSuitorRegret()) {
            winner = "Student";
        } else if (GSS.getAvgReceiverRegret() > GSH.getAvgSuitorRegret()) {
            winner = "School";
        } else {
            winner = "Tie";
        }
        leftSpace = 21 - winner.length();
        for (int i = 0; i < leftSpace; ++i)
            summary.append(" ");
        summary.append(winner);

        if (GSS.getAvgSuitorRegret() < GSH.getAvgReceiverRegret()) {
            winner = "Student";
        } else if (GSS.getAvgSuitorRegret() > GSH.getAvgReceiverRegret()) {
            winner = "School";
        } else {
            winner = "Tie";
        }
        leftSpace = 21 - winner.length();
        for (int i = 0; i < leftSpace; ++i)
            summary.append(" ");
        summary.append(winner);

        if (GSS.getAvgTotalRegret() < GSH.getAvgTotalRegret()) {
            winner = "Student";
        } else if (GSS.getAvgTotalRegret() > GSH.getAvgTotalRegret()) {
            winner = "School";
        } else {
            winner = "Tie";
        }
        leftSpace = 21 - winner.length();
        for (int i = 0; i < leftSpace; ++i)
            summary.append(" ");
        summary.append(winner);

        if (GSS.getTime() < GSH.getTime()) {
            winner = "Student";
        } else if (GSS.getTime() > GSH.getTime()) {
            winner = "School";
        } else {
            winner = "Tie";
        }
        leftSpace = 21 - winner.length();
        for (int i = 0; i < leftSpace; ++i)
            summary.append(" ");
        summary.append(winner);

        System.out.println(summary.toString());
        System.out.println("----------------------------------------------------------------------------------------------------------------");
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


    private static ArrayList<School> copySchools(ArrayList<School> P) {

        ArrayList<School> newList = new ArrayList<>();

        for (School school : P) {

            String name = school.getName();
            double alpha = school.getAlpha();
            int maxMatches = school.getMaxMatches();
            int nStudents = school.getNParticipants();
            School temp = new School(name, alpha, maxMatches, nStudents);

            for (int j = 0; j < nStudents; ++j) {
                temp.setRanking(j, school.getRankings(j));
            }
            newList.add(temp);
        }
        return newList;
    }


    private static ArrayList<Student> copyStudents(ArrayList<Student> P) {

        ArrayList<Student> newList = new ArrayList<>();

        for (Student student : P) {

            String name = student.getName();
            double GPA = student.getGPA();
            int ES = student.getES();
            int nSchools = student.getNParticipants();
            Student temp = new Student(name, GPA, ES, nSchools);

            for (int j = 0; j < nSchools; ++j) {
                temp.setRanking(j, student.getRankings(j));
            }
            newList.add(temp);
        }
        return newList;
    }

}
