/*
 */
package data;

import java.sql.Connection;
import database.Connect_db;
import database.Query;
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
public class Student {

    //fields
    String nameErr, emailErr, phoneErr;

    public Student() {
    }

//Student list
    SimpleIntegerProperty LId;
    SimpleStringProperty LRoom, LName, LGender, LPhone, LEmail, LAddress, LJoinedDate;

    public Student(int sLId, String sLRoom, String sLName, String sLGender, String sLPhone, String sLEmail, String sLAddress, String sLJoinedDate) {
        this.LId = new SimpleIntegerProperty(sLId);
        this.LRoom = new SimpleStringProperty(sLRoom);
        this.LName = new SimpleStringProperty(sLName);
        this.LGender = new SimpleStringProperty(sLGender);
        this.LPhone = new SimpleStringProperty(sLPhone);
        this.LEmail = new SimpleStringProperty(sLEmail);
        this.LAddress = new SimpleStringProperty(sLAddress);
        this.LJoinedDate = new SimpleStringProperty(sLJoinedDate);
    }

    //getter and setter
    public int getLId() {
        return LId.get();
    }

    public String getLRoom() {
        return LRoom.get();
    }

    public String getLName() {
        return LName.get();
    }

    public String getLGender() {
        return LGender.get();
    }

    public String getLPhone() {
        return LPhone.get();
    }

    public String getLEmail() {
        return LEmail.get();
    }

    public String getLAddress() {
        return LAddress.get();
    }

    public String getLJoinedDate() {
        return LJoinedDate.get();
    }

    public void setsLId(int sLId) {
        this.LId = new SimpleIntegerProperty(sLId);
    }

    public void setsLRoom(String sLRoom) {
        this.LRoom = new SimpleStringProperty(sLRoom);
    }

    public void setsLName(String sLName) {
        this.LName = new SimpleStringProperty(sLName);
    }

    public void setsLGender(String sLGender) {
        this.LGender = new SimpleStringProperty(sLGender);
    }

    public void setsLPhone(String sLPhone) {
        this.LPhone = new SimpleStringProperty(sLPhone);
    }

    public void setsLEmail(String sLEmail) {
        this.LEmail = new SimpleStringProperty(sLEmail);
    }

    public void setsLAddress(String sLAddress) {
        this.LAddress = new SimpleStringProperty(sLAddress);
    }

    public void setsLJoinedDate(String sLJoinedDate) {
        this.LJoinedDate = new SimpleStringProperty(sLJoinedDate);
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
    //get id
    public int getId() {
        int id;
        try {
            Connection conn = Connect_db.getConnection();
            ResultSet irs = conn.createStatement().executeQuery(Query.getSId);
            irs.next();
            id = irs.getInt("id") + 1;
        } catch (SQLException e) {
            id = 1;
        }
        return id;
    }

    //add student
    public boolean add(String name, String gender, int room, String email, String phone, String address) {
        //validate
        Validator v = new Validator();
        int isok = 1;
        if (v.nameCheck(name) == false) {
            isok = 0;
            setNameErr("Invalid name format");
        }
        if (v.phoneCheck(phone) == false) {
            isok = 0;
            setPhoneErr("Invalid phone format");
        }
        if (v.phoneAvailable(phone) == false) {
            isok = 0;
            setPhoneErr("This phone number is taken");
        }
        if (v.emailcheck(email) == false) {
            isok = 0;
            setEmailErr("Invalid email format");
        }
        if (v.emailAvailable(email) == false) {
            isok = 0;
            setEmailErr("This email is taken");
        }

        //insert
        if (isok == 1) {
            try {
                Connection conn = Connect_db.getConnection();
                PreparedStatement stmt = conn.prepareStatement(Query.insertSts);
                stmt.setInt(1, room);
                stmt.setString(2, name);
                stmt.setString(3, gender);
                stmt.setString(4, phone);
                stmt.setString(5, email);
                stmt.setString(6, address);
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println("Error inserting data: " + e.getLocalizedMessage());
            }
        }
        return false;
    }

    //student list
    public ObservableList<Student> loadStudentList() {
        ObservableList data = FXCollections.observableArrayList();
        try {
            Connection conn = Connect_db.getConnection();
            ResultSet srs = conn.createStatement().executeQuery(Query.getSts);
            while (srs.next()) {
                data.add(
                        new Student(srs.getInt("students.id"),
                                srs.getString("rooms.name"),
                                srs.getString("full_name"),
                                srs.getString("gender"),
                                srs.getString("phone"),
                                srs.getString("email"),
                                srs.getString("address"),
                                srs.getString("joined_date")
                        ));
            }
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return data;
    }

    //search student
    public ObservableList<Student> searchStudent(String name) {
        ObservableList data = FXCollections.observableArrayList();
        try {
            Connection conn = Connect_db.getConnection();
            ResultSet srs = conn.createStatement().executeQuery(Query.searchSts(name));
            while (srs.next()) {
                data.add(
                        new Student(srs.getInt("students.id"),
                                srs.getString("rooms.name"),
                                srs.getString("full_name"),
                                srs.getString("gender"),
                                srs.getString("phone"),
                                srs.getString("email"),
                                srs.getString("address"),
                                srs.getString("joined_date")
                        ));
            }
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return data;
    }

}
