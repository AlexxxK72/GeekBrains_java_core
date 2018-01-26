package obstacles;

import participants.iChallengable;

public class Pool extends Obstacle{
    public int distance;

    public Pool(int distance) {
        this.distance = distance;
    }

    @Override
    public void doIt(iChallengable c) {
        c.swim(this.distance);
    }
}
