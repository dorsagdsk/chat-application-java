package Controller;

import Model.GroupsFile;
import Model.Person;
import Model.Regex;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateNewGroupController{

    private Stage dialogStage;
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    private Stage addNewGroupStage;
    private Regex regex;
    private Person user ;
    private GroupsFile groupsFile;
    private UserPageContrller controller;


    public void initFunction(Stage addNewGroupStage, Person user, UserPageContrller controller){
        this.user=user;
        this.addNewGroupStage=addNewGroupStage;
        this.controller=controller;
        regex=new Regex();
        groupsFile=new GroupsFile();
    }

    @FXML
    private TextField groupNameFLD;

    @FXML
    private TextField linkFLD;

    @FXML
    void closeHandler(ActionEvent event) {
        addNewGroupStage.close();
    }

    @FXML
    void createHandler(ActionEvent event) throws IOException {                                  // ejad group
        if(regex.groupNameRegex(groupNameFLD.getText()) && regex.linkRegex(linkFLD.getText())){ // check kardan asm v link group az lhaz regex
            if(groupsFile.linkNotExist(linkFLD.getText())){                                     // bray mojpd bodan ya nbodan link
                groupsFile.addNewGroup(user,groupNameFLD.getText(),linkFLD.getText());          // add kardan info  asm group v link v info user
                controller.addGroupsToTable();                                                  // add to table userpage


                addNewGroupStage.close();
            }
            else
            {
               Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Invalid Fields");
                alert.setHeaderText("Please correct invalid fields");
              alert.setContentText(" Entered link exist");

                alert.showAndWait();
           }

        }
        else

            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Invalid Fields");
                alert.setHeaderText("Please correct invalid fields");
                alert.setContentText("Try Again");

                alert.showAndWait();
            }



    }

}
