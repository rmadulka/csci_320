package services;

import application.DatabaseConnection;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class AddToolToCategoryService {
    public AddToolToCategoryService(){

    }
    public static void addToolToCategory(String categoryId, String barcode) throws SQLException {
        Connection conn = null;
        Session session = null;
        try {
//            session = DatabaseConnection.createSession();
//            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
//            conn = DatabaseConnection.createConnection(assigned_port);

            String url = "jdbc:postgresql://localhost:"+ "5432" + "/" + "postgres";
            System.out.println("database Url: " + url);
            Properties props = new Properties();
            props.put("user", "postgres");
            props.put("password", "password");
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, props);
            System.out.println("Database connection established");

            System.out.println("Port Forwarded");

            String query="INSERT INTO tool_app.category_tool(category_id, tool_barcode) VALUES (?, ?);";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(categoryId));
            statement.setString(2, barcode);

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