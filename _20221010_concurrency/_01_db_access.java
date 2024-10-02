
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class _01_db_access {

    public static void main(String[] args) {
        try {
            DbWork.connect();
        } catch (DatabaseNotAvailableException e) {
            e.printStackTrace();
        }
    }

}

class DbWork {

    static Connection conn = null;
    static Statement stmt = null;

    public static void connect() throws DatabaseNotAvailableException {
        boolean connected = false;
        final int tries = 5;
        int counter = 0;
        String database = "w5c";

        while (!connected && counter < tries) {

            try {
                // TODO Erweitere an dieser Stelle den Code, sodass die Datenbank weg gelassen
                // wird (w3c)
                // TODO Statdessen wird in einem zweiten Schritt mit SQL "use w3c;" geprüft, ob
                // sie vorhanden ist
                // Auf diese Weise kann man unterscheiden, ob der Server noch nicht gestartet
                // ist oder die Datenbank nicht erstellt wurde
                // Existiert nur die Datenbank nicht, so erstelle sie mit einem weiteren SQL
                // Befehl
                /**
                 * Tipp: Holt euch mit createStatement() ein Statement aus dem Connection Objekt
                 * dort gibt es dann die Methode executeUpdate(String sql), um SQL auszuführen
                 */
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

                Statement statement = conn.createStatement();
                String sql = "USE " + database + ";";
                statement.executeUpdate(sql);
                System.out.println("Connection created!");
                connected = true;
            } catch (SQLException e) {
                if (e.getErrorCode() == 1049) {
                    // Database already exists error
                    System.out.println(e.getMessage());
                    Statement statement;
                    try {
                        statement = conn.createStatement();
                        String sql = "CREATE DATABASE " + database + ";";
                        statement.executeUpdate(sql);
                        System.out.println("Database created!");
                        String sql1 = "USE " + database + ";";
                        statement.executeUpdate(sql1);
                        System.out.println("Connection created!");
                        connected = true;
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                } else {
                    // Some other problems, e.g. Server down, no permission, etc
                    System.out.println("Database connection not possible. Trying again....");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }

            }

            // catch (SQLTimeoutException e){
            // System.out.println("Timeout!");
            // }
            counter++;
        }

        if (counter >= tries)
            throw new DatabaseNotAvailableException(tries);
    }

}

class DatabaseNotAvailableException extends Exception {
    DatabaseNotAvailableException(int tries) {
        super(String.format("Stopped connecting after %d tries %n", tries));
    }
}