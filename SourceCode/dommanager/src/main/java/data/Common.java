/*
 */
package data;

import Main.SceneController;
import database.Connect_db;
import database.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author tuan
 */
public final class Common {
    
    //get manager's id by username
    public static int getManagerId() {
        int id = 0;
        try {
            Connection conn = Connect_db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(Query.getManagerId);
            stmt.setString(1, SceneController.username);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            id = rs.getInt("id");

        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getLocalizedMessage());
        }
        return id;
    }

    //get room id by  room name
    public static int getRoomId(String name) {
        String i = "select id from rooms where name = '" + name + "'";
        int id = 0;
        try {
            Connection conn = Connect_db.getConnection();
            ResultSet rs = conn.createStatement().executeQuery(i);
            rs.next();
            id = rs.getInt("id");

        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getLocalizedMessage());
        }
        return id;
    }

    //get available room name
    public static ObservableList getRooms() {
        String r = "select name from rooms where occupied < capacity";
        ObservableList<String> rooms = FXCollections.observableArrayList();
        try {
            Connection conn = Connect_db.getConnection();
            ResultSet rrs = conn.createStatement().executeQuery(r);
            while (rrs.next()) {
                rooms.add(rrs.getString("name"));

            }
        } catch (SQLException exr) {
            System.out.println("Error loading data: " + exr.getMessage());
        }
        return rooms;
    }

    //reverse a string
    private static String revString(String str) {
        String reversed = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            reversed += str.charAt(i);
        }

        return reversed;
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

    //calculate electricity used
    public static int calElectric(int consumed) {
        int total = 0, t1 = 1678, t2 = 1734, t3 = 2014, t4 = 2536, t5= 2834, t6 = 2927;
        if (consumed <= 50) {
            total = t1 * consumed;
        } else if (consumed > 50 && consumed <= 100) {
            total = 50 * t1 + (consumed - 50) * t2;
        } else if (consumed > 100 && consumed <= 200) {
            total = 50 * (t1 + t2) + (consumed - 100) * t3;
        } else if (consumed > 200 && consumed <= 300){
            total = 50 * (t1 + t2) + 100 * t3 + (consumed - 200) * t4;
        } else if (consumed > 300 && consumed <= 400){
            total = 50 * (t1 + t2) + 100 * (t3 + t4) + (consumed - 300) * t5;
        } else {
            total = 50 * (t1 + t2) + 100 * (t3 + t4 + t5) + (consumed - 400) * t6;
        }
        return total;

    }

    //calculate wated used
    public static int calWater(int consumed) {
        int total = 0, t1 = 6869, t2 = 8110, t3 = 9969, t4 = 18318;
        if (consumed <= 10) {
            total = t1 * consumed;
        } else if (consumed > 10 && consumed <= 20) {
            total = 10 * t1 + (consumed - 10) * t2;
        } else if (consumed > 20 && consumed <= 30) {
            total = 10 * (t1 + t2) + (consumed - 20) * t3;
        } else {
            total = 10 * (t1 + t2 + t3) + (consumed - 30) * t4;
        }
        return total;
    }

}
