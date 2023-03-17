package DatabaseModel;

import javax.swing.*;
import java.sql.*;
import java.util.Scanner;

/**
 * This is a class
 * Created 2022-04-04
 *
 * @author Magnus Silverdal
 */
public class DatabaseConnector {
    Connection conn;
    Statement stmt;

    public DatabaseConnector() {

        try {
            // Set up connection to database
            conn = DriverManager.getConnection(
                    "jdbc:mysql://" + DatabaseLoginData.DBURL + ":" + DatabaseLoginData.port + "/" + DatabaseLoginData.DBname +
                            "? allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                    DatabaseLoginData.user, DatabaseLoginData.password);
            // Setup statement
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to log in, check your database and credentials and try again. Shutting down...");
            System.exit(0);
        }
    }

    public String getDatabaseContent() {
        String result = "";
        try {
            // Create query and execute
            String SQLQuery = "select * from book";
            ResultSet rset = stmt.executeQuery(SQLQuery);

            // Loop through the result set and convert to String
            // Need to know the table-structure
            while (rset.next()) {
                result += rset.getInt("id") + ", " +
                        rset.getString("title") + ", " +
                        rset.getString("author") + ", " +
                        rset.getDouble("price") + ", " +
                        rset.getInt("quantity") + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Something went wrong, check your tablestructure...");
            return "Error reading result from SQL-query";
        }
        return result;
    }

    public String getTableInfo() {
        String result = "";
        try {
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM book");

            // Get resultset metadata
            ResultSetMetaData metadata = results.getMetaData();
            int columnCount = metadata.getColumnCount();

            // Get the column names; column indices start from 1
            for (int i=1; i<=columnCount; i++) {
                String columnName = metadata.getColumnName(i);
                result += columnName + " ";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getBooksFromAuthor(String name) {
        String result = "";
        try {
            ResultSet results = stmt.executeQuery("SELECT title FROM book WHERE author='"+name+"'");
            while (results.next()) {
                result += results.getString("title") + "\n";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // insert
    public void insertBook(String title) {
        Scanner in = new Scanner(System.in);
        System.out.println("Ange författare:");
        String author = in.nextLine();

        String SQLQuery = "INSERT INTO book(title,author) VALUES ('" + title + "', '" + author + "')";
        try {
            stmt.executeUpdate(SQLQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Update
    public void changePriceOnBook(String title) {
        Scanner in = new Scanner(System.in);
        System.out.println("Ange pris:");
        int price = in.nextInt();
        String SQLQuery = "UPDATE book SET price = " + price +" where title = '"+title+"'";
        try {
            stmt.executeUpdate(SQLQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
