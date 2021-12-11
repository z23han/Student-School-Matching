import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class School extends Participant {

    private double alpha;

    private static final int NAME_SPACE = 40;
    private static final int ASSIGNED_SPACE = 40;

    public School()     {}

    public School(String name, double alpha, int maxMatches, int nStudents) {

        super(name, maxMatches, nStudents);
        this.alpha = alpha;
    }

    public double getAlpha()                { return this.alpha; }
    public void setAlpha(double alpha)      { this.alpha = alpha; }

    public void editSchoolInfo(ArrayList<Student> S, boolean canEditRankings) {

    }

    public void calcRankings(ArrayList<Student> S) {

        Map<Double, List<Integer>> scoreMap = new HashMap<>();
        List<Double> scores = new ArrayList<>();

        for (int i = 0; i < S.size(); ++i) {

            Student s = S.get(i);

            double score = alpha * s.getGPA() + (1-alpha) * s.getES();

            if (scoreMap.containsKey(score)) {
                scoreMap.get(score).add(i);
            } else {

                List<Integer> list = new ArrayList<>();
                list.add(i);

                scores.add(score);
                scoreMap.put(score, list);
            }
        }
        scores.sort(Collections.reverseOrder());

        int rank = 1;
        for (Double score : scores) {
            for (int sID : scoreMap.get(score)) {

                this.setRanking(sID, rank);
                ++rank;
            }
        }
    }

    public void print(ArrayList<? extends Participant> S) {

        StringBuilder out = new StringBuilder(this.getName());

        // print school name
        if (this.getName().length() < NAME_SPACE) {

            int leftSpaceNumber = NAME_SPACE - this.getName().length() + 5;

            for (int i = 0; i < leftSpaceNumber; ++i) {
                out.append(" ");
            }

        } else {
            out.append("     ");
        }

        // print spots
        out.append("     ").append(this.getMaxMatches()).append("  ");

        // print alpha
        out.append(String.format("%.2f", this.alpha)).append("  ");

        // print assigned student
        if (this.getNMatches() == 0) {
            out.append("-");

            for (int i = 0; i < ASSIGNED_SPACE-1; ++i)
                out.append(" ");

        } else {

            String[] assignedStudents = new String[this.getNMatches()];

            for (int i = 0; i < this.getNMatches(); ++i) {
                assignedStudents[i] = S.get(getMatch(i)).getName();
            }

            String totalStudents = String.join(",", assignedStudents);

            out.append(totalStudents);
            if (totalStudents.length() <= ASSIGNED_SPACE) {
                int leftSpace = ASSIGNED_SPACE - totalStudents.length();

                for (int i = 0; i < leftSpace; ++i)
                    out.append(" ");
            }
        }

        // print preferred students
        if (this.getNParticipants() == 0) {
            out.append("-");

        } else {

            String[] preferredStudents = new String[S.size()];

            for (int i = 0; i < this.getNParticipants(); ++i) {

                int rank = getRankings(i);
                int pos = rank - 1;

                preferredStudents[pos] = S.get(i).getName();
            }

            out.append(String.join(", ", preferredStudents));
        }

        System.out.println(out.toString());
    }

    public boolean isValid()                { return true; } // TODO
}
