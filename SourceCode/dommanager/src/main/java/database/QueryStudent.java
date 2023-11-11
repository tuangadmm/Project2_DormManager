
package database;

/**
 *
 * @author tuan
 */
public class QueryStudent {
//common
    //get room id by student's email
    public static String getRoomIdByEmail = "select rooms.id from rooms join students on rooms.id = students.room_id where email = ?";
    //get student's id by email
    public static String getStudentIdByEmail = "select id from students where email = ?";
//student
    //get student information by email
    public static String getStsInfo = "select * from students join rooms on students.room_id = rooms.id where email = ?";
//dashboard
    //bill info
    public static String getBillInfo = "select * from bills where room_id = ?";
    //announcement info
    public static String getAnnInfo = "select * from users join announcements on sender = users.id join user_detail on users.id = user_detail.user_id where to_room = ? order by announcements.id desc limit 1";
//room
    //student list
    public static String getSts = "select * from students where room_id = ?";
    //get free slot
    public static String getFreeSlot = "select * from rooms where id = ?";
//announcement
    //announcement list
    public static String getAnn = "select * from announcements join user_detail on sender = user_id join rooms on to_room = rooms.id where rooms.id = ? order by announcements.id desc";
//feedback
    //feedback list
    public static String getFeedback = "select * from feedback join students on sender = students.id where students.id = ? order by sent_date desc";
//detail
    //announcement
    public static String annDetail = "select * from announcements join rooms on rooms.id = to_room join user_detail on sender = user_id where announcements.id = ? ";
    //sent feedback
    public static String sentFeedbackDetail = "select * from feedback where id = ?";
    //replied feedback
    public static String repliedFeedbackDetail = "select * from feedback_reply join user_detail on replier = user_id where feedback_id = ?";
//add
    //feedback
    public static String addFeedback = "insert into feedback(sender, subject, message) value(?, ?, ?)";

}
