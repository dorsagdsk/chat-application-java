package Controller;

import Model.*;
import javafx.application.Platform;
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
import sun.applet.Main;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowPVFriendsController implements Initializable {

    private Stage showChatStage;
    private Person user;
    private Person friend;
    private UserFile userFile;
    private FriendsMessageFile messageFile;
    private boolean edited=false;
    private boolean removed=false;
    private Message selectedMessage;
    public FriendsMessageFile friendsMessageFile=new FriendsMessageFile();
    private Stage dialogStage;
    private UserPageContrller menucontroller;
    GroupsFile groupsFile=new GroupsFile();
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    private BlockUserFile blockUserFile=new BlockUserFile();

    @FXML
    private TableView<Message> chatTable;

    @FXML
    private TableColumn<Message, String> messageColumn;

    @FXML
    private TableColumn<Message, String> dateAndTimeColumn;

    @FXML
    private TextField messageFLD;

    @FXML
    private Label errorLBL;

    @FXML
    private TableColumn<Message, String> senderUsernameColumn;

    @FXML
    private Label usernameTitleLBL;
    @FXML
    private Button sendBTN;

    public void initFunction(Stage showChatStage , Person user , Person friend,UserPageContrller menucontroller){
        this.showChatStage=showChatStage;
        this.user=user;
        this.userFile= new UserFile();
        this.friend=friend;
        this.menucontroller=menucontroller;
        usernameTitleLBL.setText(friend.getUsername());
        messageFile=new FriendsMessageFile();
        addChatToTable();
    }


    @FXML
    void closeHandler(ActionEvent event) {
        menucontroller.addGroupsToTable();
        showChatStage.close();
    }

    String getNowDateTime(){    // create time every message
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return (dateTimeFormatter.format(now));
    }
    public Stage getStage(){
        return showChatStage;
    }

    @FXML
    void sendHandler(ActionEvent event) {
        if(! blockUserFile.isBlocked(user,friend)){  // block nabashd
            if(edited) {
                edited=false;
                messageFile.editMessage(this.selectedMessage,new Message(selectedMessage.getSenderUsername(),
                        selectedMessage.getReceiverUsername(),messageFLD.getText(), selectedMessage.getDateTime()));
                addChatToTable();
            }
            else {

                new Thread(){
                    public void run(){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                String dateTime= getNowDateTime();
                                String message = messageFLD.getText();
                                messageFile.addMessage(message,user.getUsername(),friend.getUsername(),
                                        dateTime);
                                addChatToTable();
                                hotKey(dateTime, message);
                            }
                        });
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                addChatToTable();
                            }
                        });
                    }
                }.start();

            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(" You can't message this user ");

            alert.showAndWait();
        }

    }
    public void hotKey( String dateTime,String message){
        Scene scene= sendBTN.getScene();
        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.B),
                new Runnable() {
                    @Override
                    public void run() {
                        friendsMessageFile.deleteMessage(new Message(user.getUsername(),friend.getUsername()
                                ,message,dateTime));
                    }
                }
        );


    }
//    public void addChatToTableAfterEditing(){
//        ArrayList<Message> messagesArrayList = new ArrayList<>();
//        messagesArrayList.addAll(messageFile.getUserMessages(user.getId(),friend.getId()));
//        ObservableList<Message> messages= FXCollections.observableArrayList(messagesArrayList);
//        chatTable.setItems(messages);
//        messageFLD.setText("");
//    }

    public void addChatToTable(){
        ArrayList<Message> messagesArrayList = new ArrayList<>();
        messagesArrayList.addAll(messageFile.getUserMessages(user.getId(),friend.getId()));
        ObservableList<Message> messages = FXCollections.observableArrayList(messagesArrayList);
        chatTable.setItems(messages);
        messageFLD.setText("");
    }

    @FXML
    void editHandler(ActionEvent event){
        errorLBL.setText("");
        this.selectedMessage = chatTable.getSelectionModel().getSelectedItem();
        if(this.selectedMessage != null){
            if(selectedMessage.getSenderUsername().equals(user.getUsername())){
                messageFLD.setText(selectedMessage.getMessage());   // show message in message fld for edite
                edited=true;
            }
            else {
                errorLBL.setText("You can not edit your friend Message");
            }

        }
        else
            errorLBL.setText("Select message");
    }

    @FXML
    void clearHistoryHandler(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(Main.class.getResource("/clearHistory.fxml"));
        loader.load();
        ClearHController clearHistoryController= loader.getController();
        Stage stage = new Stage();
        stage.setScene(new Scene((Parent) loader.getRoot()));
        stage.setTitle("Login Page");
        stage.setResizable(false);
        clearHistoryController.initFunction(stage,user,friend,this,menucontroller);
        stage.show();
    }

    @FXML
    void deleteMessageHandler(ActionEvent event) {
        errorLBL.setText("");
        this.selectedMessage= chatTable.getSelectionModel().getSelectedItem();
        if(selectedMessage != null){
            if( selectedMessage.getSenderUsername().equals(user.getUsername())){
                messageFile.deleteMessage(this.selectedMessage);
                ArrayList<Message> messagesAfterDeleting = messageFile.getUserMessages(
                        userFile.getUserId(selectedMessage.getSenderUsername()),
                        userFile.getUserId(selectedMessage.getReceiverUsername()));
                ObservableList<Message> messages = FXCollections.observableArrayList(messagesAfterDeleting);
                chatTable.setItems(messages);
            }
            else
                errorLBL.setText("You can not delete your friend Message");
        }
        else
            errorLBL.setText("Select a message");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        senderUsernameColumn.setCellValueFactory(new PropertyValueFactory<Message,String>("senderUsername"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<Message,String>("message"));
        dateAndTimeColumn.setCellValueFactory(new PropertyValueFactory<Message,String>("dateTime"));
    }

    @FXML
    void openLinkHandler(ActionEvent event) throws IOException {
        Message selectedMessage = chatTable.getSelectionModel().getSelectedItem();
        if(selectedMessage != null){
            Group selectedGroup = groupsFile.getGroup(selectedMessage.getMessage());
            if( ( selectedMessage.getMessage().charAt(0) == '@' ) &&
                    (!groupsFile.linkNotExist(selectedMessage.getMessage()) &&
                            groupsFile.isAdmin(selectedGroup,selectedMessage.getSender())
                            && isNotMember(user,selectedGroup)
                            && !(selectedMessage.getSenderUsername().equals(user.getUsername())))){

                FXMLLoader loader= new FXMLLoader(Main.class.getResource("/openLinkView.fxml"));
                loader.load();
                OpenLinkController openLinkController= loader.getController();
                Stage stage = new Stage();
                stage.setScene(new Scene((Parent) loader.getRoot()));
                stage.setTitle("Open Link");
                stage.setResizable(false);
                openLinkController.initFunction(stage,selectedMessage.getMessage() , user);
                stage.show();

            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(dialogStage);
                alert.setTitle("Invalid Fields");
                alert.setHeaderText("Please correct invalid fields");
                alert.setContentText("Select link ");

                alert.showAndWait();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Select message ");

            alert.showAndWait();
        }
    }
    public boolean isNotMember(Person user , Group group){
        if(group.getMembers() != null){

            for(int i=0 ; i < group.getMembers().size() ; i++){
                if(user.getId() == group.getMembers().get(i).getId()){
                    return false;
                }
            }
        }
        return true;

    }


}
