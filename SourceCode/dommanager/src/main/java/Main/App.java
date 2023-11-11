package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import local.*;
import java.io.IOException;
import javafx.scene.image.Image;

/**
 * JavaFX App
 */
public class App extends Application {

    public UserSession u = new UserSession();
    private static String currentUser;

    @Override
    public void start(Stage stage) throws IOException {
        if (u.isLoggedIn()) {
            if (u.userValid(u.readUser()) && UserSession.loginType == 0) {
                SceneController.username = u.readUser();
                dashboard(stage, u.readUser());
            } else if (u.userValid(u.readUser()) && UserSession.loginType == 1) {
                currentUser = u.readUser();
                dashboardS(stage);
            } else {
                login(stage);
            }
        } else {
            login(stage);
        }
    }

    //build login
    private void login(Stage stage) throws IOException {
        Scene scene = new Scene(loadFXML("Login"), 569, 399);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("img/bill.png")));
        stage.setTitle("Dorm Manager");
        stage.setResizable(false);

        stage.centerOnScreen();
        stage.show();
    }

    //build student dashboard
    public void dashboardS(Stage stage) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentDashboard.fxml"));
        Parent root = loader.load();
        StudentController c = loader.getController();
        c.buildDashboard();
        c.buildStudent(currentUser);
        
        Scene scene = new Scene(root, 1280, 800);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("img/bill.png")));
        stage.setTitle("Dorm Manager");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.centerOnScreen();
        stage.show();

    }

    //build dash board
    public void dashboard(Stage stage, String username) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Parent root = loader.load();
        SceneController c = loader.getController();
        c.buildDashboard();
        c.buildManager(username);
        Scene scene = new Scene(root, 1280, 800);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("img/bill.png")));
        stage.setTitle("Dorm Manager");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.centerOnScreen();
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
