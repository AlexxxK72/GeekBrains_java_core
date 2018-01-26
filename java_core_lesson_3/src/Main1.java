import java.util.ArrayList;
import java.util.HashMap;

public class Main1 {
    public static void main(String[] args) {
      ArrayList list = createList();
      HashMap<String, Integer> result = distinctList(list);
      System.out.println(result);
    }

    private static ArrayList<String> createList(){
        ArrayList<String> list =  new ArrayList<>();
        list.add("мама");
        list.add("папа");
        list.add("дом");
        list.add("река");
        list.add("озеро");
        list.add("лес");
        list.add("мама");
        list.add("папа");
        list.add("папа");
        list.add("лес");
        list.add("мама");
        list.add("озеро");
        list.add("мама");
        list.add("дом");
        list.add("папа");
        list.add("лес");
        list.add("мама");
        list.add("дом");
        list.add("дом");
        list.add("озеро");

        return list;
    }

    private static HashMap<String, Integer> distinctList(ArrayList<String> list){
        HashMap<String, Integer> result = new HashMap<>();
        for (String key: list) {
            if(!result.containsKey(key))result.put(key, 1);
            else result.put(key, result.get(key) + 1);
        }
        return result;
    }
}

