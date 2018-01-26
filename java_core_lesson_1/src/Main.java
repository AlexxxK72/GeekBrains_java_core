import obstacles.*;
import participants.*;

public class Main {

    public static void main(String[] args) {
        Course course = new Course();
        Team team = new Team("Dream");
        course.doIt(team);

        System.out.println("\n");
        team.showResultOk();
        System.out.println("\n");
        team.showResult();


    }
}
