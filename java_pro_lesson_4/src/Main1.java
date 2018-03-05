public class Main1 {
    public  static  Object monitor = new Object();
    public static char currChar = 'A';
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    synchronized (monitor){
                        while(currChar != 'A'){
                            monitor.wait();
                        }
                        System.out.print("A");
                        currChar = 'B';
                        monitor.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    synchronized (monitor){
                        while(currChar != 'B'){
                            monitor.wait();
                        }
                        System.out.print("B");
                        currChar = 'C';
                        monitor.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    synchronized (monitor){
                        while(currChar != 'C'){
                            monitor.wait();
                        }
                        System.out.print("C");
                        currChar = 'A';
                        monitor.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
