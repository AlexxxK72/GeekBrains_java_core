import user_Exeption.MyArrayDataException;
import user_Exeption.MyArraySizeException;

public class Main {
    public static void main(String[] args) {
        String[][] arr = createArray();

        try {
            int sumArr = sumArray(arr);
            System.out.println("Сумма значений в массиве равна: " + sumArr);
        }
        catch (MyArrayDataException|MyArraySizeException e) {
            e.printStackTrace();
            System.out.println("Массив задан не корректно, вычисление суммы значений невозможно");
        }
    }

    public static int sumArray(String[][] arr){
        int sum = 0;

        if(arr.length != 4) throw new MyArraySizeException(
                "Число строк в массиве равно " + arr.length  + " ;",
                (arr.length - 1));
        for (int i = 0; i < arr.length; i++) {
            String[] row = arr[i];
            if(row.length != 4) throw new MyArraySizeException(
                    "Число столбцов в " + i + "- й строке равно " + row.length + " ;"
                    , i);
            for (int j = 0; j < row.length; j++){

                try {
                    int add = Integer.parseInt(row[j]);
                    sum += add;
                }
                catch (Exception e) {
                    throw new MyArrayDataException(
                            "Элемент массива [" + i + "," + j + "] не является типом int; ",
                            i, j);
                }
            }
        }
        return sum;
    }

    public static String[][] createArray(){
        String[][] arr = {
            {"29", "28", "46", "158"},
            {"33", "56456", "46", "456"},
            {"456", "28", "апра", "21"},
            {"35", "7878", "46", "4"},
        };
        return arr;
    }
}
