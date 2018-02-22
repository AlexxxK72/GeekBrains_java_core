import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        System.out.println("******Домашнее задание п.1******");
        String[] sArray = new String[]{"ма", "па", "да"};
        printArray(sArray);
        sArray = changeElements(sArray, 0, 2);
        printArray(sArray);

        Integer[] iArray = new Integer[]{0, 1, 2, 5, 4, 6};
        printArray(iArray);
        iArray = changeElements(iArray, 3, 4);
        printArray(iArray);

        System.out.println("******Домашнее задание п.2******");
        ArrayList<Integer> iList = arrayToList(iArray);
        System.out.println(iList);

        System.out.println("******Домашнее задание п.3******");

        Box<Orange> box1 = new Box<>();
        for (int i = 0; i < 4; i++) {
            box1.add(new Orange());
        }
        System.out.println("box1 " + box1.getState());
        Box<Apple> box2 = new Box<>();
        for (int i = 0; i < 4; i++) {
            box2.add(new Apple());
        }
        System.out.println("box2 " + box2.getState());
        Box<Apple> box3 = new Box<>();
        box3.add(new Apple());
        box3.add(new Apple());
        System.out.println("box3 " + box3.getState());
        box3.pourFruit(box2);
        System.out.println("box3 " + box3.getState());
        System.out.println("box2 " + box2.getState());
        System.out.println("Сравниваем box1 и box2");
        System.out.println(box1.getWeight() +  " = " + box2.getWeight() + " " + box1.compare(box2));
        System.out.println("Сравниваем box1 и box3");
        System.out.println(box1.getWeight() +  " = " + box3.getWeight() + " " + box1.compare(box3));
        //не разобрался как из ящика типа Fruit, после того как в него что то положено пересыпать фрукты в Apple или Orange
    }


    public static <T> T[] changeElements(T[] array, int src, int dest){    //п.1 Д.з
        if(src < array.length || dest < array.length){
            T temp;
            temp = array[dest];
            array[dest] = array[src];
            array[src] = temp;
        }
        return array;
    }

    public static <T> ArrayList<T> arrayToList(T[] array){                //п.2 Д.з
        ArrayList<T> list = new ArrayList<>();
        for (T item : array) {
            list.add(item);
        }
        return list;
    }

    public static <T> void printArray(T[] array){                        // для распечатки массива
        for (T items : array) {
            System.out.print(String.valueOf(items) + ", ");
        }
        System.out.println();
    }
}


