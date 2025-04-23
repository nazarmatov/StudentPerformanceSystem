package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:postgresql://localhost:5432/university";
    private static final String USER = "postgres";
    private static final String PASSWORD = "mohmvf456129";

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
