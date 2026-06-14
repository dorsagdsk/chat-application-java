package Controller;

import Model.FriendsFile;
import Model.RequsetFile;
import Model.UserFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Model.Person;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowRequestFController implements Initializable {

        RequsetFile requestsFile=new RequsetFile();
        FriendsFile friendsFile=new FriendsFile();
        UserFile userFile=new UserFile();
        String username;

        private UserPageContrller menucontroller;
        private Stage showRequestsStage;
        private Stage dialogStage;

       public void initFunction1(Stage showRequestsStage,String username,UserPageContrller menucontroller ){
        this.showRequestsStage=showRequestsStage;
        this.menucontroller= menucontroller;
        this.username=username;
        updateTable();  // update kardan table show request

       }
       public void setDialogStage(Stage dialogStage) {
                this.dialogStage = dialogStage;
        }

        @FXML
        private TableColumn<Person, String> requestsColumn;

        @FXML
        private TableView<Person> requestsTable;

        @FXML
        private Button closeBTN;
        @FXML
        void addToFriendsHandler(ActionEvent event) {
                Person selectedUser = requestsTable.getSelectionModel().getSelectedItem();   // entkhab user
                if (selectedUser != null) {
                        friendsFile.addFriend(userFile.getUser(username), selectedUser);
                        requestsFile.acceptOrRejectRequest(selectedUser.getUsername(), username); // hazf requst az table
                        menucontroller.initFunction(menucontroller.getStage(),
                                friendsFile.getFriends(userFile.getUserId(username)) ,username);



                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.initOwner(dialogStage);
                        alert.setTitle("Invalid Fields");
                        alert.setHeaderText("Please correct invalid fields");
                        alert.setContentText(" well done");
                        alert.showAndWait();
                        showRequestsStage.close();

                } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.initOwner(dialogStage);
                        alert.setTitle("Invalid Fields");
                        alert.setHeaderText("Please correct invalid fields");
                        alert.setContentText(" Select a user");

                        alert.showAndWait();
                }
        }
        public void updateTable(){   // table requsta update  --->
                ArrayList<Person> applicants = new ArrayList<>();
                ArrayList<String> applicantsUsername = new ArrayList<>();
                applicantsUsername.addAll(requestsFile.getUserAllRequests(username));
                int size=  applicantsUsername.size();
                for (int i=0 ; i < size; i++ ){
                        applicants.add(userFile.getUser(applicantsUsername.get(i)));
                }
                ObservableList<Person> applicantUsers = FXCollections.observableArrayList(applicants);
                requestsTable.setItems(applicantUsers);

        }

        @FXML
        void closeHandler(ActionEvent event) throws IOException {
                Stage stage=(Stage)closeBTN.getScene().getWindow();
                stage.close();

              //  menucontroller.initFunction();
//                FXMLLoader loader= new FXMLLoader(this.getClass().getResource("../UserM.fxml"));
//                loader.load();
//                showRequestsStage.setScene(new Scene((Parent) loader.getRoot()));
//                showRequestsStage.setTitle("gdsk program ");
//                showRequestsStage.show();
       }

        @FXML
        void rejectHandler(ActionEvent event) {
                Person selectedUser = requestsTable.getSelectionModel().getSelectedItem();
                if(selectedUser != null){
                        requestsFile.acceptOrRejectRequest(selectedUser.getUsername(),username);
                        showRequestsStage.close();
                        menucontroller.initFunction(showRequestsStage,friendsFile.getFriends(userFile.getUserId(username)) ,username);
                }else
                {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.initOwner(dialogStage);
                        alert.setTitle("Invalid Fields");
                        alert.setHeaderText("Please correct invalid fields");
                        alert.setContentText(" Select a user");

                        alert.showAndWait();
                }
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
                requestsColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("username"));
        }
}

