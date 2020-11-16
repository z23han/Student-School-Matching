import java.util.ArrayList;

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
    }


    public double getAvgSuitorRegret()              { return this.avgSuitorRegret; }
    public double getAvgReceiverRegret()            { return this.avgReceiverRegret; }
    public double getAvgTotalRegret()               { return this.avgTotalRegret; }
    public boolean matchesExist()                   { return this.matchesExist; }


    public void reset(ArrayList<Student> S, ArrayList<School> R) {
        this.S = S;
        this.R = R;
    }


    public boolean match() {
        return true;
    }


    public boolean makeProposal(int suitor, int receiver) {
        return true;
    }


    public void makeEngagement(int suitor, int receiver) {

    }


    public boolean matchingCanProceed() {
        return true;
    }


    public void calcRegrets() {

    }


    public boolean isStable() {
        return true;
    }


    public void print() {

    }


    public void printMatches() {

    }


    public void printStats() {

    }
}
