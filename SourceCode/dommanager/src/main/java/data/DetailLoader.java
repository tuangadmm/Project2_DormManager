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

/**
 *
 * @author tuan
 */
public class DetailLoader {

    //fields
    //global use
    String name, username, gender, role, email, phone, address, joinedDate, time, status, roomName;
    int id, water, electric, capacity, occupied;

    //student list from room detail
    SimpleStringProperty studentName, studentGender, studentPhone, studentJoinedDate;
    SimpleIntegerProperty studentId;

    //constructor
    public DetailLoader() {
    }

    public DetailLoader(String studentName, String studentGender, String studentPhone, String studentJoinedDate, int studentId) {
        this.studentName = new SimpleStringProperty(studentName);
        this.studentGender = new SimpleStringProperty(studentGender);
        this.studentPhone = new SimpleStringProperty(studentPhone);
        this.studentJoinedDate = new SimpleStringProperty(studentJoinedDate);
        this.studentId = new SimpleIntegerProperty(studentId);
    }

    //getter and setter for student list
    public String getStudentName() {
        return studentName.get();
    }

    public String getStudentGender() {
        return studentGender.get();
    }

    public String getStudentPhone() {
        return studentPhone.get();
    }

    public String getStudentJoinedDate() {
        return studentJoinedDate.get();
    }

    public int getStudentId() {
        return studentId.get();
    }

    public void setStudentName(String studentName) {
        this.studentName = new SimpleStringProperty(studentName);
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = new SimpleStringProperty(studentGender);
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = new SimpleStringProperty(studentPhone);
    }

    public void setStudentJoinedDate(String studentJoinedDate) {
        this.studentJoinedDate = new SimpleStringProperty(studentJoinedDate);
    }

    public void setStudentId(int studentId) {
        this.studentId = new SimpleIntegerProperty(studentId);
    }

    //getter and setter
    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getGender() {
        return gender;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getJoinedDate() {
        return joinedDate;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public int getWater() {
        return water;
    }

    public int getElectric() {
        return electric;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getOccupied() {
        return occupied;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setJoinedDate(String joinedDate) {
        this.joinedDate = joinedDate;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public void setElectric(int electric) {
        this.electric = electric;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

//build data
    //student
    public void detailStudent(int id) {
        ResultSet rs;
        try {
            Connection conn = Connect_db.getConnection();
            rs = conn.createStatement().executeQuery(Query.detailSts(id));
            rs.next();
            setId(rs.getInt("students.id"));
            setName(rs.getString("full_name"));
            setGender(rs.getString("gender"));
            setRoomName(rs.getString("rooms.name"));
            setEmail(rs.getString("email"));
            setPhone(rs.getString("phone"));
            setAddress(rs.getString("address"));
            setJoinedDate(rs.getString("joined_date"));
        } catch (SQLException ex) {
            System.out.println("Error loading data: " + ex.getMessage());
        }
    }

    //bill
    public void detailBill(int id) {
        ResultSet rs;
        try {
            Connection conn = Connect_db.getConnection();
            rs = conn.createStatement().executeQuery(Query.detailBill(id));
            rs.next();
            setId(rs.getInt("bills.id"));
            setName(rs.getString("name"));
            setStatus(rs.getString("payment_status"));
            setWater(rs.getInt("water"));
            setElectric(rs.getInt("electricity"));
            setTime(rs.getString("time"));
        } catch (SQLException ex) {
            System.out.println("Error loading data: " + ex.getMessage());
        }
    }

    //account
    public void detailAccount(int id) {
        ResultSet rs;
        try {
            Connection conn = Connect_db.getConnection();
            rs = conn.createStatement().executeQuery(Query.detailAccount(id));
            rs.next();
            setId(rs.getInt("id"));
            setUsername(rs.getString("username"));
            setName(rs.getString("full_name"));
            setRole(rs.getString("role"));
            setEmail(rs.getString("email"));
            setPhone(rs.getString("phone"));
            setAddress(rs.getString("address"));
            setTime(rs.getString("joined_date"));
        } catch (SQLException ex) {
            System.out.println("Error loading data: " + ex.getMessage());
        }
    }

    //room
    public void detailRoom(int id) {
        try {
            Connection conn = Connect_db.getConnection();
            //get room info
            ResultSet rs = conn.createStatement().executeQuery(Query.detailRoom(id));
            rs.next();
            setRoomName(rs.getString("name"));
            setCapacity(rs.getInt("capacity"));
            setOccupied(rs.getInt("occupied"));

            //get bill
            rs = conn.createStatement().executeQuery(Query.roomBill(id));
            try {
                rs.next();
                setWater(rs.getInt("water"));
                setElectric(rs.getInt("electricity"));
            } catch (SQLException es) {
                setWater(0);
                setElectric(0);
            }

        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

//scene methods
    //delete student
    public boolean deleteStudent(int id) {
        try {
            Connection conn = Connect_db.getConnection();
            conn.createStatement().execute(Query.deleteSts(id));
            return true;
        } catch (SQLException ex) {
            System.out.println("Error deleting student: " + ex.getMessage());
        }

        return false;
    }

    //update student
    public boolean updateStudent(int id, String room, String phone) {
        int rid = Common.getRoomId(room);

        try {
            Connection conn = Connect_db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(Query.updateSts);
            stmt.setInt(1, rid);
            stmt.setString(2, phone);
            stmt.setInt(3, id);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating data: " + e.getMessage());
        }
        return false;
    }

    //delete bill
    public boolean deleteBill(int id) {
        try {
            Connection conn = Connect_db.getConnection();
            conn.createStatement().execute(Query.deleteBill(id));
            return true;
        } catch (SQLException ex) {
            System.out.println("Error deleting bill: " + ex.getMessage());
        }
        return false;
    }

    //update bill
    public boolean updateBill(int id, String status) {
        try {
            Connection conn = Connect_db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(Query.updateBill);
            stmt.setInt(2, id);
            stmt.setString(1, status);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error updating data: " + e.getMessage());
        }
        return false;
    }

    //delete account
    public boolean deleteAccount(int id) {
        try {
            Connection conn = Connect_db.getConnection();
            conn.createStatement().execute(Query.deleteAccount(id));
            return true;
        } catch (SQLException ex) {
            System.out.println("Error deleting account: " + ex.getMessage());
        }
        return false;
    }

    //update account
    public boolean updateAccount(int id, String role, String phone) {
        try {
            Connection conn = Connect_db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(Query.updateAccount);
            stmt.setString(1, role);
            stmt.setString(2, phone);
            stmt.setInt(3, id);
            stmt.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error updating data: " + e.getMessage());
        }
        return false;
    }

    //delete room
    public boolean deleteRoom(String name) {
        try {
            //check if room is empty
            Connection conn = Connect_db.getConnection();
            ResultSet rs = conn.createStatement().executeQuery(Query.getOccupied(name));
            rs.next();
            if (rs.getInt("occupied") == 0) {
                //delete room
                try {
                    conn.createStatement().execute(Query.deleteRoom(name));
                    conn.createStatement().execute(Query.deleteRoomBill(Common.getRoomId(name)));
                    return true;
                } catch (SQLException del) {
                    System.out.println("Error deleting room: " + del.getClass());
                }
            } else {
                return false;
            }
        } catch (SQLException ch) {
            System.out.println("Error loading data: " + ch.getMessage());
        }
        return false;
    }

    //update room
    public boolean updateRoom(int id, int capacity) {
        if (capacity < 1) {
            return false;
        } else {
            try {
                Connection conn = Connect_db.getConnection();
                PreparedStatement stmt = conn.prepareStatement(Query.updateRoom);
                stmt.setInt(1, capacity);
                stmt.setInt(2, id);
                stmt.execute();
                return true;
            } catch (SQLException e) {
                System.out.println("Error updating room: " + e.getCause());
            }
        }
        return false;
    }

    //load room student
    public ObservableList loadStudentList(int id) {
        ObservableList<DetailLoader> data = FXCollections.observableArrayList();
        try {
            Connection conn = Connect_db.getConnection();
            //ResultSet
            ResultSet r = conn.createStatement().executeQuery(Query.getRoomSts(id));

            //add data
            while (r.next()) {
                //Iterate Row
                data.add(
                        new DetailLoader(r.getString("full_name"),
                                r.getString("gender"),
                                r.getString("phone"),
                                r.getString("joined_date"),
                                r.getInt("id")
                        ));
            }
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return data;
    }

}
