public class Q {
    boolean docSet = false;
    Document doc;

    synchronized Document get() {
        while (!docSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        docSet = false;
        notify();
        return doc;
    }

    synchronized void put(Document doc) {
        while (docSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.doc = doc;
        docSet = true;
        notify();
    }
}
