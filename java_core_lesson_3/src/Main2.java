import phoneBook.PhoneBook;
import phoneBook.PhoneRecord;

public class Main2 {
    public static void main(String[] args) {

        PhoneBook book = new PhoneBook();
        book.add(new PhoneRecord("Иванов", "+7 495 599-0260"));
        book.add(new PhoneRecord("Иванов", "+7 495 599-0260"));
        book.add(new PhoneRecord("Петров", "+7 495 598-0261"));
        book.add(new PhoneRecord("Сидоров", "+7 495 591-0222"));
        book.add(new PhoneRecord("Иванов", "+7 495 588-0263"));
        book.add(new PhoneRecord("Кузнецов", "+7 495 592-0264"));
        book.add(new PhoneRecord("Иванов", "+7 495 599-0265"));

        System.out.println(book.getPhones("Иванов"));

        book = new PhoneBook();
        book.add(new PhoneRecord("Кузнецов", "+7 495 592-0266"));

        System.out.println(book.getPhones("Кузнецов"));
    }
}
