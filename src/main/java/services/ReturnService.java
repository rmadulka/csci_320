package services;

import application.DatabaseConnection;
import com.jcraft.jsch.Session;

import java.sql.*;
;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReturnService {

    public static void returnTool(String username, String barcode) throws SQLException {
        Connection conn = null;
        Session session = null;
        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);
            System.out.println("Port Forwarded");

            String query = "select * from tool_app.request where username = (?) and tool_barcode =(?)";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, barcode);

            ResultSet result = statement.executeQuery();

            if(!result.next()){
                System.out.println("User did not borrow this item");
            }
            else{
                String query2 = "update tool_app.request set date_returned = (?), status = (?) " +
                        "where username = (?) and tool_barcode  = (?)";

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime now = LocalDateTime.now();
                String currDate = dtf.format(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.sql.Date sql_date_needed_returned = new java.sql.Date(sdf.parse(currDate).getTime());

                PreparedStatement statement2 = conn.prepareStatement(query2);
                statement2.setDate(1, sql_date_needed_returned);
                statement2.setString(2, "Returned");
                statement2.setString(3, username);
                statement2.setString(4, barcode);
                boolean result2 = statement2.execute();

                String query3 = "update tool_app.tool set shareable = true where barcode = (?)";
                PreparedStatement statement3 = conn.prepareStatement(query3);
                statement3.setString(1, barcode);
                boolean result3 = statement3.execute();

                System.out.println("Tool has been returned");

            }

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
