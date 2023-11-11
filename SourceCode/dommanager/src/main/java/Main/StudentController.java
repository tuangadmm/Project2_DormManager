/*
 */
package Main;

import student.*;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import local.UserSession;

/**
 *
 * @author tuan
 */
public class StudentController {
//nodes
    //dashboard

    @FXML
    Label name, electricity, water, total, payment;
    //room
    @FXML
    Label freeSlot;
    @FXML
    TableView<Room> roomMates;
    @FXML
    TableColumn<Room, String> tbName, tbGender, tbPhone, tbAddress, tbJoinedDate;
    @FXML
    TableColumn<Room, Integer> tbId;
    //announcement
    @FXML
    TableView<Announcement> announcementList;
    @FXML
    TableColumn<Announcement, Integer> tbIdA;
    @FXML
    TableColumn<Announcement, String> tbFrom, tbRole, tbSubject, tbMessage, tbSentDate;
    //feedback
    @FXML
    TableView<Feedback> feedbackList;
    @FXML
    TableColumn<Feedback, Integer> tbIdF;
    @FXML
    TableColumn<Feedback, String> tbSubjectF, tbMessageF, tbSentDateF, tbStatus;
    //personal
    @FXML
    Label pId, pName, pGender, pRoom, pAddress, pPhone, pEmail, pJoinedDate;
//add form
    //feedback, announcement
    @FXML
    Label addError;
    @FXML
    TextField nfSubject, naSubject;
    @FXML
    TextArea nfMessage, naMessage;

//button
    @FXML
    Button announcementBtn, confirmBtn;
//global
    private Stage stage, addForm;
    private Scene scene;
    private Parent root;
    public static String email;

//initialize
    @FXML
    public void initialize() {
        UserSession u = new UserSession();
        email = u.readUser();
    }

//build
    //build stage
    public void buildStage(Stage stage, Scene scene, ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 1280, 800);
        stage.setScene(scene);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("img/bill.png")));
        stage.setResizable(false);
        stage.show();
    }

//build data
    //build student information
    public void buildStudent(String email) {
        Student s = new Student();
        s.loadStudent(email);
        name.setText(s.getName());
    }

    public void buildDashboard() {
        Dashboard d = new Dashboard();
        d.loadDashboard();
        electricity.setText(Common.formatCurrency(d.getElectricity()) + " VND");
        water.setText(Common.formatCurrency(d.getWater()) + " VND");
        total.setText(Common.formatCurrency(d.getTotal()) + " VND");
        payment.setText(d.getPayment());
        announcementBtn.setText(d.getNoftify());
    }

    public void buildRoom() {
        Room r = new Room();
        freeSlot.setText(r.getFreeSlot());

        tbId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tbName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tbGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        tbPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        tbAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tbJoinedDate.setCellValueFactory(new PropertyValueFactory<>("JoinedDate"));

        roomMates.setItems(r.loadStudentList());
    }

    public void buildAnnouncement() {
        Announcement a = new Announcement();

        tbIdA.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tbFrom.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tbRole.setCellValueFactory(new PropertyValueFactory<>("Role"));
        tbSubject.setCellValueFactory(new PropertyValueFactory<>("Subject"));
        tbMessage.setCellValueFactory(new PropertyValueFactory<>("Message"));
        tbSentDate.setCellValueFactory(new PropertyValueFactory<>("SentDate"));

        announcementList.setItems(a.loadAnnList());

        announcementList.setRowFactory(tv -> {
            TableRow<Announcement> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        Announcement rowData = row.getItem();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudentAnnouncementDetail.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        StudentDetailController d = fxmlLoader.getController();
                        d.buildAnn(rowData.getId());

                        Stage x = new Stage();
                        x.initModality(Modality.APPLICATION_MODAL);
                        x.setTitle("Detail");
                        x.setScene(new Scene(root1));
                        x.setResizable(false);
                        x.show();
                    } catch (Exception ee) {
                        System.out.println("Error loading accouncement: " + ee.getLocalizedMessage());
                    }
                }
            });
            return row;
        });
    }

    public void buildFeedback() {
        Feedback f = new Feedback();

        tbIdF.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tbSubjectF.setCellValueFactory(new PropertyValueFactory<>("Subject"));
        tbMessageF.setCellValueFactory(new PropertyValueFactory<>("Message"));
        tbSentDateF.setCellValueFactory(new PropertyValueFactory<>("SentDate"));
        tbStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

        feedbackList.setItems(f.loadFeedbackList());

        feedbackList.setRowFactory(tv -> {
            TableRow<Feedback> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        Feedback rowData = row.getItem();
                        int rowId = rowData.getId();

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudentFeedbackDetail.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        StudentDetailController d = fxmlLoader.getController();
                        d.buildFeedback(rowId);
                        d.buildFeedbackReply(rowId);
                        Stage x = new Stage();
                        x.initModality(Modality.APPLICATION_MODAL);
                        x.setTitle("Detail");
                        x.setScene(new Scene(root1));
                        x.setResizable(false);
                        x.show();
                    } catch (Exception ee) {
                        System.out.println(ee.getCause());
                    }
                }
            });
            return row;
        });
    }

    public void buildPersonal() {
        Student s = new Student();
        s.loadStudent(email);
        pId.setText(String.valueOf(s.getId()));
        pName.setText(s.getName());
        pGender.setText(s.getGender());
        pRoom.setText(s.getRoom());
        pAddress.setText(s.getAddress());
        pPhone.setText(s.getPhone());
        pEmail.setText(s.getEmail());
        pJoinedDate.setText(s.getJoinedDate());

    }

//load scene
    public void dashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentDashboard.fxml"));
        root = loader.load();

        StudentController c = loader.getController();
        c.buildDashboard();
        c.buildStudent(email);

        buildStage(stage, scene, event);
    }

    public void room(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentRoom.fxml"));
        root = loader.load();

        StudentController c = loader.getController();
        c.buildRoom();
        c.buildStudent(email);

        buildStage(stage, scene, event);
    }

    public void announcement(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentAnnouncement.fxml"));
        root = loader.load();

        StudentController c = loader.getController();
        c.buildAnnouncement();
        c.buildStudent(email);

        buildStage(stage, scene, event);
    }

    public void feedback(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentFeedback.fxml"));
        root = loader.load();

        StudentController c = loader.getController();
        c.buildFeedback();
        c.buildStudent(email);

        buildStage(stage, scene, event);
    }

    public void personal(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentPersonal.fxml"));
        root = loader.load();

        StudentController c = loader.getController();
        c.buildPersonal();
        c.buildStudent(email);

        buildStage(stage, scene, event);
    }

    public void logout(ActionEvent event) throws IOException {
        UserSession u = new UserSession();
        u.clearUser();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 569, 399);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    //add feedback
    public void addFeedback(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddFeedbackForm.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        addForm = new Stage();
        addForm.initModality(Modality.APPLICATION_MODAL);
        addForm.setTitle("Add feedback");
        addForm.setScene(new Scene(root1));
        addForm.setResizable(false);
        addForm.show();
    }

//methods
    public void addNewFeedback(ActionEvent event) throws IOException{
        String subject = nfSubject.getText();
        String message = nfMessage.getText();
        if (Common.isEmpty(subject) || Common.isEmpty(message)) {
            addError.setText("All fields are required");
        } else {
            Feedback f = new Feedback();
            if (f.addFeedback(subject, message)) {
                Dialog changeOk = new Dialog();
                ButtonType btn = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                changeOk.setTitle("Information");
                changeOk.setContentText("Your feedback has been recorded");
                changeOk.getDialogPane().getButtonTypes().add(btn);
                changeOk.showAndWait();
                addForm = (Stage) confirmBtn.getScene().getWindow();
                addForm.close();
                reloadFeedback(event);

            }
        }
    }
    
    //reload scene
    public void reloadFeedback(ActionEvent event) throws IOException {
        addForm = (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        addForm.close();

        feedback(event);
    }
    public void reloadAnnouncement(ActionEvent event) throws IOException {
        addForm = (Stage) Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        addForm.close();

        announcement(event);
    }

}
