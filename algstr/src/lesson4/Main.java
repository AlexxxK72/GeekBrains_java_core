package lesson4;

public class Main {
    private static int linear(int[] arr, int size) {
        if (size > 0)
            return Math.min(arr[size - 1], linear(arr, size - 1));
        else
            return arr[size];
    }

    public static void main(String[] args) {
        int[] arr = {1,2,7,6,5,4,3,2,-5,3,4,5,6};
        System.out.println(linear(arr, arr.length));

        DoubleRelatedList drl = new DoubleRelatedList();
        System.out.println(drl);
        drl.push(new Cat(1, "cat1"));
        drl.push(new Cat(1, "cat2"));
        drl.push(new Cat(1, "cat3"));
        System.out.println(drl);
        drl.pop();
        System.out.println(drl);
        drl.pop();
        System.out.println(drl);
        drl.deleteCurrent();
        System.out.println(drl);
        drl.push(new Cat(1, "cat4"));
        System.out.println(drl);
        drl.delete(new Cat(1, "cat4"));
        System.out.println(drl);
    }
}
