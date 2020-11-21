import java.util.*;

public class SMPSolver {

    private ArrayList<Student> S = new ArrayList<>();
    private ArrayList<School> R = new ArrayList<>();
    private double avgSuitorRegret;
    private double avgReceiverRegret;
    private double avgTotalRegret;
    private boolean matchesExist;

    public SMPSolver() {}

    public SMPSolver(ArrayList<Student> S, ArrayList<School> R) {
        this.S = S;
        this.R = R;

        avgSuitorRegret = 1;
        avgReceiverRegret = 1;
        avgTotalRegret = 1;
        matchesExist = false;
    }


    public double getAvgSuitorRegret()              { return this.avgSuitorRegret; }
    public double getAvgReceiverRegret()            { return this.avgReceiverRegret; }
    public double getAvgTotalRegret()               { return this.avgTotalRegret; }
    public boolean matchesExist()                   { return this.matchesExist; }


    public void reset(ArrayList<Student> S, ArrayList<School> R) {
        this.S = S;
        this.R = R;

        avgSuitorRegret = 0;
        avgReceiverRegret = 0;
        avgTotalRegret = 0;
        matchesExist = false;
    }


    public boolean match() throws Exception {

        if (S.size() == 0) {
            throw new Exception("ERROR: No suitors are loaded!");
        }

        if (R.size() == 0) {
            throw new Exception("ERROR: No receivers are loaded!");
        }

        if (S.size() != R.size()) {
            throw new Exception("ERROR: The number of suitors and receivers must be equal!");
        }

        // reset before matching
        for (Student s : S) {
            s.setSchool(-1);
        }

        for (School h : R) {
            h.setStudent(-1);
        }

        // init free studentID list and proposal map
        ArrayList<Integer> freeStudentIDs = new ArrayList<>();
        Map<Integer, List<Integer>> proposalMap = new HashMap<>();

        for (int i = 0; i < S.size(); ++i) {

            freeStudentIDs.add(i);

            proposalMap.put(i, new ArrayList<>());
        }

        while (freeStudentIDs.size() > 0) {

            int sID = freeStudentIDs.remove(0);

            Student currStudent = S.get(sID);

            int[] rankings = currStudent.getRankings();

            int currMin = Integer.MAX_VALUE;
            int hID = -1;

            for (int j = 0; j < rankings.length; ++j) {

                if (proposalMap.get(sID).contains(j))
                    continue;

                if (rankings[j] < currMin) {
                    hID = j;
                    currMin = rankings[j];
                }
            }

            if (hID == -1) {

                matchesExist = false;

                return false;

            } else {

                boolean result = makeProposal(sID, hID);

                if (result) {

                    // if receiver has been matched to another student before, we need to put the student to freeStudentIDs
                    School hSchool = R.get(hID);

                    if (hSchool.getStudent() != -1) {

                        int prevSID = hSchool.getStudent();

                        S.get(prevSID).setSchool(-1);

                        freeStudentIDs.add(prevSID);
                    }

                    makeEngagement(sID, hID);

                    proposalMap.get(sID).add(hID);

                } else {

                    proposalMap.get(sID).add(hID);

                    // we need to add back the student
                    freeStudentIDs.add(sID);
                }
            }
        }

        // set schools' student in case we missed before
        for (int i = 0; i < S.size(); ++i) {

            Student st = S.get(i);

            School h = R.get(st.getSchool());

            if (h.getStudent() == -1) {
                h.setStudent(i);
            }
        }

        matchesExist = true;

        return true;
    }


    public boolean makeProposal(int suitor, int receiver) {

        Student s = S.get(suitor);

        School h = R.get(receiver);

        if (h.getStudent() == -1) {

            return true;

        } else {

            int currSID = h.getStudent();

            if (currSID == suitor)
                return true;

            int suitorRank = h.getRanking(suitor);
            int currRank = h.getRanking(currSID);

            if (suitorRank < currRank) {
                return true;

            } else {
                return false;
            }
        }
    }


    public void makeEngagement(int suitor, int receiver) {

        Student s = S.get(suitor);

        School h = R.get(receiver);

        s.setSchool(receiver);

        h.setStudent(suitor);
    }


    public boolean matchingCanProceed() {

        return true;
    }


    public void calcRegrets() {

        double totalStudentRegret = 0.0;

        for (Student s : S) {

            int mySchoolID = s.getSchool();

            int mySchoolRank = s.findRankingByID(mySchoolID);

            totalStudentRegret += mySchoolRank - 1;
        }

        this.avgSuitorRegret = totalStudentRegret / S.size();

        double totalSchoolRegret = 0.0;

        for (School h : R) {

            int myStudentID = h.getStudent();

            int myStudentRank = h.findRankingByID(myStudentID);

            totalSchoolRegret += myStudentRank - 1;
        }

        this.avgReceiverRegret = totalSchoolRegret / R.size();

        this.avgTotalRegret = (this.avgSuitorRegret + this.avgReceiverRegret) / 2;
    }


    public boolean isStable() {

        if (matchesExist)
            return true;
        else
            return false;
    }


    public void print() throws Exception {

        printMatches();

        System.out.println();

        printStats();

        System.out.println();
    }


    public void printMatches() throws Exception {

        if (!matchesExist) {
            throw new Exception("ERROR: No matches exist!");
        }

        System.out.println("Matches:");
        System.out.println("--------");

        for (School h : R) {

            System.out.print(h.getName() + ": ");

            Student s = S.get(h.getStudent());

            System.out.println(s.getName());
        }
    }


    public void printStats() {

        System.out.print("Stable matching? ");

        if (isStable()) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

        calcRegrets();

        System.out.println("Average student regret: " + String.format("%.2f", getAvgSuitorRegret()));
        System.out.println("Average school regret: " + String.format("%.2f", getAvgReceiverRegret()));
        System.out.println("Average total regret: " + String.format("%.2f", getAvgTotalRegret()));
    }
}
