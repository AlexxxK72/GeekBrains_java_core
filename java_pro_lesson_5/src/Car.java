import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable{
    private static int CARS_COUNT;
    private static int CARS_COUNT_FINISH;
    private static Boolean isWinner = false;
    private static Boolean isStart = false;
    private static Boolean isFinish = false;
    static {
        CARS_COUNT = 0;
        CARS_COUNT_FINISH = 0;
    }
    private CyclicBarrier cb;
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed, CyclicBarrier cb) {
        this.cb = cb;
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            cb.await();
            if(isStart == false) {
                isStart = true;
                System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        if(isWinner == false){
            isWinner = true;
            System.out.println(this.name + " - WIN");
        }
        CARS_COUNT_FINISH++;
        if(CARS_COUNT == CARS_COUNT_FINISH){
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        }
    }
}
