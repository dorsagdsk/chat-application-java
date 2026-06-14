package Controller;

import Model.GroupsFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import Model.Person;

public class OpenLinkController {

    private Stage openLinkStage;
    private Person user;
    private String linkAddress;
    private GroupsFile groupsFile;

    @FXML
    private Hyperlink hyperlink;

    public void initFunction(Stage openLinkStage , String linkAddress , Person user){
        this.openLinkStage=openLinkStage;
        hyperlink.setText(linkAddress);
        this.user=user;
        this.linkAddress=linkAddress;
        groupsFile= new GroupsFile();

    }

    @FXML
    void hyperlinkHandler(ActionEvent event) {
        groupsFile.addMemberToGroup(linkAddress,user);
        openLinkStage.close();
    }

}
