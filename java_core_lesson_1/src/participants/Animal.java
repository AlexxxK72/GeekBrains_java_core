package participants;

public abstract class Animal implements iChallengable {
   protected int maxRunDistance;
   protected int maxJumpHeight;
   protected int maxSwimDistance;
   protected String name;
   protected Boolean onDistance;

    public Animal(String name, int maxRunDistance, int maxJumpHeight, int maxSwimDistance) {
        this.maxRunDistance = maxRunDistance;
        this.maxJumpHeight = maxJumpHeight;
        this.maxSwimDistance = maxSwimDistance;
        this.name = name;
        this.onDistance = true;
    }

    @Override
    public void run(int distance){
        if(distance <= this.maxRunDistance) System.out.println(this.name + " ran the distance!");
        else {
            onDistance = false;
            System.out.println(this.name + " failed the running distance!");
        }
    }

    @Override
    public void jump(int height){
        if(height <= this.maxJumpHeight) System.out.println(this.name + " jumped over the obstacle!");
        else {
            onDistance = false;
            System.out.println(this.name + " failed to jump over the obstacle!");
        }
    }

    @Override
    public void swim(int distance){
        if(distance <= this.maxSwimDistance) System.out.println(this.name + " swam the distance");
        else {
            onDistance = false;
            System.out.println(this.name + " failed the swimming distance!");
        }
    }

    @Override
    public void printInfo(){
        if(onDistance) System.out.println(this.name + " successfully finished the marathon!");
        else System.out.println(this.name + " failed!");
    }

    @Override
    public boolean isOnDistance() {
        return onDistance;
    }
}
