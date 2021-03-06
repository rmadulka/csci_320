package services;

import application.DatabaseConnection;
import com.jcraft.jsch.Session;

import java.sql.*;
;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReturnService {

    public static void returnTool(String requestID, String username) throws SQLException {
        Connection conn = null;
        Session session = null;
        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);

            String query = "select * from request where request_id = (?) AND username = (?) AND status = 'Accepted'";

            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(requestID));
            statement.setString(2, username);


            ResultSet result = statement.executeQuery();

            if(!result.next()){
                System.out.println("User cannot return this request");
            }
            else{
                String query2 = "update request set date_returned = (?), status = (?) " +
                        "where request_id = (?)";

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime now = LocalDateTime.now();
                String currDate = dtf.format(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.sql.Date sql_date_needed_returned = new java.sql.Date(sdf.parse(currDate).getTime());

                PreparedStatement statement2 = conn.prepareStatement(query2);
                statement2.setDate(1, sql_date_needed_returned);
                statement2.setString(2, "Returned");
                statement2.setInt(3, Integer.parseInt(requestID));
                boolean result2 = statement2.execute();

                String query3 = "update tool set shareable = true from request where request.tool_barcode = tool.barcode AND request.request_id = (?)";
                PreparedStatement statement3 = conn.prepareStatement(query3);
                statement3.setInt(1, Integer.parseInt(requestID));
                boolean result3 = statement3.execute();

                System.out.println("Tool has been returned");

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
