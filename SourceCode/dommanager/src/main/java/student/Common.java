package student;

import database.Connect_db;
import database.QueryStudent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author tuan
 */
public class Common {

    //get room id by student's email
    public static int getRoomIdByEmail(String email) {
        try {
            Connection conn = Connect_db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(QueryStudent.getRoomIdByEmail);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("rooms.id");
        } catch (SQLException e) {
            return 0;
        }
    }

    //get student's id by email
    public static int getStudentIdByEmail(String email) {
        try {
            Connection conn = Connect_db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(QueryStudent.getStudentIdByEmail);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("id");
        } catch (SQLException e) {
            return 0;
        }
    }

    //format currency
    public static String formatCurrency(String currency) {
        String formated = "";
        currency = revString(currency);

        for (int i = 1; i < currency.length(); i++) {
            if (i % 3 == 0) {
                formated += currency.charAt(i - 1) + ".";
            } else {
                formated += currency.charAt(i - 1);
            }
        }
        formated += currency.charAt(currency.length() - 1);
        return revString(formated);
    }

    //reverse a string
    private static String revString(String str) {
        String reversed = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            reversed += str.charAt(i);
        }

        return reversed;
    }
    
    //check empty string
    public static boolean isEmpty(String s){
        return s.length() < 1;
    }

}
