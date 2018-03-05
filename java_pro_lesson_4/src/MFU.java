public class MFU {
    private Q qprint;
    private Q qscan;
    private PrintScanner ps;

    public MFU() {
        this.qprint = new Q();
        this.qscan = new Q();
        this.ps = new PrintScanner(qprint, qscan);
    }

    public void print(Document doc){
        qprint.put(doc);
    }

    public void scan(Document doc){
        qscan.put(doc);
    }
}
