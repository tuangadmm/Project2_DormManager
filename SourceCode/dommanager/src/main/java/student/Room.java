/*
 */
package student;

import Main.StudentController;
import java.sql.Connection;
import database.Connect_db;
import database.QueryStudent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author tuan
 */
public class Room {

    //fields
    String nameErr, emailErr, phoneErr;

    public Room() {
    }

//Student list
    SimpleIntegerProperty Id;
    SimpleStringProperty Name, Gender, Phone, Address, JoinedDate;

    public Room(int sId, String sName, String sGender, String sPhone, String sAddress, String sJoinedDate) {
        this.Id = new SimpleIntegerProperty(sId);
        this.Name = new SimpleStringProperty(sName);
        this.Gender = new SimpleStringProperty(sGender);
        this.Phone = new SimpleStringProperty(sPhone);
        this.Address = new SimpleStringProperty(sAddress);
        this.JoinedDate = new SimpleStringProperty(sJoinedDate);
    }

    //getter and setter
    public int getId() {
        return Id.get();
    }

    public String getName() {
        return Name.get();
    }

    public String getGender() {
        return Gender.get();
    }

    public String getPhone() {
        return Phone.get();
    }

    public String getAddress() {
        return Address.get();
    }

    public String getJoinedDate() {
        return JoinedDate.get();
    }

    public void setsId(int sId) {
        this.Id = new SimpleIntegerProperty(sId);
    }

    public void setsName(String sName) {
        this.Name = new SimpleStringProperty(sName);
    }

    public void setsGender(String sGender) {
        this.Gender = new SimpleStringProperty(sGender);
    }

    public void setsPhone(String sPhone) {
        this.Phone = new SimpleStringProperty(sPhone);
    }

    public void setsAddress(String sAddress) {
        this.Address = new SimpleStringProperty(sAddress);
    }

    public void setsJoinedDate(String sJoinedDate) {
        this.JoinedDate = new SimpleStringProperty(sJoinedDate);
    }

//other
    //getter
    public String getNameErr() {
        return nameErr;
    }

    public String getEmailErr() {
        return emailErr;
    }

    public String getPhoneErr() {
        return phoneErr;
    }

    //setter
    public void setNameErr(String nameErr) {
        this.nameErr = nameErr;
    }

    public void setEmailErr(String emailErr) {
        this.emailErr = emailErr;
    }

    public void setPhoneErr(String phoneErr) {
        this.phoneErr = phoneErr;
    }

    //methods

    //student list
    public ObservableList<Room> loadStudentList() {
        ObservableList data = FXCollections.observableArrayList();
        try {
            Connection conn = Connect_db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(QueryStudent.getSts);
            stmt.setInt(1, Common.getRoomIdByEmail(StudentController.email));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                data.add(
                        new Room(rs.getInt("students.id"),
                                rs.getString("full_name"),
                                rs.getString("gender"),
                                rs.getString("phone"),
                                rs.getString("address"),
                                rs.getString("joined_date")
                        ));
            }
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return data;
    }
    //get room free slot
    public String getFreeSlot(){
        try{
            Connection conn = Connect_db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(QueryStudent.getFreeSlot);
            stmt.setInt(1, Common.getRoomIdByEmail(StudentController.email));
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return String.valueOf(rs.getInt("capacity") - rs.getInt("occupied"));
        }catch(SQLException e){
            System.out.println("Error getting free slot: " + e.getMessage());
            return "0";
        }
    }


}
