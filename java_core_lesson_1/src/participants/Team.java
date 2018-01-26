package participants;

public class Team {
    protected String name;
    public iChallengable[] members;

    public Team(String name) {
        this.name = name;
        members = new iChallengable[]{
                new Cat("Barsik", 1000, 5, 0),
                new Dog("Sharik", 2000, 2, 200),
                new Human("Alex", 5000, 2, 1000),
                new Human("Max", 3000, 2, 500)};
    }

    public void showResultOk(){
        System.out.println("Information on the participants who completed the distance:\n");
        for (int i = 0; i < members.length; i++){
            if(members[i].isOnDistance())
            members[i].printInfo();
        }
    }

    public void showResult(){
        System.out.println("Information on all participants:\n");
        for (int i = 0; i < members.length; i++){
            members[i].printInfo();
        }
    }
}
