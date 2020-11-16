import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class SMPLauncher {

    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static Student[] students = new Student[0];

    public static School[] schools = new School[0];

    public static int nStudents = 0;

    public static int nSchools = 0;

    public static boolean rankingsSet = false;

    public static final double MAX_GPA = 4;

    public static final int MAX_ES = 5;

    public static final double MAX_ALPHA = 1;

    public static void main(String[] args) throws IOException {

        String choice = "";

        while (true) {

            displayMenu();

            System.out.println();
            System.out.print("Enter choice: ");
            choice = reader.readLine().toUpperCase();

            switch (choice) {

                case "S":
                    try {
                        System.out.println();

                        addStudents();

                        System.out.println();

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "H":
                    try {
                        System.out.println();

                        addSchools();

                        System.out.println();

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "A":
                    try {
                        nStudents = 0;
                        nSchools = 0;

                        if (students != null)
                            nStudents = students.length;

                        if (schools != null)
                            nSchools = schools.length;

                        rankingsSet = assignRankings(students, schools, nStudents, nSchools);

                        if (rankingsSet)
                            System.out.println("Assigning rankings successful");
                        else
                            System.out.println("Assigning rankings failed");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "E":
                    try {
                        nStudents = 0;
                        nSchools = 0;

                        if (students != null)
                            nStudents = students.length;

                        if (schools != null)
                            nSchools = schools.length;

                        editData(students, schools, nStudents, nSchools, rankingsSet);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "P":
                    try {
                        nStudents = 0;
                        nSchools = 0;

                        if (students != null)
                            nStudents = students.length;

                        if (schools != null)
                            nSchools = schools.length;

                        printStudents(students, schools, nStudents, rankingsSet);
                        printSchools(students, schools, nSchools, rankingsSet);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "M":
                    try {
                        nStudents = 0;
                        nSchools = 0;

                        if (students != null)
                            nStudents = students.length;

                        if (schools != null)
                            nSchools = schools.length;

                        match(students, schools, nStudents, nSchools, rankingsSet);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "D":
                    try {
                        nStudents = 0;
                        nSchools = 0;

                        if (students != null)
                            nStudents = students.length;

                        if (schools != null)
                            nSchools = schools.length;

                        displayMatches(students, schools, nStudents, nSchools);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "R":
                    students = null;
                    schools = null;
                    System.out.println("Database cleared!");
                    break;

                case "Q":
                    System.out.println("Sayonara!");
                    break;

                default:
                    System.out.println("ERROR: Invalid menu choice!");
                    break;
            }

            if (choice.equals("Q"))
                break;
        }
    }


    /*
     * Display the menu
     * */
    public static void displayMenu() {

        System.out.println("JAVA STABLE MARRIAGE PROBLEM v1");
        System.out.println("");
        System.out.println("S - Enter students");
        System.out.println("H - Enter high schools");
        System.out.println("A - Assign rankings");
        System.out.println("E - Edit students and schools");
        System.out.println("P - Print students and schools");
        System.out.println("M - Match students and schools");
        System.out.println("D - Display matches and statistics");
        System.out.println("R - Reset database");
        System.out.println("Q - Quit");
    }


    /*
     * Get student information from user and return the number of students.
     * Any existing assignments and rankings are erased.
     * */
    public static int getStudents(List<Student> S) {

        if (S == null || S.size() == 0) {
            if (students == null)
                return 0;
            else
                return students.length;
        }

        students = new Student[S.size()];

        for (int i = 0; i < S.size(); ++i) {
            students[i] = S.get(i);
        }

        return students.length;
    }


    /*
     * Get school information from user and return the number of schools.
     * Any existing assignments and rankings are erased.
     * */
    public static int getSchools(List<School> H) {

        if (H == null || H.size() == 0) {
            if (schools == null)
                return 0;
            else
                return schools.length;
        }

        schools = new School[H.size()];

        for (int i = 0; i < H.size(); ++i) {
            schools[i] = H.get(i);
        }

        return schools.length;
    }


    /*
     * Get each student’s school rankings and calculate each school’s rankings of students
     * return whether or not ranking happened.
     * */
    public static boolean assignRankings(Student[] S, School[] H, int nStudents, int nSchools) throws Exception {

        if (nStudents == 0 || nSchools == 0)
            return false;

        System.out.println("Current All Schools");
        for (School h : H) {
            System.out.println(h.toString());
        }
        System.out.println("Please assign schools' rankings with comma delimited for each student");

        for (Student s : S) {

            System.out.println(s.toString() + ": Please enter");
            String input = reader.readLine();

            updateRankings(input, s, nSchools);
        }

        for (School h : H) {
            h.calcRankings(S);
        }

        return true;
    }


    private static void updateRankings(String prompt, Student st, int nSchools) throws Exception {

        String[] rankingInfo;

        try {
            rankingInfo = prompt.split(",");

            if (rankingInfo.length != nSchools)
                throw new Exception("School number is wrong");

            st.setNSchools(nSchools);

            for (int i = 0; i < nSchools; ++i) {

                int rank = getInteger(rankingInfo[i], 1, nSchools);
                st.setRanking(i, rank);
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    /*
     * Sub-area menu to edit students and schools.
     * */
    public static void editData(Student[] S, School[] H, int nStudents, int nSchools, boolean rankingsSet) throws Exception {

        editStudents(S, H, nStudents, nSchools, rankingsSet);

        editSchools(S, H, nStudents, rankingsSet);
    }


    /*
     * Sub-area to edit students.
     * */
    public static void editStudents(Student[] S, School[] H, int nStudents, int nSchools, boolean rankingsSet) throws Exception {

        if (nStudents == 0)
            return;

        while (true) {
            System.out.println("Pick the following students to edit. Enter student number");

            for (int i = 0; i < nStudents; ++i) {
                System.out.println(i+1 + ": " + S[i].toString());
            }

            String input = reader.readLine();

            int sID = getInteger(input, 1, nStudents) - 1;

            Student st = S[sID];
            System.out.println("You picked " + st.toString() + ". Please edit by entering (name, GPA, extracurricular score). Enter Q/q to quit");

            input = reader.readLine();

            if (input.trim().toUpperCase().equals("Q"))
                break;

            try {
                String[] studentInfo = input.split(",");

                if (studentInfo.length != 3) {
                    throw new Exception("ERROR: input format incorrect");
                }

                String name = studentInfo[0].trim();
                double GPA = getDouble(studentInfo[1], 0, MAX_GPA);
                int es = getInteger(studentInfo[2], 0, MAX_ES);

                st.setName(name);
                st.setGPA(GPA);
                st.setES(es);

                System.out.println("Student " + (sID+1) + " is updated " + st.toString());

                // edit rankings only if rankingsSet and there're more than 1 schools
                if (rankingsSet && nSchools > 0) {
                    System.out.println("Do you want to update rankings? (y/n)");

                    input = reader.readLine();

                    if (!input.trim().equals("y") && !input.trim().equals("n"))
                        throw new Exception("ERROR: Choice must be ’y’ or ’n’!");

                    else if (input.trim().equals("y")) {

                        System.out.println("Please enter new rankings for " + nSchools + " schools");

                        input = reader.readLine();
                        updateRankings(input, st, nSchools);
                    }
                }
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }

        // need to recalculate students' ranking
        for (School h : H) {
            h.calcRankings(S);
        }
    }


    /*
     * Sub-area to edit schools.
     * */
    public static void editSchools(Student[] S, School[] H, int nSchools, boolean rankingsSet) throws Exception {

        if (nSchools == 0)
            return;

        while (true) {
            System.out.println("Pick the following schools to edit. Enter school number");

            for (int i = 0; i < nSchools; ++i) {
                System.out.println(i+1 + ": " + H[i].toString());
            }

            String input = reader.readLine();

            int sID = getInteger(input, 1, nSchools) - 1;

            School sc = H[sID];
            System.out.println("You picked " + sc.toString() + ". Please edit by entering (name, alpha). Enter Q/q to quit");

            input = reader.readLine();

            if (input.trim().toUpperCase().equals("Q"))
                break;

            try {
                String[] schoolInfo = input.split(",");

                if (schoolInfo.length != 2) {
                    throw new Exception("ERROR: input format wrong");
                }

                String name = schoolInfo[0].trim();
                double alpha = getDouble(schoolInfo[1], 0, 1);

                sc.setName(name);
                sc.setAlpha(alpha);

                System.out.println("School " + (sID+1) + " is updated " + sc.toString());

                // need to recalculate student's rankings
                sc.calcRankings(S);

            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
    }


    /*
     * Print students to the screen, including matched school (if one exists).
     * */
    public static void printStudents(Student[] S, School[] H, int nStudents, boolean rankingsSet) {

        if (nStudents == 0) {
            System.out.println("No students");
            return;
        }

        System.out.println("---------- Students ----------");
        for (int i = 0; i < nStudents; ++i) {

            Student s = S[i];
            s.print(H, rankingsSet);
        }
    }


    /*
     * Print schools to the screen, including matched student (if one exists).
     * */
    public static void printSchools(Student[] S, School[] H, int nSchools, boolean rankingsSet) {

        if (nSchools == 0) {
            System.out.println("No schools");
            return;
        }

        System.out.println("---------- Schools ----------");
        for (int i = 0; i < nSchools; ++i) {

            School h = H[i];
            h.print(S, rankingsSet);
        }
    }


    /*
     * Match students and schools, and return whether or not matching happened.
     * */
    public static boolean match(Student[] S, School[] H, int nStudents, int nSchools, boolean rankingsSet) throws Exception {

        if (!matchingCanProceed(nStudents, nSchools, rankingsSet))
            return false;


        return true;
    }


    /*
     * Display matches and statistics.
     * */
    public static void displayMatches(Student[] S, School[] H, int nStudents, int nSchools) throws Exception {

        boolean rankingsSet = assignRankings(S, H, nStudents, nSchools);

    }


    /*
     * Check that the conditions to proceed with matching are satisfied.
     * */
    public static boolean matchingCanProceed(int nStudents, int nSchools, boolean rankingsSet) throws Exception {

        if (nStudents == 0)
            throw new Exception("ERROR: No students are loaded!");

        if (nSchools == 0)
            throw new Exception("ERROR: No schools are loaded!");

        if (nStudents != nSchools)
            throw new Exception("ERROR: The number of students and schools must be equal!");

        if (!rankingsSet)
            throw new Exception("ERROR: Student and school rankings must be set before matching!");

        return true;
    }


    /*
     * Get an integer in the range [LB, UB] from the user.
     * Prompt the user repeatedly until a valid value is entered.
     * */
    public static int getInteger(String prompt, int LB, int UB) throws Exception {

        try {
            int value = Integer.parseInt(prompt.trim());

            if (value > UB || value < LB) {
                throw new Exception("ERROR: Input must be an integer in [" + LB + ", " + UB + "]!");
            }

            return value;

        } catch (Exception e) {
            throw new Exception("ERROR: Input must be a real number in [" + LB + ", " + UB + "]!");
        }
    }


    /*
     * Get a real number in the range [LB, UB] from the user.
     * Prompt the user repeatedly until a valid value is entered.
     * */
    public static double getDouble(String prompt, double LB, double UB) throws Exception {

        try {
            double value = Double.parseDouble(prompt.trim());

            if (value > UB || value < LB) {
                throw new Exception("ERROR: Input must be a real number in [" + LB + ", " + UB + "]!");
            }

            return value;

        } catch (Exception e) {
            throw new Exception("ERROR: Input must be a real number in [" + LB + ", " + UB + "]!");
        }
    }


    private static void addStudents() throws Exception {

        System.out.print("Number of students to add: ");

        String input = reader.readLine();

        int numberStudent = getInteger(input, 0, Integer.MAX_VALUE);
        Student[] newStudents = new Student[numberStudent];

        boolean success;
        int currNumberStudent = students.length;
        int nSchools = schools.length;

        for (int i = 0; i < numberStudent; ++i) {
            System.out.println();

            System.out.print("Student " + (currNumberStudent + i + 1) + ": ");
            String name = reader.readLine();

            double GPA = 0.0;
            success = false;

            while (!success) {
                try {
                    System.out.print("GPA: ");

                    input = reader.readLine();
                    GPA = getDouble(input, 0, MAX_GPA);

                    success = true;

                } catch (Exception e) {

                    System.out.println();
                    System.out.println(e.getMessage());
                    System.out.println();
                }
            }

            int es = 0;
            success = false;

            while (!success) {
                try {
                    System.out.print("Extracurricular score: ");

                    input = reader.readLine();
                    es = getInteger(input, 0, MAX_ES);

                    success = true;

                } catch (Exception e) {

                    System.out.println();
                    System.out.println(e.getMessage());
                    System.out.println();
                }
            }

            Student newSt = new Student(name, GPA, es, nSchools);

            newStudents[i] = newSt;
        }

        Student[] total = new Student[students.length + newStudents.length];
        System.arraycopy(students, 0, total, 0, students.length);
        System.arraycopy(newStudents, 0, total, students.length, newStudents.length);

        students = total;
    }


    private static void addSchools() throws Exception {

        System.out.print("Number of schools to add: ");

        String input = reader.readLine();

        int numberSchool = getInteger(input, 0, Integer.MAX_VALUE);
        School[] newSchools = new School[numberSchool];

        boolean success;
        int currSchoolNumber = schools.length;
        int nStudents = students.length;

        for (int i = 0; i < numberSchool; ++i) {
            System.out.println();

            System.out.print("School " + (currSchoolNumber + i + 1) + ": ");
            String name = reader.readLine();

            double alpha = 0.0;
            success = false;

            while (!success) {
                try {
                    System.out.print("GPA weight: ");

                    input = reader.readLine();
                    alpha = getDouble(input, 0, MAX_ALPHA);

                    success = true;

                } catch (Exception e) {

                    System.out.println();
                    System.out.println(e.getMessage());
                    System.out.println();
                }
            }

            School newSc = new School(name, alpha, nStudents);

            newSchools[i] = newSc;
        }

        School[] total = new School[schools.length + newSchools.length];
        System.arraycopy(schools, 0, total, 0, schools.length);
        System.arraycopy(newSchools, 0, total, schools.length, newSchools.length);

        schools = total;
    }
}
