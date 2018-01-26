package phoneBook;

import java.util.ArrayList;
import java.util.HashSet;

public class PhoneBook {
    private static HashSet<PhoneRecord> listPhone;

    public PhoneBook() {
        if(listPhone == null) listPhone = new HashSet<>();
    }

    public boolean add(PhoneRecord record){
        return listPhone.add(record);
    }

    public ArrayList<String> getPhones(String sName){
        ArrayList<String> lPhones = new ArrayList<String>();
        for (PhoneRecord record: listPhone) {
            if(record.getsName() == sName) lPhones.add(record.getPhone());
        }
        return lPhones;
    }
}
