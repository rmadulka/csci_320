package services;

import application.DatabaseConnection;
import application.LoginSession;
import com.jcraft.jsch.Session;

import java.sql.*;
import java.util.Properties;

public class LoginService {
    public LoginService() {

    }

    public static LoginSession login(String username, String password) throws SQLException {
        Connection conn = null;
        Session session = null;

        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);

            // Do something with the database....

            String query = "SELECT * FROM tool_app.\"user\" WHERE tool_app.\"user\".username = (?) AND tool_app.\"user\".password = (?);";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            if (!result.next()) {
                System.err.println("Invalid Credentials");
                return null;
            }

            String updateLastAccessDate = "UPDATE tool_app.\"user\"\n" +
                    "SET last_access_date = current_date\n" +
                    "WHERE username = (?)";

            PreparedStatement statementUpdate = conn.prepareStatement(updateLastAccessDate);
            statementUpdate.setString(1, username);
            statementUpdate.execute();


            System.out.println("Successfully logged in");

            LoginSession loginSession = new LoginSession(username);
            return loginSession;
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
