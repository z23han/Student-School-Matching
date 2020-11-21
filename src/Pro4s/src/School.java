import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

public class School {

    private String name;
    private double alpha;
    private int[] rankings;
    private int student;
    private int regret;

    Map<Double, List<Integer>> scoreMap;

    private static final int NAME_SPACE = 27;

    public School() {}

    public School(String name, double alpha, int nStudents) {

        this.name = name;
        this.alpha = alpha;

        this.student = -1;

        setNStudents(nStudents);
    }


    public String getName()                                     { return this.name; }
    public double getAlpha()                                    { return this.alpha; }
    public int getRanking(int i)                                { return this.rankings[i]; }
    public int getStudent()                                     { return this.student; }
    public int getRegret()                                      { return this.regret; }


    public void setName(String name)                            { this.name = name; }
    public void setAlpha(double alpha)                          { this.alpha = alpha; }
    public void setRanking(int i, int r)                        { this.rankings[i] = r; }
    public void setStudent(int i)                               { this.student = i; }
    public void setRegret(int r)                                { this.regret = r; }
    public void setNStudents(int n)                             { this.rankings = new int[n]; }


    // find student ranking based on student ID
    public int findRankingByID(int ind) {
        return rankings[ind];
    }


    // get new info from the user
    // implemented inside SMPLauncher editSchools
    public void editInfo(ArrayList<Student> S , boolean canEditRankings) {

    }


    // calculate rankings based on weight alpha
    public void calcRankings(ArrayList<Student> S) {

        scoreMap = new HashMap<>();

        setNStudents(S.size());

        for (int i = 0; i < S.size(); ++i) {

            Student st = S.get(i);
            double score = alpha * st.getGPA() + (1 - alpha) * st.getES();

            if (scoreMap.containsKey(score)) {
                scoreMap.get(score).add(i);

            } else {
                List<Integer> list = new ArrayList<>();
                list.add(i);

                scoreMap.put(score, list);
            }
        }

        List<Double> scores = new ArrayList<>(scoreMap.keySet());
        Collections.sort(scores, Collections.reverseOrder());

        int count = 1;
        for (Double score : scores) {
            List<Integer> sIDs = scoreMap.get(score);

            for (int s : sIDs) {
                setRanking(s, count);
                count++;
            }
        }
    }


    // print school info and assigned student in tabular format
    public void print(ArrayList<Student> S, boolean rankingsSet) {

        StringBuilder out = new StringBuilder();

        // print school name
        if (this.name.length() < NAME_SPACE) {

            int leftSpaceNumber = NAME_SPACE - this.name.length() + 5;

            out.append(this.name);

            for (int i = 0; i < leftSpaceNumber; ++i) {
                out.append(" ");
            }

        } else {
            out.append(this.name).append("     ");
        }

        // print alpha
        out.append(this.alpha).append("  ");

        // print assigned student
        if (this.student == -1) {
            out.append("-                          ");

        } else {

            Student s = S.get(this.student);

            out.append(s.getName() + "               ");
        }

        // print preferred students
        if (rankings.length == 0) {
            out.append("-");

        } else {

            String[] preferredStudents = new String[S.size()];

            for (int i = 0; i < rankings.length; ++i) {

                int rank = rankings[i];

                int pos = rank - 1;

                preferredStudents[pos] = S.get(i).getName();
            }

            out.append(String.join(", ", preferredStudents));
        }

        System.out.println(out.toString());
    }


    // print the rankings separated by a comma
    public void printRankings(ArrayList<Student> S) {

    }


    public boolean isValid() {
        return true;
    }
}
