import java.util.ArrayList;

public class School {

    private String name;
    private double alpha;
    private int[] rankings;
    private int student;
    private int regret;

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
    public void editInfo(ArrayList<Student> S , boolean canEditRankings) {

    }


    // calculate rankings based on weight alpha
    public void calcRankings(ArrayList<Student> S) {

    }


    // print school info and assigned student in tabular format
    public void print(ArrayList<Student> S , boolean rankingsSet) {

    }


    // print the rankings separated by a comma
    public void printRankings(ArrayList<Student> S) {

    }


    public boolean isValid() {
        return true;
    }
}
