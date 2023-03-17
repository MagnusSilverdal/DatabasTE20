package DatabaseModel;

/**
 * This is a class
 * Created 2022-04-04
 *
 * @author Magnus Silverdal
 */
public class DatabaseConnectorTest {
    public static void main(String[] args) {
        DatabaseConnector dbconn = new DatabaseConnector();
        System.out.println("Tabellens struktur:");
        System.out.println(dbconn.getTableInfo());
        System.out.println("Visa hela tabellens innehåll:");
        System.out.println(dbconn.getDatabaseContent());
        System.out.println("Välj ut något speciellt");
        System.out.println(dbconn.getBooksFromAuthor("JRR Tolkien"));
        // Lägg till något till databasen
        dbconn.insertBook("Nalle Puh");
        // Ändra på något i databasen
        dbconn.changePriceOnBook("NallePuh");
    }
}
