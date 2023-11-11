/*
 */
package data;

import database.Connect_db;
import database.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * dashboard data and manager data
 */
public class Bill {

    //field
    String error;
    public Bill() {
    }
    
    private SimpleStringProperty room, paymentStatus, month;
    private SimpleIntegerProperty billId, water, electricity, total;
    
    //constructor for bill list
    public Bill(String room, String paymentStatus, String month, int billId, int water, int electricity, int total) {
        this.room = new SimpleStringProperty(room);
        this.paymentStatus = new SimpleStringProperty(paymentStatus);
        this.month = new SimpleStringProperty(month);
        this.billId = new SimpleIntegerProperty(billId);
        this.water = new SimpleIntegerProperty(water);
        this.electricity = new SimpleIntegerProperty(electricity);
        this.total = new SimpleIntegerProperty(total);
    }
    //getter, setter for bill list

    public String getMonth() {
        return month.get();
    }
    public String getRoom() {
        return room.get();
    }
    public String getPaymentStatus() {
        return paymentStatus.get();
    }
    public int getBillId() {
        return billId.get();
    }
    public int getWater() {
        return water.get();
    }
    public int getElectricity() {
        return electricity.get();
    }
    public int getTotal() {
        return total.get();
    }

    public void setMonth(String month) {
        this.month = new SimpleStringProperty(month);
    }
    public void setRoom(String room) {
        this.room = new SimpleStringProperty(room);
    }
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = new SimpleStringProperty(paymentStatus);
    }
    public void setBillId(int billId) {
        this.billId =  new SimpleIntegerProperty(billId);
    }
    public void setWater(int water) {
        this.water =  new SimpleIntegerProperty(water);
    }
    public void setElectricity(int electricity) {
        this.electricity =  new SimpleIntegerProperty(electricity);
    }
    public void setTotal(int total) {
        this.total =  new SimpleIntegerProperty(total);
    }
    
    //getter

    public String getError() {
        return error;
    }

    //setter
    public void setError(String error) {
        this.error = error;
    }

    //method
    //load table data
    public ObservableList<Bill> loadBill(String month) {
        ObservableList<Bill> data = FXCollections.observableArrayList();
        try {
            Connection conn = Connect_db.getConnection();
            //ResultSet
            ResultSet b = conn.createStatement().executeQuery(Query.getBill(month));

            //add data
            while (b.next()) {
                //Iterate Row
                data.add(
                        new Bill(b.getString("name"), 
                            b.getString("payment_status"),
                            b.getString("time"),
                            b.getInt("bills.id"),
                            b.getInt("water"),
                            b.getInt("electricity"),
                            b.getInt("water") + b.getInt("electricity")
                        ));
            }
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return data;
    }

    //add bill
    public boolean add(String room, String month, String water, String electricity) {
        int isok = 1;
        Validator v = new Validator();
        if (v.moneyCheck(water) == false || v.moneyCheck(electricity) == false) {
            isok = 0;
            setError("Please enter a valid amount");
            return false;
        }

        //add bill
        if (isok == 1) {
            try {
                Connection conn = Connect_db.getConnection();
                PreparedStatement stmt = conn.prepareStatement(Query.insertBill);
                stmt.setInt(1, Common.calWater(Integer.parseInt(water)));
                stmt.setInt(2, Common.calElectric(Integer.parseInt(electricity)));
                stmt.setString(3, month);
                stmt.setInt(4, Common.getRoomId(room));
                stmt.execute();
                return true;

            } catch (SQLException be) {
                System.out.println("Error inserting data: " + be.getMessage());
            }
        }
        return false;
    }

    //get room list
    public ObservableList getRooms() {
        ObservableList<String> rooms = FXCollections.observableArrayList();
        try {
            Connection conn = Connect_db.getConnection();
            ResultSet rrs = conn.createStatement().executeQuery(Query.getAllRoom);
            while (rrs.next()) {
                rooms.add(rrs.getString("name"));
            }
        } catch (SQLException exr) {
            System.out.println("Error loading data: " + exr.getMessage());
        }
        return rooms;
    }

    //get bill id
    public int getId() {
        String b = "select id from bills order by id desc limit 1";
        int id;
        try {
            Connection conn = Connect_db.getConnection();
            ResultSet brs = conn.createStatement().executeQuery(b);
            brs.next();
            id = brs.getInt("id") + 1;
        } catch (SQLException e) {
            id = 1;
        }
        return id;
    }

}
