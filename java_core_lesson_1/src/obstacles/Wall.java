package obstacles;

import participants.iChallengable;

public class Wall extends Obstacle{
    public int height;

    public Wall(int height) {
        this.height = height;
    }

    @Override
    public void doIt(iChallengable c) {
        c.jump(this.height);
    }
}
