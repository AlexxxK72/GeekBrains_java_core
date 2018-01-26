package user_Exeption;

public class MyArraySizeException extends RuntimeException {
    public int rowNumber;

    public MyArraySizeException(String message, int rowNumber) {
        super(message);
        this.rowNumber = rowNumber;
    }

    @Override
    public String toString() {
        return "MyArraySizeException{" +
                getMessage() +
                " rowNumber=" + rowNumber +
                '}';
    }
}
