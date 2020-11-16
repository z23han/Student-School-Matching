import java.util.Arrays;

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


    public void setRanking(int i, int r) throws Exception {

        if (Arrays.asList(rankings).contains(r)) {
            throw new Exception("ERROR: Rank " + r + " already used!");
        }

        this.rankings[i] = r;
    }

    // find school ranking based on school ID
    public int findRankingByID(int ind) {
        return this.rankings[ind];
    }


    // get new info from the user
    public void editInfo(School[] H, boolean canEditRankings) {


    }


    // edit rankings
    public void editRankings(School[] H) {

        for (int i = 0; i < H.length; ++i) {

        }
    }


    // print student info and assigned school in tabular format
    public void print(School[] H, boolean rankingsSet) {

        System.out.println(this.toString());

        if (rankingsSet)
            printRankings(H);
    }


    // print the rankings separated by a comma
    public void printRankings(School[] H) {

        if (H == null || H.length == 0)
            return;

        StringBuilder out = new StringBuilder("rankings: ");

        for (int i = 0; i < H.length; ++i) {
            out.append(rankings[i] + ",");
        }

        System.out.println(out.toString().substring(0, out.length()-1));
    }


    @Override
    public String toString() {

        return "Student {"  + this.name + ", " + this.GPA + ", " + this.ES + "}";
    }
}
