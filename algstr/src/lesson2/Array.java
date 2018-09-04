package lesson2;

public class Array {
    private int arr[];
    private int size;
    private boolean isSorted;

    private Array() {
        isSorted = false;
    }

    public Array(int size) {
        this();
        this.size = 0;
        this.arr = new int[size];
    }

    public Array(int... args) {
        this();
        this.size = args.length;
        this.arr = args;
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

    boolean remove(int value) {
        int index = linearFind(value);
        return index != -1 && delete(index);
    }

    boolean delete(int index) { // by index
        if (index >= size || index < 0)
            throw new ArrayIndexOutOfBoundsException("" + index);
        System.arraycopy(arr, index + 1, arr, index, size - index - 1);
        size--;
        return true;
    }

//    boolean deleteAll(int value) { // by value
//        boolean success = false;   //O(N^2)
//        while (remove(value)) {
//            success = true;
//        }
//        return success;
//    }

    boolean deleteAll(int value) {
        boolean success = false;
        for (int i = 0; i < size; i++) {
            if (arr[i] == value) {
                delete(i);
                i--;
                success = true;
            }
        }
        return success;
    }

    void deleteAll() { //clear array
        size = 0;
    }

    public int linearFind(int value) {
        for (int i = 0; i < size; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;
    }

    //mid = low + ((value - arr[low]) * (high - low)) / (arr[high] - arr[low]);
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
        boolean flag = false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(j, j + 1);
                    flag = true;
                }
            }
            if (!flag) break;
        }
        isSorted = true;
    }

    public void sortSelect() {
        int f;
        for (int i = 0; i < size; i++) {
            f = i;
            for (int j = i + 1; j < size; j++) {
                if (arr[j] < arr[f])
                    f = j;
            }
            swap(i, f);
        }
        isSorted = true;
    }

    public void sortInsert() {
        for (int i = 1; i < size; i++) {
            int temp = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] >= temp) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = temp;
        }
        isSorted = true;
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

    int getMax() {
        if (size == 0) throw new RuntimeException("Empty array");
        if (size == 1) return arr[0];
        int r = arr[0];
        for (int i = 1; i < size; i++) {
            if (r < arr[i])
                r = arr[i];
        }
        return r;
    }

    int getMin() {
        if (size == 0) throw new RuntimeException("Empty array");
        if (size == 1) return arr[0];
        int r = arr[0];
        for (int i = 1; i < size; i++) {
            if (r > arr[i])
                r = arr[i];
        }
        return r;
    }

    void pigeon() {
        int min = getMin();
        int max = getMax();
        int[] freq = new int[max - min + 1];
        for (int i = 0; i < size; i++)
            freq[arr[i] - min]++;

        int arrIndex = 0;
        for (int i = 0; i < freq.length; i++)
            for (int elems = freq[i]; elems > 0; elems--)
                arr[arrIndex++] = i + min;
    }
}
