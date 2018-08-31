package lesson2;

public class Main {
    public static void main(String args[]) {

        System.out.println("Исходный массив");
        Array arr = new Array(5, 7, 10, 4, 25, 30, 4, 1, 2, 4, 4);
        System.out.println(arr);
        System.out.println("Удаление элемента с индексом 1");
        arr.delete(1);
        System.out.println(arr);
        System.out.println("Удаление элементов значение которых равно 4");
        arr.deleteAll(4);
        System.out.println(arr);
        System.out.println("Удаление всех элементов");
        arr.deleteAll();
        System.out.println(arr);

        arr = new Array(5, 7, 10, 4, 25, 30, 4, 1, 2, 4, 4);
        System.out.println(arr);
        arr.sortBubble();
        System.out.println("SortBubble");
        System.out.println("Сложность O(n^2) " + arr.length() * arr.length());
        System.out.println(arr);
        System.out.println(arr.countToString());
        arr = new Array(5, 7, 10, 4, 25, 30, 4, 1, 2, 4, 4);
        arr.sortFineBubble();
        System.out.println("SortFineBubble");
        System.out.println("Сложность O(n^2) " + arr.length() * arr.length());
        System.out.println(arr);
        System.out.println(arr.countToString());
        arr = new Array(5, 7, 10, 4, 25, 30, 4, 1, 2, 4, 4);
        arr.sortInsert();
        System.out.println("SortInsert");
        System.out.println("Сложность O(n^2) " + arr.length() * arr.length());
        System.out.println(arr);
        System.out.println(arr.countToString());
        arr = new Array(5, 7, 10, 4, 25, 30, 4, 1, 2, 4, 4);
        arr.sortSelect();
        System.out.println("SortSelect");
        System.out.println("Сложность O(n^2) " + arr.length() * arr.length());
        System.out.println(arr);
        System.out.println(arr.countToString());
    }
}