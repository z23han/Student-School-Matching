import java.util.ArrayList;

public class Participant {

    private String name;
    private int[] rankings;
    private ArrayList<Integer> matches = new ArrayList<>();
    private int regret;
    private int maxMatches;


    public Participant() {}

    public Participant(String name, int maxMatches, int nParticipants) {

        this.name = name;
        this.maxMatches = maxMatches;
        this.rankings = new int[nParticipants];
    }

    public String getName()                 { return this.name; }
    public int getRankings(int i)           { return this.rankings[i]; }
    public int getMatch(int i)              { return this.matches.get(i); }
    public int getRegret()                  { return this.regret; }

    public int getMaxMatches()              { return this.maxMatches; }

    public int getNMatches()                { return this.matches.size(); }
    public int getNParticipants()           { return rankings.length; }

    // isFull is represented when all the participants (ranking length) are all in the matches array
    public boolean isFull()                 { return matches.size() == maxMatches; }

    public void setName(String name)        { this.name = name; }
    public void setRanking(int i, int r)   { this.rankings[i] = r; }

    // setMatch is to add m to the matches list if it's not there
    public void setMatch(int m) {

        if (!matchExists(m))
            this.matches.add(m);
    }

    public void setRegret(int r)            { this.regret = r;}
    public void setNParticipants(int n)     { this.rankings = new int[n]; }
    public void setMaxMatches(int n)        { this.maxMatches = n; }


    public void clearMatches() {

        this.matches.clear();
    }

    public int findRankingByID(int k)       { return this.rankings[k]; }

    // worst match is retrieved, when we iterate the matches array and check the rankings
    // return -1 if matches is empty
    public int getWorstMatch() {

        int participantID = -1;
        int worst = -1;

        for (int match : matches) {
            if (rankings[match] > worst) {
                worst = rankings[match];
                participantID = match;
            }
        }
        return participantID;
    }

    // unmatch is to remove k from matches list if it's there
    public void unmatch(int k) {

        if (matchExists(k)) {
            this.matches.remove(Integer.valueOf(k));
        }
    }

    public boolean matchExists(int k) {
        return this.matches.contains(k);
    }

    public int getSingleMatchedRegret(int k)    { return 0; }   // TODO
    public void calcRegret()                {}  // TODO

    public void editInfo(ArrayList<? extends Participant> P) {


    }

    public void editRankings(ArrayList<? extends Participant> P) {

    }

    // print methods
    public void print(ArrayList<? extends Participant> P) {

    }

    public void printRankings(ArrayList<? extends Participant> P) {

    }

    public String getMatchNames(ArrayList<? extends Participant> P) {

        ArrayList<String> names = new ArrayList<>();

        for (int match : matches) {
            Participant p = P.get(match);
            names.add(p.getName());
        }
        return String.join(", ", names);
    }

    public boolean isValid()                { return true; }    // TODO
}
