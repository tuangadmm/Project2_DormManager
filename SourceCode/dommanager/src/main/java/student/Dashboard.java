package student;

import Main.StudentController;
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
public class Dashboard {

    //fields
    String name, electricity, water, total, payment, noftify;

    //constructor
    public Dashboard() {

    }

    public Dashboard(String name, String electricity, String water, String total, String payment, String noftify) {
        this.name = name;
        this.electricity = electricity;
        this.water = water;
        this.total = total;
        this.payment = payment;
        this.noftify = noftify;
    }

    //getter and setter
    public String getName() {
        return name;
    }

    public String getElectricity() {
        return electricity;
    }

    public String getWater() {
        return water;
    }

    public String getTotal() {
        return total;
    }

    public String getPayment() {
        return payment;
    }

    public String getNoftify() {
        return noftify;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void setNoftify(String noftify) {
        this.noftify = noftify;
    }

    //methods
    //load dashboard information
    public void loadDashboard() {
        try {
            Connection conn = Connect_db.getConnection();

            //bill information
            PreparedStatement stmt = conn.prepareStatement(QueryStudent.getBillInfo);
            stmt.setInt(1, Common.getRoomIdByEmail(StudentController.email));
            ResultSet rs = stmt.executeQuery();
            rs.next();
            setElectricity(String.valueOf(rs.getInt("electricity")));
            setWater(String.valueOf(rs.getInt("water")));
            setTotal(String.valueOf(rs.getInt("water") + rs.getInt("electricity")));
            setPayment(rs.getString("payment_status"));

        } catch (SQLException e) {
            setElectricity(String.valueOf(0));
            setWater(String.valueOf(0));
            setTotal(String.valueOf(0));
            setPayment("No bill available");
        }
        try {
            Connection conn = Connect_db.getConnection();

            //announcement information
            PreparedStatement stmt = conn.prepareStatement(QueryStudent.getAnnInfo);
            stmt.setInt(1, Common.getRoomIdByEmail(StudentController.email));
            ResultSet rs = stmt.executeQuery();
            rs.next();
            setNoftify("New from " + rs.getString("role") + ": " + rs.getString("user_detail.full_name") + ": " + rs.getString("subject"));

        } catch (SQLException e) {
            setNoftify("No new notification");
        }
    }

}
