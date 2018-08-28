package lesson1;

public  class methods {
    //Нахождение среднего арифметического массива
    public static double averageArrInt(int ... arr){
       int sum = 0;
       for (int item : arr){
           sum += item;
       }

       return sum/arr.length;
    }

    //Рекурсивный Поиск минимального элемента в массиве
    public static int minArrInt(int[] arr, int index) {
        if (arr.length > index) {
            int next = minArrInt(arr, index + 1);
            return (arr[index] > next) ? next : arr[index];
        } else {
            return arr[0];
        }
    }
}
