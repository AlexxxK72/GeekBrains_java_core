package lesson2;

public class Array {
    private int[] arr;
    private int size;
    private int count;
    private boolean isSorted;

    private Array() {
        isSorted = false;
        count = 0;
    }

    public Array(int size) {
        this();
        this.size = 0;
        arr = new int[size];
    }

    public Array(int... args) { // int[] args = new int[args.length];
        this();
        size = args.length;
        arr = args;
    }

    public boolean isSorted() {
        return isSorted;
    }

    public int length() {
        return size;
    }

    public int get(int index) {
        if (index < 0 || index >= size)
            throw new ArrayIndexOutOfBoundsException("WTF!");
        return arr[index];
    }

    public void set(int index, int value) {
        if (index < 0 || index >= size)
            throw new ArrayIndexOutOfBoundsException("WTF!");
        arr[index] = value;
    }

    public void append(int value) {
        if (size >= arr.length - 1) {
            int[] temp = arr;
            arr = new int[size * 2];
            System.arraycopy(temp, 0, arr, 0, size);
        }
        arr[size++] = value;
    }

    public boolean remove() {
        if (size == 0)
            return false;
        size--;
        return true;
    }

    // homework
    //*************************************************************************
    public boolean delete(int index) {
        int iMax = size - 1;
        if (iMax < index || index < 0)
            return false;
        while (index < iMax){
            swap(index, ++index);
        }
        size--;
        return true;
    }

    public boolean deleteAll(int value) {
        boolean isValue = false;
        for (int i = 0; i < size ; i++) {
           if(arr[i] == value) {
               isValue = true;
               delete(i--);
           }
        }
        return isValue;
    }

    public boolean deleteAll() {
        size = 0;
        return true;
    }
    //*************************************************************************

    public boolean isInArray(int value) {
        for (int i = 0; i < size; i++) {
            if (arr[i] == value) {
                return true;
            }
        }
        return false;
    }
    // k << n == k * 2 ^ n
// k >> n == k / 2 ^ n
    public int find(int value) {
        if (!isSorted)
            throw new RuntimeException("Trying to search in unsorted array");
        int l = 0;
        int r = size;
        int m;
        while (l < r) {
            m = (l + r) >> 1; // 8 = 00001000 >> 2 = 00000010 = 2
            if (value == arr[m]) {
                return m;
            } else {
                if (value < arr[m]) {
                    r = m;
                } else {
                    l = m + 1;
                }
            }
        }
        return -1;
    }

    private void swap(int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public void sortBubble() {
        count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1; j++) {
                count++;
                if (arr[j] > arr[j + 1])
                    swap(j, j + 1);

            }
        }
        isSorted = true;
    }

    public void sortFineBubble() {
        count = 0;
        for (int i = size - 1; i >= 1; i--){
            for (int j = 0; j < i; j++){
                count++;
                if(arr[j] > arr[j + 1])
                    swap(j, j + 1);
            }
        }
        isSorted = true;
    }

    public void sortSelect() {
        int f;
        count = 0;
        for (int i = 0; i < size; i++) {
            f = i;
            for (int j = i + 1; j < size; j++) {
                count++;
                if (arr[j] < arr[f])
                    f = j;
            }
            swap(i, f);
        }
        isSorted = true;
    }

    public void sortInsert() {
        count = 0;
        for (int i = 1; i < size; i++) {
            int temp = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] >= temp) {
                count++;
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = temp;
        }
        isSorted = true;
    }

    public String countToString() {
        return "Кол-во итераций: " + count;
    }

    @Override
    public String toString() {
        int iMax = size - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(arr[i]);
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
    }
}
