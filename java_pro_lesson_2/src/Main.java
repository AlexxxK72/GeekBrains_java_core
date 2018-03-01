import java.sql.*;
import java.util.Scanner;

public class Main implements iCommands{
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement ps;
    private static Scanner scanner;

    public static void main(String[] args) {
        try {
            scanner = new Scanner(System.in);
            connect();
            createTable();
            clearTable();
            fillTable();

            //ввести команды
            // /price product545
            // /changeprice product10 10000
            // /productsprice 100 600

            getPrice("product545");
            changePrice("product10", 10000);
            getProducts(100, 600);
            getPrice("product10");

            while (true){
                if(scanner.hasNext()) {
                    String text = scanner.nextLine();
                    if(text.startsWith(SYSTEM)){
                        String cmd = text.split(" ")[0];
                        if(cmd.equalsIgnoreCase(END))
                            break;
                        if (cmd.equalsIgnoreCase(PRICE)) {
                            try {
                                getPrice(text.substring(cmd.length() + 1));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (cmd.equalsIgnoreCase(CHANGE_PRICE))
                        {
                            String[] items = text.split(" ");
                            if(items.length == 3){
                                String title = items[1];
                                Double nPrice = Double.valueOf(items[2]);
                                try {
                                    changePrice(title, nPrice);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if(cmd.equalsIgnoreCase(PRODUCTS_PRICE)){
                            String[] items = text.split(" ");
                            if(items.length == 3){
                                String title = items[1];
                                Double minPrice = Double.valueOf(items[1]);
                                Double maxPrice = Double.valueOf(items[2]);
                                try {
                                    getProducts(minPrice, maxPrice);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    else System.out.println("This command does not exist");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
    }

    public static void getPrice(String title) throws SQLException{
        ps = connection.prepareStatement("SELECT cost FROM products WHERE title = ?");
        ps.setString(1, title);
        ResultSet resultSet = ps.executeQuery();
        if(resultSet.next()) System.out.println("Price " + title + ": " + resultSet.getDouble(1));
        else System.out.println(title + " not found!");
    }

    public static void changePrice(String title, double nPrice) throws SQLException {
        ps = connection.prepareStatement("UPDATE products SET cost = ? WHERE title = ?;");
        ps.setDouble(1, nPrice);
        ps.setString(2, title);
        ps.executeUpdate();
    }

    public static void getProducts(double minPrice, double maxPrice) throws SQLException {
        ps = connection.prepareStatement("SELECT * FROM  products WHERE cost BETWEEN ? AND ?;");
        ps.setDouble(1, minPrice);
        ps.setDouble(2, maxPrice);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next())
            System.out.println(resultSet.getInt("prodId") + "\t"
                    + resultSet.getString("title") + "\t"
                    + resultSet.getDouble("cost"));
    }


    public static void createTable() throws SQLException {
        statement.execute(
                "CREATE TABLE IF NOT EXISTS products(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "prodId INTEGER UNIQUE," +
                "title  TEXT," +
                "cost   REAL);");
    }

    public static void fillTable() throws SQLException {
        connection.setAutoCommit(false);
        ps = connection.prepareStatement("INSERT INTO products (prodId, title, cost) VALUES(?, ?, ?);");
        for (int i = 1; i <= 10000; i++) {
            ps.setInt(1, i);
            ps.setString(2, "product" + i);
            ps.setDouble(3, i*10);
            ps.executeUpdate();
        }
        connection.commit();
    }

    public static void clearTable() throws SQLException {
        statement.execute("DELETE FROM products");
    }

    public static void connect() throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:db_2.db");
        statement = connection.createStatement();
    }

    public static void disconnect(){
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void startIn(){
        new Thread(() -> {
            while (true){
                if(scanner.hasNext()) {
                    String text = scanner.nextLine();
                    if(text.startsWith(SYSTEM)){
                        String cmd = text.split(" ")[0];
                        if(cmd.equalsIgnoreCase(END))
                            break;
                        if (cmd.equalsIgnoreCase(PRICE)) {
                            try {
                                getPrice(text.substring(cmd.length() + 1));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        if (cmd.equalsIgnoreCase(CHANGE_PRICE))
                        {
                            String[] items = text.split(" ");
                            if(items.length == 3){
                                String title = items[1];
                                Double nPrice = Double.valueOf(items[2]);
                                try {
                                    changePrice(title, nPrice);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if(cmd.equalsIgnoreCase(PRODUCTS_PRICE)){
                            String[] items = text.split(" ");
                            if(items.length == 3){
                                String title = items[1];
                                Double minPrice = Double.valueOf(items[1]);
                                Double maxPrice = Double.valueOf(items[2]);
                                try {
                                    getProducts(minPrice, maxPrice);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    else System.out.println("This command does not exist");
                }
            }
        }).start();
    }
}
