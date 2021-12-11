import java.util.ArrayList;

public class Student extends Participant {

    private double GPA;
    private int ES;

    private static final int NAME_SPACE = 27;
    private static final int ASSIGNED_SPACE = 40;

    public Student()    {}

    public Student(String name, double GPA, int ES, int nSchools) {

        super(name, 1, nSchools);
        this.GPA = GPA;
        this.ES = ES;
    }

    public double getGPA()              { return this.GPA; }
    public int getES()                  { return ES; }
    public void setGPA(double GPA)      { this.GPA = GPA; }
    public void setES(int ES)           { this.ES = ES; }

    public void editInfo(ArrayList<School> H, boolean canEditRankings) {


    }

    public void print(ArrayList<? extends Participant> H) {

        StringBuilder out = new StringBuilder(getName());

        if (getName().length() > NAME_SPACE) {
            out.append("     ");

        } else {

            int leftSpace = NAME_SPACE - getName().length() + 5;
            for (int i = 0; i < leftSpace; ++i) {
                out.append(" ");
            }
        }

        out.append(this.GPA).append("   ");
        out.append(this.ES).append("  ");

        if (this.getNMatches() == 0) {
            out.append("-");

            for (int i = 0; i < ASSIGNED_SPACE-1; ++i)
                out.append(" ");
        } else {

            String schoolName = H.get(this.getMatch(0)).getName();
            int leftSpace = ASSIGNED_SPACE - schoolName.length();
            out.append(schoolName);

            for (int i = 0; i < leftSpace; ++i)
                out.append(" ");
        }

        for (int i = 0; i < this.getNParticipants(); ++i) {
            out.append(H.get(this.getRankings(i)-1).getName()).append(", ");
        }

        System.out.println(out.substring(0, out.length()-2));
    }

    public boolean isValid()            { return true; }    // TODO
}
