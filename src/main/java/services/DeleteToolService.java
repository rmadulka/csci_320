package services;

import application.DatabaseConnection;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class DeleteToolService {

    public DeleteToolService() {

    }

    public static void deleteTool(String barcode, String username) throws SQLException {
        Connection conn = null;
        Session session = null;

        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);

            // Do something with the database....

            String query = "delete from tool_app.tool where barcode = ? AND owner = ? AND shareable = TRUE;";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, barcode);
            statement.setString(2, username);

            int result = statement.executeUpdate();

            if (result > 0) {
                System.out.println("Successfully deleted tool " + barcode);
            } else {
                System.out.println("Deletion unsuccessful, make sure you are the owner of the tool and that the tool is not being borrowed");
            }



        } catch (NumberFormatException nfe) {
            System.err.println("Error parsing arguments, NumberFormatException. See help for formatting");
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
    }
}
