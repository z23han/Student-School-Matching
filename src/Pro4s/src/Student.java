import java.util.ArrayList;

public class Student {

    private String name;
    private double GPA;
    private int ES;
    private int[] rankings;
    private int school;
    private int regret;

    public Student() {}

    public Student(String name, double GPA, int ES, int nSchools) {

        this.name = name;
        this.GPA = GPA;
        this.ES = ES;

        setNSchools(nSchools);
    }


    public String getName()                         { return this.name; }
    public double getGPA()                          { return this.GPA; }
    public int getES()                              { return this.ES; }
    public int getRanking(int i)                    { return rankings[i]; }
    public int getSchool()                          { return this.school; }
    public int getRegret()                          { return this.regret; }


    public void setName(String name)                { this.name = name; }
    public void setGPA(double GPA)                  { this.GPA = GPA; }
    public void setES(int ES)                       { this.ES = ES; }
    public void setSchool(int i)                    { this.school = i; }
    public void setRegret(int r)                    { this.regret = r; }
    public void setNSchools(int n)                  { this.rankings = new int[n]; }


    public void setRanking(int i, int r) {


    }


    public int findRankingByID(int ind) {
        return this.rankings[ind];
    }


    public void editInfo(ArrayList< School > H , boolean canEditRankings) {


    }


    public void editRankings(ArrayList< School > H , boolean rankingsSet) {

    }


    public void print(ArrayList< School > H) {

    }


    public void printRankings(ArrayList< School > H) {


    }


    public boolean isValid() {

        return true;
    }
}
