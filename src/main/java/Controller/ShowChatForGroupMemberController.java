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

public class ShowChatForGroupMemberController implements Initializable {

    private Stage showChatStage;
    private Group group;
    private GroupsMessageFile groupsMessageFile;
    private Person user;
    private Message selectedMessage;
    private boolean edited=false;
    private Stage dialogStage;
    GroupsFile groupsFile=new GroupsFile();
    private UserPageContrller menucontroller;
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void initFunction(Stage showChatStage , Group group , Person user , UserPageContrller menucontroller){
        this.showChatStage=showChatStage;//
        this.group=group;//
        this.user=user;//
        groupsMessageFile=new GroupsMessageFile();
        groupNameLBL.setText(group.getGroupName());//
        this.menucontroller= menucontroller;

        addChatToTable();//

    }

    @FXML
    private TableView<Message> chatTable;
    @FXML
    private Button sendBTN;

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
    void closeHandler(ActionEvent event) {
        showChatStage.close();
    }

    @FXML
    void deleteMessageHandler(ActionEvent event) {
        errorLBL.setText("");
        this.selectedMessage =chatTable.getSelectionModel().getSelectedItem();
        if(selectedMessage != null){
            if(selectedMessage.getSender().getId() == user.getId()){
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
            if(selectedMessage.getSenderUsername().equals(user.getUsername())){
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

    String getNowDateTime(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return (dateTimeFormatter.format(now));
    }


    @FXML
    void sendHandler(ActionEvent event) {
        if(edited){
            groupsMessageFile.editMessage(this.selectedMessage,new Message(user.getUsername(),group.getId()
                    ,messageFLD.getText(),selectedMessage.getDateTime()));
            edited=false;
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
                            groupsMessageFile.addMessage(message,user.getUsername(),group.getId(),
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

    public void hotKey( String dateTime , String message) {
        Scene scene = sendBTN.getScene();
        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.B),
                new Runnable() {
                    @Override
                    public void run() {
                        groupsMessageFile.deleteMessage(new Message(user.getUsername(), group.getId()
                                , message, dateTime));
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        senderUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("senderUsername"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        dateAndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
    }
}
