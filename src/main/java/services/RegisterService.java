package services;

import application.DatabaseConnection;
import application.LoginSession;
import com.jcraft.jsch.Session;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class RegisterService {
    public RegisterService() {

    }

    public static LoginSession register(String username, String password, String email, String firstName, String lastName) throws SQLException {
        Connection conn = null;
        Session session = null;

        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);

            // Do something with the database....
            String query = "INSERT INTO \"user\"(username, password, email, first_name, last_name, creation_date, last_access_date)\n" +
                    "VALUES\n" +
                    "    (?, ?, ?, ?, ?, current_date, current_date);";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, email);
            statement.setString(4, firstName);
            statement.setString(5, lastName);

            boolean result = statement.execute();
            System.out.println("Registration Successful");

            return new LoginSession(username);

        } catch (PSQLException psqle) {
            System.out.println(Objects.requireNonNull(psqle.getServerErrorMessage()).getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }

        return null;
    }
}
