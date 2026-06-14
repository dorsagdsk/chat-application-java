package Controller;

import Model.RequsetFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class SendRequestSCDPageController {
    public Button cancelBTN;
    private Stage dialogStage;
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    private Stage sendRequestStage;
    private Stage stage = new Stage();
    private RequsetFile requestsFile;
    private String username;


    public void initFunction(Stage sendRequestStage , String friendUsername , String username){
        this.sendRequestStage=sendRequestStage;
        usernameFLD.setText(friendUsername);
        requestsFile= new RequsetFile();
        this.username= username;
    }

    @FXML
    private Label usernameFLD;

    @FXML
    private Button sendBTN;

    @FXML
    void cancelHandler(ActionEvent event) throws IOException {

        stage.close();
    }

    @FXML
    void sendHandler(ActionEvent event) {
        if(requestsFile.requestIsNotExist(this.username,usernameFLD.getText())){// if this request not exist
            requestsFile.sendRequest(this.username,usernameFLD.getText());       // add it to request file

           Stage stage=(Stage)sendBTN.getScene().getWindow();
            stage.close();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("You can not request to "+usernameFLD.getText());

            alert.showAndWait();
        }

    }
}
