package lesson1;


public class main {

    public static void main(String[]args){

        //Нахождение среднего арифметического массива
        System.out.println("AVG(arr) = " + methods.averageArrInt(new int[]{1, 2, 3, 4, 5}));

        //Рекурсивный Поиск минимального элемента в массиве
        System.out.println("MIN(arr) = " + methods.minArrInt(new int[]{15, 21, 3, 48, 25}, 0));
    }
}
