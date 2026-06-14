package Controller;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserPageContrller implements Initializable {
    private Stage primaryStage;
    private GroupsMessageFile groupsMessageFile=new GroupsMessageFile();
    FriendsFile friendsFile=new FriendsFile();
    String username;
    private Person user;
    private ObservableList<Person> userFriends ;
    private ArrayList<Person> userFriendsArr;
    private UserFile userFile;
    private GroupsFile groupsFile;
    private BlockUserFile blockUserFile;
    private  FriendsMessageFile friendsMessageFile;
    @FXML
    private Label usernameId;



    public void initFunction(Stage userPageStage , ArrayList<Person> userFriends, String username){
        this.primaryStage = userPageStage;
        this.username=username;
        userFile=new UserFile();
        blockUserFile= new BlockUserFile();
        groupsFile= new GroupsFile();
        friendsFile= new FriendsFile();
        this.user=userFile.getUser(username);
        this.userFriendsArr=userFriends;
        friendsMessageFile=new FriendsMessageFile();
        this.userFriends = FXCollections.observableArrayList(userFriends);
        usernameId.setText(username);

        addGroupsToTable();  // add kardan group in tabla

        addFriendsToTable(this.userFriends); // add kardan friends to tabla

    }
    private Stage dialogStage;
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
        @FXML
        private Button showFGBTN;
        @FXML
        private Button sentRqtBTN;

        @FXML
        private Button logoutFBTN;

         @FXML
         private TableView<Person> FREANDTABLE;

         @FXML
         private TableView<Group> GROUPTABLE;

         @FXML
         private TableColumn<Person, String> friendculm;
         @FXML
         private TableColumn<Group, String> groupColumn;




    public Stage getStage(){
        return this.primaryStage;
    }

    void addFriendsToTable(ObservableList<Person> userFriends){
        FREANDTABLE.setItems(userFriends);
    }

    @FXML
        void blockfAc(ActionEvent event) { // block kardan frind
        Person selectedUser = FREANDTABLE.getSelectionModel().getSelectedItem();
        if(selectedUser != null){
            if(!blockUserFile.isBlocked(userFile.getUser(username) , selectedUser)){ // if this user was block it can not block it again
                blockUserFile.blockFriend(userFile.getUser(username),selectedUser); // here we block
            }
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(dialogStage);
                alert.setTitle("Invalid Fields");
                alert.setHeaderText("Please correct invalid fields");
                alert.setContentText(" This user is blocked  ");

                alert.showAndWait();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(" Select a user");

            alert.showAndWait();
        }

        }


        @FXML
        void showChatfAc(ActionEvent event)throws IOException { // show chat for tab friend
            Person selectedUser = FREANDTABLE.getSelectionModel().getSelectedItem();
            if( selectedUser != null){ // ckeck select someone
                FXMLLoader loader= new FXMLLoader(getClass().getResource("/showChatFriendsView.fxml"));
                loader.load();
                ShowPVFriendsController showChatFriendsController = loader.getController();
                Stage stage = new Stage();
                stage.setScene(new Scene((Parent) loader.getRoot()));
                stage.setTitle("Chat");
                stage.setResizable(false);
                showChatFriendsController.initFunction(stage,userFile.getUser(this.username) ,selectedUser, this);
                stage.show();
            }
            else
            {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Invalid Fields");
                alert.setHeaderText("Please correct invalid fields");
                alert.setContentText(" Select a user");

                alert.showAndWait();
            }
        }

        @FXML
        void showRqtAc(ActionEvent event) throws IOException {
           FXMLLoader loader= new FXMLLoader(this.getClass().getResource("../showRequests.fxml"));
            loader.load();
            ShowRequestFController showRequestsController = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene((Parent) loader.getRoot()));
            stage.setTitle("Requests");
            stage.setResizable(false);
            showRequestsController.initFunction1(stage ,username,this);
            stage.show();

         }

         @FXML
         void sendRqtAc(ActionEvent event) throws IOException { // show a send request page
             Stage StratPageS = new Stage();
             Stage stage=(Stage)showFGBTN.getScene().getWindow();


             FXMLLoader loader= new FXMLLoader(this.getClass().getResource("../sendRequestFirstPage.fxml"));
             loader.load();
             SendRequestFRTPageController sendRequestFirstPageController = loader.getController();
             sendRequestFirstPageController.initFunction(stage , username);
             StratPageS.setScene(new Scene((Parent) loader.getRoot()));
             StratPageS.show();
         }

        @FXML
        void logoutAc(ActionEvent event)throws IOException { // go to login page

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../LoginView.fxml"));
            loader.load();
            LoginController loginPageController = loader.getController();
            //primaryStage.close();
            Stage stages = new Stage();
            stages = (Stage)logoutFBTN.getScene().getWindow();
            stages.close();
            Stage stage = new Stage();
            stage.setScene(new Scene((Parent) loader.getRoot()));
            stage.setTitle("Login Page");
            stage.setResizable(false);
            loginPageController.initFunction(stage);
            stage.show();

        }
         //=====================================

        @FXML
        void newGroupAc(ActionEvent event)throws IOException {  // create a new group
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/CreateNewGroupView.fxml"));
            loader.load();
            CreateNewGroupController addNewGroupController = loader.getController();
            Stage stage= new Stage();
            stage.setScene(new Scene((Parent) loader.getRoot()));
            stage.setTitle("New Group");
            stage.setResizable(false);
            addNewGroupController.initFunction(stage,user,this);
            stage.show();

        }

        @FXML
        void showFACG(ActionEvent event) {

            Group selectedGroup = GROUPTABLE.getSelectionModel().getSelectedItem();
            if(selectedGroup != null){
                if( groupsFile.isAdmin(selectedGroup,user)){ // here we check if its a admin of the group we show the chat page of admin
                    try {
                       // goToAdminChatPage(selectedGroup,user);
                        Group group =selectedGroup;
                        Person admin =user;
                        FXMLLoader loader= new FXMLLoader(getClass().getResource("/showChatForGroupForAdmin.fxml"));
                        loader.load();
                        ShowChatForGroupAdminController showChatForGroupAdminController = loader.getController();
                        Stage stage= new Stage();
                        stage.setScene(new Scene((Parent) loader.getRoot()));
                        stage.setTitle("Chat");
                        stage.setResizable(false);
                        showChatForGroupAdminController.initFunction(stage,group,admin);
                        hotKey();
                        stage.show();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
                else {
                    try {
                        Group group;
                        group = selectedGroup ;
                        FXMLLoader loader= new FXMLLoader(getClass().getResource("/showChatGroupForMembers.fxml"));
                        loader.load();
                        ShowChatForGroupMemberController showChatForGroupMemberController = loader.getController();
                        Stage stage= new Stage();
                        stage.setScene(new Scene((Parent) loader.getRoot()));
                        stage.setTitle("Chat");
                        stage.setResizable(false);
                        showChatForGroupMemberController.initFunction(stage,group,user,this);
                        hotKey();
                        stage.show();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Invalid Fields");
                alert.setHeaderText("Please correct invalid fields");
                alert.setContentText(" Select a group");

                alert.showAndWait();
            }

        }
    public void hotKey(){ //shortcut of remove last message
        Scene scene= showFGBTN.getScene();
        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.B),
                new Runnable() {
                    @Override
                    public void run() {
                        groupsMessageFile.deleteLastMessage();
                    }
                }
        );
    }
    @FXML
    public void removeAc(ActionEvent event) { //remove kardan group
        Group selectedGroup = GROUPTABLE.getSelectionModel().getSelectedItem(); // create a selection mode
        if(selectedGroup != null){// if the secltion was not empaty
            if( groupsFile.isAdmin(selectedGroup, user)) { // check kardan agar admin bashad
                groupsMessageFile.clearHistory(selectedGroup); //pak sodan history
                groupsFile.removeGroup(selectedGroup); // group select shota pak shavad
                addGroupsToTable(); // group baqi manda khana v add to table
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Invalid Fields");
                alert.setHeaderText("Please correct invalid fields");
                alert.setContentText(" You aren't this group admin");

                alert.showAndWait();
            }

        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(" Select a group");

            alert.showAndWait();
        }
    }

        @FXML
        void logoutAccG(ActionEvent event) throws IOException{   // back to login page
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../LoginView.fxml"));
            loader.load();
            LoginController loginPageController = loader.getController();
            primaryStage.close();
//            primaryStage.setScene(new Scene((Parent) loader.getRoot()));
//            primaryStage.setTitle("Login Page");
//            primaryStage.setResizable(false);
//            loginPageController.initFunction(primaryStage);
//            primaryStage.show();
              Stage stage = new Stage();
              stage.setScene(new Scene((Parent) loader.getRoot()));
              stage.setTitle("Login Page");
              stage.setResizable(false);
              loginPageController.initFunction(stage);
              stage.show();


        }
    public void addGroupsToTable(){
        ArrayList<Group> groupsArr=new ArrayList<>();
        groupsArr.addAll(groupsFile.getUserAllGroups(user)); // find all the group of that user is init
        ObservableList<Group> groups = FXCollections.observableArrayList(groupsArr);
        GROUPTABLE.setItems(groups);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        friendculm.setCellValueFactory(new PropertyValueFactory<Person,String>("username"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("groupName"));
    }
}


