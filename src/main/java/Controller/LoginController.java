package Controller;

import Model.FriendsFile;
import Model.Person;
import Model.UserFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sun.applet.Main;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {
    private Stage primaryStage;

    public void initFunction(Stage primaryStage){
        this.primaryStage = primaryStage;
        userFile =new UserFile();
    }
    UserFile userFile=new UserFile();
    private Stage dialogStage;
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    FriendsFile friendsFile=new FriendsFile();
    @FXML
    private Button BTNback;

    @FXML
    private TextField userFLD;


    @FXML
    private TextField passFLD;


    @FXML
    void loginAction(ActionEvent event) throws IOException {
        if(userFile.userFound(userFLD.getText(),passFLD.getText())){// check if the user is exist
            ArrayList<Person> userFriends = friendsFile.findFriends(userFile.getUser(userFLD.getText()));
            //found the friends of the user
            String username = userFLD.getText();

            FXMLLoader loader= new FXMLLoader(Main.class.getResource("/UserM.fxml"));
            loader.load();
          //  primaryStage.close();  //******
            Stage stage = new Stage();
            UserPageContrller menucontroller= loader.getController();
            stage.setScene(new Scene((Parent) loader.getRoot()));
            stage.setTitle("User Page");
            stage.setResizable(false);
            menucontroller.initFunction(stage,userFriends,username);
            stage.show();



        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(" check the password and username");

            alert.showAndWait();
        }

    }
    @FXML
    void backAc(ActionEvent event) throws IOException { // back to start page
        Stage stage=(Stage)BTNback.getScene().getWindow();
        stage.close();
        FXMLLoader loader= new FXMLLoader(this.getClass().getResource("../StartPageView.fxml"));
        loader.load();
       StartPageController loginPageController = new StartPageController();
       Stage stage1 = new Stage();
        stage1.setScene(new Scene((Parent) loader.getRoot()));
        stage1.setTitle("gdsk program");
        stage1.setResizable(false);
        loginPageController.initFunction1(primaryStage);
        stage1.show();
    }

}
