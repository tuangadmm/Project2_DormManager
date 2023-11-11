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
public class Room {
    //field
    SimpleStringProperty name;
    SimpleIntegerProperty id, total, occupied, free;

    public Room() {
    }

    //constructor for room list
    public Room(String rName, int rId, int rTotal, int rOccupied, int rFree) {
        this.name = new SimpleStringProperty(rName);
        this.id = new SimpleIntegerProperty(rId);
        this.total = new SimpleIntegerProperty(rTotal);
        this.occupied = new SimpleIntegerProperty(rOccupied);
        this.free = new SimpleIntegerProperty(rFree);
    }
    
    //getter and setter for room list

    public String getName() {
        return name.get();
    }
    public int getId() {
        return id.get();
    }
    public int getTotal() {
        return total.get();
    }
    public int getOccupied() {
        return occupied.get();
    }
    public int getFree() {
        return free.get();
    }
    
    public void setName(String rName) {
        this.name = new SimpleStringProperty(rName);
    }
    public void setId(int rId) {
        this.id = new SimpleIntegerProperty(rId);
    }
    public void setTotal(int rTotal) {
        this.total = new SimpleIntegerProperty(rTotal);
    }
    public void setOccupied(int rOccupied) {
        this.occupied = new SimpleIntegerProperty(rOccupied);
    }
    public void setFree(int rFree) {
        this.free = new SimpleIntegerProperty(rFree);
    }
    
    //methods
    //add room
    public boolean add(String capacity, String occupied) {
        Validator v = new Validator();
        int isok = 1;
        String c = capacity;
        String o = occupied;

        if (capacity.length() == 0) {
            c = "8";
        }
        if (occupied.length() == 0) {
            o = "0";
        }

        if (v.numbercheck(c) == false) {
            isok = 0;
            return false;
        }

        try {
            if (Integer.parseInt(c) == 0) {
                isok = 0;
                return false;
            }
        } catch (NumberFormatException ne) {
            isok = 0;
            return false;
        }

        if (v.numbercheck(o) == false) {
            isok = 0;
            return false;
        }

        if (isok == 1) {
            try {
                Connection conn = Connect_db.getConnection();
                PreparedStatement stmt = conn.prepareStatement(Query.insertRoom);
                stmt.setString(1, setNewName());
                stmt.setInt(2, Integer.parseInt(c));
                stmt.setInt(3, Integer.parseInt(o));
                stmt.execute();
                return true;
            } catch (SQLException se) {
                return false;
            }
        }
        return false;
    }

    //set room name
    public String setNewName() {
        int id = getNewId();
        if (id >= 10) {
            return "R" + String.valueOf(id);
        } else {
            return "R0" + String.valueOf(id);
        }
    }

    //get id
    public int getNewId() {
        int id;
        try {
            Connection conn = Connect_db.getConnection();
            ResultSet irs = conn.createStatement().executeQuery(Query.getRId);
            irs.next();
            id = irs.getInt("id") + 1;
        } catch (SQLException e) {
            id = 1;
        }
        return id;
    }
    
    //load room list
    public ObservableList<Room> loadRoom(){
        ObservableList data = FXCollections.observableArrayList();
        try{
            Connection conn = Connect_db.getConnection();
            ResultSet rrs = conn.createStatement().executeQuery(Query.getRoomList);
            while(rrs.next()){
                data.add(
                        new Room(rrs.getString("name"), 
                                rrs.getInt("id"),
                                rrs.getInt("capacity"),
                                rrs.getInt("occupied"),
                                rrs.getInt("capacity") - rrs.getInt("occupied")
                        ));
            }
        }catch(SQLException e){
            System.out.println("Error loading data: " + e.getMessage());
        }
        return data;
    }


}
