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

    Map<Double, List<Integer>> scoreMap = null;

    public School() {}

    public School(String name, double alpha, int nStudents) {

        this.name = name;
        this.alpha = alpha;

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
    public void editInfo(Student[] S, boolean canEditRankings) {

        if (canEditRankings) {
            calcRankings(S);
        }
    }


    // calculate rankings based on weight alpha
    public void calcRankings(Student[] S) {

        scoreMap = new HashMap<>();

        for (int i = 0; i < S.length; ++i) {

            Student s = S[i];
            double score = alpha * s.getGPA() + (1 - alpha) * s.getES();

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
    public void print(Student[] S, boolean rankingsSet) {

        System.out.println(this.toString());

        if (rankingsSet)
            printRankings(S);
    }


    // print the rankings separated by a comma
    public void printRankings(Student[] S) {

        if (S == null || S.length == 0)
            return;

        StringBuilder out = new StringBuilder("rankings: ");

        for (int i = 0; i < S.length; ++i) {
            out.append(rankings[i] + ",");
        }

        System.out.println(out.toString().substring(0, out.length()-1));
    }


    @Override
    public String toString() {

        return "School {" + this.name + ", " + this.alpha + "}";
    }
}
