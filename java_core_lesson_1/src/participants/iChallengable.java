package participants;

public interface iChallengable {
    void run(int distance);
    void jump(int height);
    void swim(int distance);
    void printInfo();
    boolean isOnDistance();
}
