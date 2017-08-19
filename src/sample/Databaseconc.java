/**
 * Created by Sivasubramanian on 8/17/2017.
 */
package sample;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Databaseconc {
    Connection conn = null;

    public static Connection DBconnector() {
        try {
            Connection conn = null;
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:C:/Users/Sivasubramanian/IdeaProjects/trailnew/src/sample/Calcidb.db";

            conn = DriverManager.getConnection(url);
            CheckConnection(conn);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void CheckConnection(Connection conn){
        if (conn==null){
            System.out.println("Connection is not Successful");
        }
        else{
            System.out.println("Connection is Successful");
        }
    }
}
