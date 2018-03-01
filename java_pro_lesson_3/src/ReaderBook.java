import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class ReaderBook {
    private static final int COUNT_PAGE_SYMBOL = 1800;
    private File book;
    private long lengthBook = 0;

    public ReaderBook(String path) {
        this.book = new File(path);
        if(this.book.exists())lengthBook = this.book.length();
    }

    public void getPage(int page){
        if(page > 0 && (COUNT_PAGE_SYMBOL * (page - 1)) < lengthBook) {
            try {
                RandomAccessFile raf = new RandomAccessFile(book, "r");
                raf.seek(COUNT_PAGE_SYMBOL * (page - 1));
                byte[] bArr = new byte[100];
                int i = 0;
                System.out.println("\n");
                System.out.println("***********************Page " + page + "***********************");
                System.out.println("\n");
                while (raf.read(bArr) > 0 && i < 18) {
                    System.out.println(new String(bArr));
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else System.out.println("Page not found!");
    }
}
