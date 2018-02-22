import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static void main ( String args []) {
        TreeSet<TreeSetTest> ts = new TreeSet<>();
        HashMap< String , String > hm = new HashMap <>();
        hm . put ( "Russia" , "Moscow" );
        hm . put ( "France" , "Paris" );
        hm . put ( "Germany" , "Berlin" );
        hm . put ( "Norway" , "Oslo" );
        Set<Map.Entry<String, String>> set = hm.entrySet ();
        for ( Map . Entry < String , String > o : set ) {
            System . out . print ( o . getKey () + ": " );
            System . out . println ( o . getValue ());
        }
        hm . put ( "Germany" , "Berlin2" );
        System . out . println ( "New Germany Entry: " + hm . get ( "Germany" ));

        //hm.get("Germany")
    }
}
