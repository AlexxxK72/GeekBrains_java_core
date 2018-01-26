package obstacles;

import participants.iChallengable;

public class Cross extends Obstacle {
    public int distance;

    public Cross(int distance) {
        this.distance = distance;
    }

    @Override
    public void doIt(iChallengable c) {
        c.run(this.distance);
    }
}
