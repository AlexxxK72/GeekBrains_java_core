package user_Exeption;

public class MyArrayDataException extends RuntimeException {
    public int row;
    public int column;

    public MyArrayDataException(String message, int row, int column) {
        super(message);
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return "MyArrayDataException{" +
                getMessage() +
                " row=" + row +
                ", column=" + column +
                '}';
    }
}
