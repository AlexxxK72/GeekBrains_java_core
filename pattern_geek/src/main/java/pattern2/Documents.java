package pattern2;

import java.util.ArrayList;
import java.util.List;

public class Documents {

    private static List<Document> list;
    private static Documents ourInstance = new Documents();

    public static Documents getInstance() {
        return ourInstance;
    }

    private Documents() {
       if(list == null) list = new ArrayList<Document>();
    }
}
