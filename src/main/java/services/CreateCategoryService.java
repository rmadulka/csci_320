package services;

import application.DatabaseConnection;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class CreateCategoryService {
    public CreateCategoryService(){

    }public static void createCategory(String categoryName, String username) throws SQLException {
        Connection conn = null;
        Session session = null;
        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);
            System.out.println("Port Forwarded");

            // Do something with the database....

            String query = "INSERT INTO tool_app.category(category_name, category_created_user) VALUES (?, ?);";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, categoryName);
            statement.setString(2, username);

            boolean result = statement.execute();

            System.out.println("Successfully added category");

        } catch (NumberFormatException nfe) {
            System.err.println("Error parsing arguments, NumberFormatException. See help for formatting");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Closing Database Connection");
                conn.close();
            }
            if (session != null && session.isConnected()) {
                System.out.println("Closing SSH Connection");
                session.disconnect();
            }
        }
    }
}