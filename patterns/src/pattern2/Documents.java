package pattern2;

public class Documents {

    private static Documents ourInstance = new Documents();

    public static Documents getInstance() {
        return ourInstance;
    }

    private Documents() {
    }
}
