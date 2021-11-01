package services;

import application.DatabaseConnection;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateCategoryNameService {
    public UpdateCategoryNameService(){

    }public static void updateCategoryName(String categoryName, String categoryID) throws SQLException {
        Connection conn = null;
        Session session = null;
        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);
            System.out.println("Port Forwarded");

            // Do something with the database....

            String query = "Update tool_app.category\n" +
                    "Set category_name = (?) " +
                    "Where category_id = (?)";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, categoryName);
            statement.setString(2, categoryID);

            int result = statement.executeUpdate();

            if (result > 1 ) {
                System.out.println("Successfully added category");
            } else {
                System.out.println("Category name was not updated");
            }


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