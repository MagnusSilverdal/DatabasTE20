import javax.swing.*;
import java.sql.*;

public class DatabaseExample {
    public static void main(String[] args) {
        Connection conn = null;
        String user = "magnus";
        JPasswordField pf = new JPasswordField();
        JOptionPane.showConfirmDialog(null, pf, "password?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        String password = new String(pf.getPassword());

        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bookshop? "+
                    "allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            Statement stmt = conn.createStatement();
            String SQLQuery = "SELECT * FROM book";
            ResultSet result = stmt.executeQuery(SQLQuery);

            ResultSetMetaData metadata = result.getMetaData();

            int numCols = metadata.getColumnCount();
            for (int i = 1 ; i <= numCols ; i++) {
                System.out.println(metadata.getColumnClassName(i));
            }

            while (result.next()) {
                String output = "";
                output += result.getInt("id") + ", " +
                        result.getString("title") + ", " +
                        result.getString("author") + ", " +
                        result.getDouble("price") + ", " +
                        result.getInt("quantity");
                System.out.println(output);
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
