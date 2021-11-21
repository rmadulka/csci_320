package services;

import application.DatabaseConnection;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardService {

    public static void dashboard(String usernameArg) throws SQLException {
        Connection conn = null;
        Session session = null;

        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);

            System.out.println("\n--- tools dashboard ---");
            // Do something with the database....

            // display number of available tools from the users catalog
            String availableCountQuery =
                    "select count(*) from (select * from tool where owner = (?)) as x where shareable = true";
            PreparedStatement statement = conn.prepareStatement(availableCountQuery);
            statement.setString(1, usernameArg);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                String count = result.getString(1);
                System.out.println("total available tools: " + count);
            }

            // display number of lent tools from the users catalog
            String lentCountQuery = "select count(*)\n" +
                    "from tool tool\n" +
                    "inner join request request on request.tool_barcode = tool.barcode\n" +
                    "where status = 'Accepted' and owner = (?);" ;
            statement = conn.prepareStatement(lentCountQuery);
            statement.setString(1, usernameArg);
            result = statement.executeQuery();
            if(result.next()){
                String count = result.getString(1);
                System.out.println("total lent tools: " + count);
            }

            // display number of borrowed tools that the user has
            String borrowedCountQuery = "select count(*)\n" +
                    "from tool tool\n" +
                    "inner join request request on request.tool_barcode = tool.barcode\n" +
                    "where status = 'Accepted' and username = (?);";
            statement = conn.prepareStatement(borrowedCountQuery);
            statement.setString(1, usernameArg);
            result = statement.executeQuery();
            if(result.next()){
                String count = result.getString(1);
                System.out.println("total borrowed tools: " + count);
            }

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
