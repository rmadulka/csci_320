package services;

import application.DatabaseConnection;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecommendationService {
    public RecommendationService() {

    }
    private static String FORMAT = "%-12s|%-50s|%-10s";
    public static void recommendedTools(String username,String barcode) throws SQLException {
        Connection conn = null;
        Session session = null;


        try {
            session = DatabaseConnection.createSession();
            int assigned_port = session.setPortForwardingL(DatabaseConnection.LPORT, "localhost", DatabaseConnection.RPORT);
            conn = DatabaseConnection.createConnection(assigned_port);

            // Do something with the database....
            recommendToolsHelper(conn, barcode);


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

    public static void recommendToolsHelper(Connection conn, String barcode) throws SQLException {
        String query="select r2.tool_barcode, t.name, count(r2.tool_barcode) as num_prev_request\n" +
                "from request r1\n" +
                "join request r2 on r1.username = r2.username\n" +
                "join tool t on r2.tool_barcode = t.barcode\n" +
                "where r1.tool_barcode = (?) AND r2.tool_barcode != (?)\n" +
                "group by r2.tool_barcode, t.name\n" +
                "order by num_prev_request desc";


        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1,barcode);
        statement.setString(2,barcode);
        ResultSet result = statement.executeQuery();

        if (!result.next()) {
            System.out.println("No available tools");
        } else {
            System.out.format(FORMAT, "barcode", "name","number previous Requests");
            System.out.println();
            int count=0;
            do {
                String recommended=result.getString("tool_barcode");
                String name = result.getString("name");
                String numRequests= result.getString("num_prev_request");


                System.out.format(FORMAT, recommended, name,numRequests );
                System.out.println();
                count++;
            } while (result.next()&&count<3);
        }
    }
}
