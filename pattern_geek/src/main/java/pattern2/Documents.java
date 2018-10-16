package pattern2;

import java.util.ArrayList;
import java.util.List;

public class Documents {

    private List<Document> list;
    private static Documents ourInstance = null;

    public static Documents getInstance() {
        if(ourInstance == null) ourInstance = new Documents();
        return ourInstance;
    }

    private Documents() {
       list = getDocuments();
    }

    private List<Document> getDocuments(){
        //FIXME
        //get a list of documents from DB
         return new ArrayList<Document>();
    }

    public List<Document> getList() {
        return list;
    }
}
