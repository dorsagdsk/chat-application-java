package Controller;

import Model.Group;
import Model.GroupsFile;
import Model.GroupsMessageFile;
import Model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RemoveMemberController implements Initializable {

    private Stage kickStage;
    private Group group;
    private GroupsFile groupsFile;
    private ShowChatForGroupAdminController controller;
    private Stage dialogStage;
    private GroupsMessageFile groupsMessageFile=new GroupsMessageFile();

    public void initFunction(Stage kickStage , Group group , ShowChatForGroupAdminController controller){
        groupNameLBL.setText(group.getGroupName());
        this.kickStage= kickStage;
        this.group=group;
        this.groupsFile= new GroupsFile();
        this.controller=controller;
        addToTable();
    }

    @FXML
    private TableView<Person> groupMembersTable;

    @FXML
    private TableColumn<Person, String> membersColumn;

    @FXML
    private Label groupNameLBL;

    @FXML
    void closeHandler(ActionEvent event) {
        kickStage.close();
    }

    public void addToTable(){
        ArrayList<Person> usersArrayList = new ArrayList<>();
        usersArrayList.addAll(groupsFile.getGroupMembers(group.getId()));
        ObservableList<Person> users = FXCollections.observableArrayList(usersArrayList);
        groupMembersTable.setItems(users);
    }

    @FXML
    void kickMemberHandler(ActionEvent event) {
        Person selectedMember = groupMembersTable.getSelectionModel().getSelectedItem();
        if(selectedMember != null){

            Group groupAfterKickMember = groupsFile.kickMember(selectedMember, group);
            addToTable();
            controller.initFunction(controller.getStage() , groupAfterKickMember ,group.getAdmin());
            kickStage.close();

        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(" Select a member");

            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        membersColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    }
}
