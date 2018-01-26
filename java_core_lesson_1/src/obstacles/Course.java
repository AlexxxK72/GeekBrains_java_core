package obstacles;

import participants.Team;

public class Course {
    private Obstacle[] obstacles;
    public Course() {
        this.obstacles = new Obstacle[]{
                new Cross(500),
                new Wall(2),
                new Pool(100)
        };
    }

    public void doIt(Team team){
        for (int i = 0; i < team.members.length; i++)
            for (int j = 0; j < this.obstacles.length; j++)
                obstacles[j].doIt(team.members[i]);
    }
}
