
import java.sql.*;



public class DbHelper {
    private static DbHelper ourInstance = new DbHelper();

    private Connection connection;
    private Statement statement;
    private PreparedStatement ps;

    public static DbHelper getInstance() {
        return ourInstance;
    }

    private DbHelper(){
        try {
            connect();
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createTable() throws SQLException {
        statement.execute(
                "CREATE TABLE IF NOT EXISTS users(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "login TEXT," +
                        "pass  TEXT," +
                        "directory TEXT);");
    }

    public Boolean checkUser(String login, String pass) throws SQLException {
        ps = connection.prepareStatement("SELECT * FROM  users WHERE login = ? AND pass = ?;");
        ps.setString(1, login);
        ps.setString(2, pass);
        ResultSet resultSet = ps.executeQuery();
        return resultSet.next();
    }

    public String getPathCloud(String login, String pass) throws SQLException {
        ps = connection.prepareStatement("SELECT * FROM  users WHERE login = ? AND pass = ?;");
        ps.setString(1, login);
        ps.setString(2, pass);
        ResultSet resultSet = ps.executeQuery();
        if(resultSet.next()) return resultSet.getString("directory");
        return null;
    }

    private void connect() throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:db_user_cloud.db");
        statement = connection.createStatement();
    }

    private void disconnect(){
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        disconnect();
    }
}
