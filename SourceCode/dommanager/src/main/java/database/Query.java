/*
 */
package database;

/**
 *
 * @author tuan
 */
public class Query {
//common
    //get presence of an email
    public static String getEmail(String email){
        return "select count(*) as numRow from students where email = '" + email + "'";
    };
    
    //get presence of an phone number
    public static String getPhone(String phone){
        return "select count(*) as numRow from students where phone = '" + phone + "'";
    };
    
//student
    //get list of students
    public static String getSts = "select * from students join rooms on students.room_id = rooms.id where students.deleted = 0";

    //get id of last inserted student 
    public static String getSId = "select id from students order by id desc limit 1";
    
    //insert new student
    public static String insertSts = "insert into students (room_id, full_name, gender, phone, email, address) value (?, ?, ?, ?, ?, ?)";
    
    //get occupied slots of current room
    public static String getOccupied(int id){
        return "select occupied from rooms where id = " + id;
    };
    
    //search student
    public static String searchSts(String name){
        return "select * from students join rooms on students.room_id = rooms.id where full_name like '%"+name+"%' and students.deleted = 0 ";
    };
//bill
    //get bill
    public static String getBill(String month){
        if (!month.equals("All")) {
            return "select * from bills join rooms on bills.room_id = rooms.id where time = '" + month + "' and bills.deleted = 0 ";
        }else{
            return "select * from bills join rooms on bills.room_id = rooms.id and bills.deleted = 0 ";
        }
    };
    
    //add bill
    public static String insertBill = "insert into bills (water, electricity, time, room_id) value(?, ?, ?, ?)";
    
    //get all rooms
    public static String getAllRoom = "select name from rooms where deleted = 0";
    
//account
    //add to users table
    public static String insertUser = "insert into users (username, password) value (?, ?)";
    
    //add to user_detail table
    public static String insertUserDetail = "insert into user_detail(user_id, full_name, email, phone, role, address) value (?, ?, ?, ?, ?, ?)";

    //get id of last inserted account
    public static String getUId = "select id from users order by id desc limit 1";

    //get list of accounts
    public static String getAccountList = "select users.id, username, role, full_name, email, phone, address, joined_date from users "
        + "join user_detail on users.id = user_detail.user_id "
        + "where id != 1 and deleted = 0  "
        + "order by id";
    
//room
    //add room
    public static String insertRoom = "insert into rooms (name, capacity, occupied) value (?, ?, ?)";

    //get id of last inserted room
    public static String getRId = "select id from rooms order by id desc limit 1";

    //get list of rooms
    public static String getRoomList = "select * from rooms where deleted = 0";
    
//basic
    //load manager info
    public static String getManager(String username){
        return "select * from users join user_detail on users.id = user_detail.user_id where username = '" + username + "'";
    }
    //get manager's id by username
    public static String getManagerId = "select id from users where username = ?";
    
    //load dashboard info
    //student
    public static String getStsInfo = "select count(*) as totalStudent from students where deleted = 0";
    //room
    public static String getRsInfo = "select * from rooms where deleted = 0";
    //bill
    public static String getBInfo = "select * from bills where payment_status = 'Not paid' and deleted = 0";

//setting
    //update manager
    public static String updateManager = "update user_detail set full_name = ? , email = ?, phone = ?, address = ?  where user_id = ?";

    //delete manager
    public static String deleteManager(String username){
        return "update users set deleted = 1 where username = '" + username + "'";
    }
    
    //change password
    public static String updatePassword(String nPw, int id){
        return "update users set password = '" + nPw + "' where id = '" + id + "'";
    }
//announcement
    //get announcement list
    public static String getAnnList = "select * from announcements join user_detail on sender = user_id join rooms on rooms.id = to_room order by announcements.id desc";
    //announcement detail
    public static String getAnnDetail = "select * from announcements join rooms on sender = to_room join user_detail on sender = user_id where announcements.id = ?";
    //add announcement
    public static String addAnnouncement = "insert into announcements (sender, to_room, subject, message) value (?, ?, ?, ?)";
//feedback
    //get feedback list
    public static String getFeedbackList = "select * from students join feedback on sender = students.id join rooms on rooms.id = room_id order by feedback.id desc";
    //get feedback detail
    public static String getFeedbackDetail = "select * from students join feedback on sender = students.id join rooms on rooms.id = room_id where feedback.id = ?";
    //get feedback reply
    public static String getFeedbackReply = "select * from feedback_reply where feedback_id = ?";
    //answer feedback
    public static String answerFeedback = "insert into feedback_reply (feedback_id, replier, reciever, message) value(?, ?, ?, ?)";
//reset password
    //check if information is valid
    public static String infoCheck = "select count(*) as numRow from users join user_detail on users.id = user_detail.user_id "
                + "where username = ? "
                + "and email = ? "
                + "and phone = ? "
                + "and deleted = 0";

    //reset password
    public static String resetPassword = "update users set password = ? where username = ?";

//detailLoader
    //student
    public static String detailSts(int id){
        return "select * from students join rooms on students.room_id = rooms.id where students.id = " + id;
    }
    
    //bill
    public static String detailBill(int id){
        return "select * from bills join rooms on bills.room_id = rooms.id where bills.id = " + id;
    }
    
    //account
    public static String detailAccount(int id){
        return "select * from users join user_detail on users.id = user_detail.user_id where id = " + id;
    }
    
    //room
    //room detail
    public static String detailRoom(int id){
        return "select * from rooms where id = " + id;
    }
    //bill of room
    public static String roomBill(int id){
        return "select * from bills where room_id = " + id + " order by time limit 1";
    }
    
    //delete student
    public static String deleteSts(int id){
        return "update students set deleted = 1 where id = " + id;
    }
    
    //update student
    public static String updateSts = "update students set room_id = ?, phone = ? where id = ?";

    //delete bill
    public static String deleteBill(int id){
        return "update bills set deleted = 1 where id = " + id;
    }

    //update bill
    public static String updateBill = "update bills set payment_status = ? where id = ?";
  
    //delete account
    public static String deleteAccount(int id){
        return "update users set deleted = 1 where id = " + id;
    }
    
    //update account
    public static String updateAccount = "update user_detail set role = ?, phone = ? where user_id = ?";

    //delete room
    //get current room occupied slots
    public static String getOccupied(String name){
        return "select occupied from rooms where name = '" + name + "'";
    }
    //delete room
    public static String deleteRoom(String name){
        return "update rooms set deleted = 1 where name = '" + name + "'";
    }
    //delete bill of deleted room
    public static String deleteRoomBill(int id){
        return "update bills set deleted = 1 where room_id = " + id;
    }
    
    //update room
    public static String updateRoom = "update rooms set capacity = ? where id = ?";

    //get student list of room
    public static String getRoomSts(int id){
        return "select * from students where deleted = 0 and room_id = " + id;
    }
    
    
}
