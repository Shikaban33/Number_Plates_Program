//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import numberPlateClass.*;
public class DatabaseOp {
    public DatabaseOp() {
    }
    public Connection databaseLink;
    public Connection connectToDb() {
        Connection connection = null;
        String DB_URL = "jdbc:sqlserver://MSI\\TEW_SQLEXPRESS;databaseName=NumeriaiV2;user=Shikabane;password=1234;encrypt=true;trustServerCertificate=true;";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            databaseLink = DriverManager.getConnection(DB_URL);
            System.out.println("Connected successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return databaseLink;
    }

    public static void disconnectFromDb(Connection connection, Statement statement) throws SQLException {
        try {
            if (connection != null) {
                connection.close();
            }

            if (statement != null) {
                statement.close();
            }
            System.out.println("Disconnected successfully.");
        } catch (SQLException var3) {
        }

    }
}
