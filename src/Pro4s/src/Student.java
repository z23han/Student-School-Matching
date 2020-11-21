import java.util.ArrayList;

public class Student {

    private String name;
    private double GPA;
    private int ES;
    private int[] rankings;
    private int school;
    private int regret;

    private static final int NAME_SPACE = 27;

    public Student() {}

    public Student(String name, double GPA, int ES, int nSchools) {

        this.name = name;
        this.GPA = GPA;
        this.ES = ES;

        this.school = -1;

        setNSchools(nSchools);
    }


    public String getName()                         { return this.name; }
    public double getGPA()                          { return this.GPA; }
    public int getES()                              { return this.ES; }
    public int getRanking(int i)                    { return rankings[i]; }
    public int getSchool()                          { return this.school; }
    public int getRegret()                          { return this.regret; }
    public int[] getRankings()                      { return this.rankings; }


    public void setName(String name)                { this.name = name; }
    public void setGPA(double GPA)                  { this.GPA = GPA; }
    public void setES(int ES)                       { this.ES = ES; }
    public void setSchool(int i)                    { this.school = i; }
    public void setRegret(int r)                    { this.regret = r; }
    public void setNSchools(int n)                  { this.rankings = new int[n]; }


    public void setRanking(int i, int r) throws Exception {

        for (int curr = 0; curr < i; ++curr) {

            if (rankings[curr] == r) {
                throw new Exception("ERROR: Rank " + r + " already used!");
            }
        }

        this.rankings[i] = r;
    }


    public int findRankingByID(int ind) {
        return this.rankings[ind];
    }


    // implemented inside SMPLauncher editStudent
    public void editInfo(ArrayList<School> H , boolean canEditRankings) {


    }


    // implemented inside SMPLauncher editStudents
    public void editRankings(ArrayList<School> H , boolean rankingsSet) {

    }


    public void print(ArrayList<School> H) {

        StringBuilder out = new StringBuilder(this.name);

        if (this.name.length() > NAME_SPACE) {
            out.append("     ");

        } else {

            int leftSpace = NAME_SPACE - this.name.length() + 5;

            for (int i = 0; i < leftSpace; ++i) {

                out.append(" ");
            }
        }

        out.append(this.GPA).append("   ");

        out.append(this.ES).append("  ");

        if (this.school == -1) {

            out.append("-                          ");

        } else {

            out.append(H.get(this.school).getName()).append("     ");
        }

        for (int r : rankings) {

            out.append(H.get(r-1).getName()).append(", ");
        }

        System.out.println(out.substring(0, out.length()-2));
    }


    public void printRankings(ArrayList<School> H) {


    }


    public boolean isValid() {

        return true;
    }
}
