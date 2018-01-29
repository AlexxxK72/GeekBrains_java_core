package phoneBook;

import java.util.ArrayList;
import java.util.HashMap;

public class PhoneBook {
    private static HashMap<Integer, String> listUser;
    private static HashMap<String, Integer> listPhone;

    public PhoneBook() {
        if(listUser == null) listUser = new HashMap<>();
        if(listPhone == null) listPhone = new HashMap<>();
    }

    public void add(String sName, String phone){
        int userId = getUserId(sName);
        listUser.put(userId, sName);
        listPhone.put(phone, userId);
    }

    public ArrayList<String> getPhones(String sName){
        ArrayList<String> lPhones = new ArrayList<>();
        int userId = getUserId(sName);
        listPhone.forEach((k, v) -> {
            if(v == userId) lPhones.add(k);
        });
        return lPhones;
    }

    private int getUserId(String sName){
        int nId = 0;
        for (int i = 0; i < listUser.size(); i++){
            if(listUser.get(i).equals(sName)) return i;
            nId = i + 1;
        }
        return nId;
    }
}
