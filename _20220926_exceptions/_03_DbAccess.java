import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class _03_DbAccess {
    public static void main(String[] args) {

        DbWork db = new DbWork();
        try {
            db.connect();
        } catch (DatabaseNotAvailableException e) {
            e.printStackTrace();
        }
        
	}
}

class DbWork {
    Connection conn = null;
    Statement stmt = null;

    public void connect() throws DatabaseNotAvailableException {
        boolean connected = false;
        final int tries = 50;
        int counter = 0;

        while (!connected && counter < tries)

        {
            try {
                conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/w3c", "root", "123");
                System.out.println("Connection is created successfully:");
                connected = true;
            } catch (SQLException e) {
                System.out.println("Database connection not succesful. Trying again in 1 Second.");
                //e.printStackTrace();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            counter++;
        }
        if(!(counter < tries)){
            throw new DatabaseNotAvailableException(tries);
        }
    }
}

class DatabaseNotAvailableException extends Exception {
    private static final long serialVersionUID = 1L;

    DatabaseNotAvailableException(int tries) {
        super(String.format("Stopped connecting after %d tries %n",tries));
    }
}
