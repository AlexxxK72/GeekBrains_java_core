import java.io.Closeable;
import java.io.IOException;

public class PrintScanner implements Runnable, Closeable{
    private Q qprint;
    private Q qscan;
    Thread tWork;


    public PrintScanner(Q qprint, Q qscan) {
        this.qprint = qprint;
        this.qscan = qscan;
        tWork = new Thread(this);
        tWork.start();
    }

    @Override
    public void run() {
        new  Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Document doc = qprint.get();
                    for (int i = 0; i < doc.getPages(); i++) {
                        System.out.println("Отпечатано документ :\t" + doc.getName() + "\tстраница:\t" + i);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Document doc = qscan.get();
                    for (int i = 0; i < doc.getPages(); i++) {
                        System.out.println("Отсканировано документ :\t" + doc.getName() + "\tстраница:\t" + i);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public void close() throws IOException {
        tWork.interrupt();
    }
}