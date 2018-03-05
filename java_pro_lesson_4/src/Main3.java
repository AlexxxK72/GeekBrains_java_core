public class Main3 {

    public static void main(String[] args) {
        Document[] docs = new Document[]{
                new Document("1",5),
                new Document("2",15),
                new Document("3",10)};

        MFU mfu = new MFU();
        for (int i = 0; i < docs.length; i++) {
            mfu.print(docs[i]);
        }
        for (int i = 0; i < docs.length; i++) {
            mfu.scan(docs[i]);
        }
    }
}

