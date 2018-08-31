package lesson1;


public class Main {

    public static void main(String[]args){

        //Нахождение среднего арифметического массива
        System.out.println("AVG(arr) = " + Methods.averageArrInt(new int[]{1, 2, 3, 4, 5}));

        //Рекурсивный Поиск минимального элемента в массиве
        System.out.println("MIN(arr) = " + Methods.minArrInt(new int[]{15, 21, 3, 48, 25}, 0));
    }
}
