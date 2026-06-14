package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Model.*;

import java.io.IOException;

public class SendRequestFRTPageController {
   private Stage sendRequestStage;
    Regex regex=new Regex();
    String username;
    UserFile userFile=new UserFile();
    FriendsFile friendsFile=new FriendsFile();
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    private Stage dialogStage;

    public void initFunction(Stage sendRequestStage,String username){
        this.sendRequestStage=sendRequestStage;
        this.username=username;
    }

    @FXML
    private TextField usernameFLD;


    @FXML
    private Button  closeBTN ;
    @FXML
    private Button sendBTN;


    @FXML
    void searchHandler(ActionEvent event){
        try { // check kardan az lhaz regex v khodesh v dostash nbasha
            if(regex.usernameRegex(usernameFLD.getText()) && !usernameFLD.getText().equals(this.username) &&
            userFile.searchUser(usernameFLD.getText()) ){
                if( !friendsFile.isFriend(username, usernameFLD.getText())){

                    String friendUsername = usernameFLD.getText();
                    Stage stage = new Stage();
                    FXMLLoader loader= new FXMLLoader(getClass().getResource("/sendRequest2Page.fxml"));
                    loader.load();
                    SendRequestSCDPageController sendRequestSecondPageController= loader.getController();
                    stage.setScene(new Scene((Parent) loader.getRoot()));
                    stage.setTitle("Send Request");
                    stage.setResizable(false);
                    sendRequestSecondPageController.initFunction(sendRequestStage,friendUsername,username);
                    stage.show();
                }else {
                   String errorMessage = "";
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(dialogStage);
                    alert.setTitle("Invalid Fields");
                    alert.setHeaderText("Entered username is your friend");
                    alert.setContentText(errorMessage);
                    alert.showAndWait();
                }
            }
            else{
                String errorMessage = "";
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Invalid Fields");
                alert.setHeaderText("user nit found");
                alert.setContentText(errorMessage);
                alert.showAndWait();
                throw new UserNotFoundEc();
            }

        }catch (UserNotFoundEc| IOException e){
            System.out.println(e.getMessage());
        }
    }

//    public void goToRequestSecondPage(String friendUsername , String username) throws IOException {
//        FXMLLoader loader= new FXMLLoader(getClass().getResource("/sendRequest2Page.fxml"));
//        loader.load();
//        SendRequestSCDPageController sendRequestSecondPageController= loader.getController();
//        sendRequestStage.setScene(new Scene((Parent) loader.getRoot()));
//        sendRequestStage.setTitle("Send Request");
//        sendRequestStage.setResizable(false);
//        sendRequestSecondPageController.initFunction(sendRequestStage,friendUsername,username);
//        sendRequestStage.show();
//    }


    @FXML
    void backAc(ActionEvent event) throws IOException{
        Stage stage = (Stage) closeBTN .getScene().getWindow();
        stage.close();

    }

}
