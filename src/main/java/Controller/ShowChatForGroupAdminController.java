package Controller;

import Model.Group;
import Model.GroupsMessageFile;
import Model.Message;
import Model.Person;
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

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowChatForGroupAdminController implements Initializable {

    private Stage showChatStage;
    private GroupsMessageFile groupsMessageFile;
    private Person admin;
    private Group group;
    private boolean edited=false;
    private Message selectedMessage;
   // private boolean unSend;

    public void initFunction(Stage showChatStage , Group group , Person admin){
        this.showChatStage=showChatStage;
        this.admin = admin;
        this.group=new Group(group.getId(),group.getAdmin(),group.getGroupName(),group.getLink(),group.getMembers());
        groupNameLBL.setText(group.getGroupName());
        groupsMessageFile= new GroupsMessageFile();
      //  unSend=true;
        addChatToTable();
    }


    @FXML
    private TableView<Message> chatTable;

    @FXML
    private TableColumn<Message, String> dateAndTimeColumn;

    @FXML
    private Label errorLBL;

    @FXML
    private Label groupNameLBL;

    @FXML
    private TableColumn<Message, String> messageColumn;

    @FXML
    private TextField messageFLD;

    @FXML
    private TableColumn<Message, String> senderUsernameColumn;
    @FXML
    private Button sendBTN;

    @FXML
    void clearHistoryHandler(ActionEvent event) {
        groupsMessageFile.clearHistory(group);
        addChatToTable();
    }

    @FXML
    void closeHandler(ActionEvent event) {
        showChatStage.close();
    }

    @FXML
    void deleteMessageHandler(ActionEvent event) {
        errorLBL.setText("");
        this.selectedMessage =chatTable.getSelectionModel().getSelectedItem();
        if(selectedMessage != null){
            if(selectedMessage.getSender().getId() == admin.getId()){
                groupsMessageFile.deleteMessage(selectedMessage);
                addChatToTable();
            }
            else
                errorLBL.setText("You can't delete your friend message");

        }
        else
            errorLBL.setText("Select a user");
    }

    @FXML
    void editHandler(ActionEvent event) {
        errorLBL.setText("");
        this.selectedMessage = chatTable.getSelectionModel().getSelectedItem();
        if(selectedMessage != null){
            if(selectedMessage.getSenderUsername().equals(admin.getUsername())){
                messageFLD.setText(selectedMessage.getMessage());
                edited=true;
            }
            else {
                errorLBL.setText("You can't edit your friend Message");
            }

        }
        else
            errorLBL.setText("Select message");
    }

    @FXML
    void kickMemberHandler(ActionEvent event) throws IOException {
        if(group.getMembers() != null){
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/RemoveMemberView.fxml"));
            loader.load();
            RemoveMemberController kickMemberController = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene((Parent) loader.getRoot()));
            stage.setTitle("Kick Member");
            stage.setResizable(false);
            kickMemberController.initFunction(stage, group,this);
            stage.show();
        }
        else
            errorLBL.setText("this group hasn't member");
    }

    public Stage getStage(){
        return showChatStage;
    }

    String getNowDateTime(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return (dateTimeFormatter.format(now));
    }
    @FXML
    void sendHandler(ActionEvent event) {
        if(edited){
            groupsMessageFile.editMessage(this.selectedMessage,new Message(admin.getUsername(),group.getId()
                    ,messageFLD.getText(),selectedMessage.getDateTime()));
            addChatToTable();
            edited=false;
        }
        else {

            new Thread(){
                public void run(){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            String dateTime= getNowDateTime();
                            String message = messageFLD.getText();
                            groupsMessageFile.addMessage(message,admin.getUsername(),group.getId(),
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

    public void hotKey( String dateTime , String message){
        Scene scene= sendBTN.getScene();
        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.B),
                new Runnable() {
                    @Override
                    public void run() {
                        groupsMessageFile.deleteMessage(new Message(admin.getUsername(),group.getId()
                                ,message,dateTime));

                    }
                }
        );
        }

    public void addChatToTable(){
        ArrayList<Message> messagesArrayList = new ArrayList<>();
       messagesArrayList.addAll(groupsMessageFile.getGroupMessages(group.getId()));
        ObservableList<Message> messages = FXCollections.observableArrayList(messagesArrayList);
        chatTable.setItems(messages);
        messageFLD.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateAndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        senderUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("senderUsername"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
    }
}
