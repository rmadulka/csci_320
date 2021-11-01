package services;

import application.DatabaseConnection;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoveToolFromCategoryService {
    public RemoveToolFromCategoryService(){

    }
    public static void removeToolFromCategory(String categoryId, String barcode) throws SQLException {
        Connection conn = null;
        Session session = null;
        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);
            System.out.println("Port Forwarded");

            String query = "delete from tool_app.category_tool where category_id = ? AND tool_barcode = ? ;";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(categoryId));
            statement.setString(2, barcode);

            int result = statement.executeUpdate();

            if (result > 1) {
                System.out.println("Successfully removed tool from category");
            } else {
                System.out.println("Tool was not in the specified category");
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