import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SMPSolver {

    private ArrayList<Participant> S = new ArrayList<>();
    private ArrayList<Participant> R = new ArrayList<>();
    private double avgSuitorRegret;
    private double avgReceiverRegret;
    private double avgTotalRegret;
    private boolean matchesExist;
    private boolean stable;
    private long compTime;
    private boolean suitorFirst;

    public SMPSolver() {

        avgSuitorRegret = 0;
        avgReceiverRegret = 0;
        avgTotalRegret = 0;
    }

    public double getAvgSuitorRegret()                      { return this.avgSuitorRegret; }
    public double getAvgReceiverRegret()                    { return this.avgReceiverRegret; }
    public double getAvgTotalRegret()                       { return this.avgTotalRegret; }
    public boolean matchesExist()                           { return this.matchesExist; }
    public boolean isStable()                               { return this.stable; }
    public long getTime()                                   { return compTime; }
    public int getNSuitorOpenings()                         { return 0; }       // TODO
    public int getNReceiverOpenings()                       { return 0; }       // TODO

    public void setMatchesExist(boolean b)                  { this.matchesExist = b; }
    public void setSuitorFirst(boolean b)                   { this.suitorFirst = b; }

    public void setParticipants(ArrayList<? extends Participant> S, ArrayList<? extends Participant> R) {

        this.S.clear();
        this.S.addAll(S);

        this.R.clear();
        this.R.addAll(R);
    }

    public void clearMatches() {

        this.matchesExist = false;

        for (Participant p : S) {
            p.clearMatches();
        }

        for (Participant p : R) {
            p.clearMatches();
        }
    }

    public boolean matchingCanProceed()                     { return true; }    // TODO

    public boolean match() throws Exception {

        if (S.size() == 0) {
            throw new Exception("ERROR: No suitors are loaded!");
        }

        if (R.size() == 0) {
            throw new Exception("ERROR: No receivers are loaded!");
        }

        // a map that stores the current preferred pointer in the rankings
        Map<Integer, Integer> suitorChoiceMap = new HashMap<>();

        for (int i = 0; i < S.size(); ++i) {
            suitorChoiceMap.put(i, 0);
        }

        // loop and do 2-steps many-to-many Gale-Shapley algorithms
        int count = 0;

        long start_time = System.nanoTime();

        for (;;) {

            for (int i = 0; i < S.size(); ++i) {

                // step-1, get the next suitor
                Participant suitor = S.get(i);

                if (suitor.isFull())
                    continue;

                ++count;

                int rIndex = suitor.getRankings(suitorChoiceMap.get(i))-1;

                // step-2, check the receiver
                Participant receiver = R.get(rIndex);

                if (!receiver.isFull()) {

                    receiver.setMatch(i);
                    suitor.setMatch(rIndex);
                } else {

                    int worst = receiver.getWorstMatch();
                    receiver.unmatch(worst);

                    receiver.setMatch(i);
                    suitor.setMatch(rIndex);
                }

                // point to the next index in the rankings
                suitorChoiceMap.put(i, suitorChoiceMap.get(i)+1);
            }
            // if all the suitors are full, we break the loop
            if (count == 0)
                break;
            // reset count
            count = 0;
        }

        long end_time = System.nanoTime();
        compTime = (long) ((end_time - start_time) / 1e6);

        if (compTime == 0)
            compTime = 1;

        // after the iteration, the matches should be full
        printStats();

        System.out.println(Math.max(S.size(), R.size()) + " matches made in " + compTime + "ms!");
        System.out.println();

        return true;
    }

    public void updateParticipants(ArrayList<? extends Participant> DST, ArrayList<? extends Participant> SRC) {

        for (int i = 0; i < DST.size(); ++i) {

            Participant s0 = DST.get(i);
            Participant s1 = SRC.get(i);

            for (int j = 0; j < s1.getNMatches(); ++j) {

                s0.setMatch(s1.getMatch(j));
            }
        }
    }


    private boolean makeProposal(int suitor, int receiver)  { return true; }    // TODO

    private void makeEngagement(int suitor, int receiver, int oldSuitor) {}     // TODO

    public void calcRegrets() {

        // calculate suitor regret
        int sTotal = 0;
        for (Participant s : S) {

            for (int i = 0; i < s.getNMatches(); ++i) {
                int match = s.getMatch(i);
                int rank = s.getRankings(match);
                sTotal += rank - 1;
            }
        }
        avgSuitorRegret = Double.parseDouble(String.format("%.2f", (double)sTotal / S.size()));

        // calculate receiver regret
        int rTotal = 0;
        for (Participant r : R) {

            for (int i = 0; i < r.getNMatches(); ++i) {
                int match = r.getMatch(i);
                int rank = r.getRankings(match);
                rTotal += rank - 1;
            }
        }
        avgReceiverRegret = Double.parseDouble(String.format("%.2f", (double)rTotal / R.size()));

        avgTotalRegret = Double.parseDouble(String.format("%.2f", (double)(sTotal + rTotal) / (S.size() + R.size())));
    }

    public boolean determineStability()                     { return true; }

    // print methods
    public void print()         {}

    public void printMatches() {

        if (suitorFirst) {
            System.out.println("STUDENT-OPTIMAL SOLUTION");
        } else {
            System.out.println("SCHOOL-OPTIMAL SOLUTION");
        }
        System.out.println();

        System.out.println("Matches:");
        System.out.println("--------");

        if (suitorFirst) {

            for (Participant r : R) {

                System.out.print(r.getName() + ": ");
                System.out.println(r.getMatchNames(S));
            }
            System.out.println();

        } else {

            for (Participant s : S) {

                System.out.print(s.getName() + ": ");
                System.out.println(s.getMatchNames(R));
            }
            System.out.println();
        }

        printStats();
    }


    public void printStats() {

        calcRegrets();

        System.out.print("Stable matching? ");
        if (determineStability())
            System.out.println("Yes");
        else
            System.out.println("No");

        System.out.println("Average suitor regret: " + avgSuitorRegret);
        System.out.println("Average receiver regret: " + avgReceiverRegret);
        System.out.println("Average total regret: " + avgTotalRegret);

        System.out.println();
    }


    public void printStatsRow(String rowHeading) {

        StringBuilder out = new StringBuilder(rowHeading);

        int leftSpace = 25 - rowHeading.length();
        for (int i = 0; i < leftSpace; ++i)
            out.append(" ");

        if (determineStability())
            out.append("Yes");
        else
            out.append(" No");

        String regret = "";

        if (suitorFirst) {
            regret = String.format("%.2f", avgReceiverRegret);
        } else {
            regret = String.format("%.2f", avgSuitorRegret);
        }
        leftSpace = 21 - regret.length();
        for (int i = 0; i < leftSpace; ++i)
            out.append(" ");
        out.append(regret);

        if (suitorFirst) {
            regret = String.format("%.2f", avgSuitorRegret);
        } else {
            regret = String.format("%.2f", avgReceiverRegret);
        }
        leftSpace = 21 - regret.length();
        for (int i = 0; i < leftSpace; ++i)
            out.append(" ");
        out.append(regret);

        regret = String.format("%.2f", avgTotalRegret);
        leftSpace = 21 - regret.length();
        for (int i = 0; i < leftSpace; ++i)
            out.append(" ");
        out.append(regret);

        String timeStr = Long.valueOf(compTime).toString();
        leftSpace = 21 - timeStr.length();
        for (int i = 0; i < leftSpace; ++i)
            out.append(" ");
        out.append(timeStr);

        System.out.println(out.toString());
    }

    public void reset() {

        clearMatches();
        this.S.clear();
        this.R.clear();
        avgSuitorRegret = 0;
        avgReceiverRegret = 0;
        avgTotalRegret = 0;
    }
}
