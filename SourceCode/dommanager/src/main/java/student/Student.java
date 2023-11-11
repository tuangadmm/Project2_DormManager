
package student;

import data.Validator;
import data.Common;
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
public class Student {
    //fields
    String id, name, gender, room, address, phone, email, joinedDate;
    
    //constructor
    public Student() {
    }

    public Student(String id, String name, String gender, String room, String address, String phone, String email, String joinedDate) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.room = room;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.joinedDate = joinedDate;
    }
    
    public String getId(){    
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJoinedDate() {
        return joinedDate;
    }

    //getter and setter
    public void setJoinedDate(String joinedDate) {    
        this.joinedDate = joinedDate;
    }

    //methods
    public void loadStudent(String email) {
        try{
            Connection conn = Connect_db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(QueryStudent.getStsInfo);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            rs.next();
            setId(String.valueOf(rs.getInt("students.id")));
            setName(rs.getString("full_name"));
            setGender(rs.getString("gender"));
            setRoom(rs.getString("name"));
            setAddress(rs.getString("address"));
            setPhone(rs.getString("phone"));
            setEmail(rs.getString("email"));
            setJoinedDate(rs.getString("joined_date"));
            
        }catch(SQLException e){
            
        }
    }
    
    
    
}
