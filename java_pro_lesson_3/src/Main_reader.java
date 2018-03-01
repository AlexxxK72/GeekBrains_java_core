import java.util.Scanner;

public class Main_reader {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ReaderBook reader = new ReaderBook("book.txt");
        while (true){
            if(in.hasNext());
            String s = in.nextLine();
            if(s.equalsIgnoreCase("end"))break;
            else{
                int page = Integer.parseInt(s);
                reader.getPage(page);
            }
        }


    }
}
