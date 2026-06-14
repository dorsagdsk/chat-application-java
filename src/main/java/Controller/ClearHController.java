package Controller;

import Model.FriendsMessageFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import Model.Person;

public class ClearHController {

    private Stage clearHistoryStage;
    private FriendsMessageFile messageFile;
    private Person user;
    private Person friend;
    private ShowPVFriendsController controller;
    private UserPageContrller menucontrollerl;

    public void initFunction(Stage ClearHistoryF, Person user , Person friend , ShowPVFriendsController controller, UserPageContrller menucontroller){
        this.clearHistoryStage= ClearHistoryF;
        messageFile= new FriendsMessageFile();
        this.controller=controller;
        this.user=user;
        this.menucontrollerl=menucontroller;
        this.friend=friend;
    }


    @FXML
    void acceptHandler(ActionEvent event) {
        messageFile.clearHistory(user,friend);
        controller.initFunction(controller.getStage(),user,friend,menucontrollerl);
        clearHistoryStage.close();

    }

    @FXML
    void rejectHandler(ActionEvent event) {
        clearHistoryStage.close();
    }
}
