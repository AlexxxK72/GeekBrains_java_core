public class Task implements Runnable{
    @Override
    public void run() {
        new Thread(new Runnable() {
            @Override
            public void run(){
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(1);
                        System.out.println(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run(){
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(1);
                        System.out.println(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new Thread(new Task()).start();
//        System.out.println("Main");
//        Thread t = new Thread(new Task());
//        t.start();
    }
}
