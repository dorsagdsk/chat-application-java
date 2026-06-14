package Controller;
import Model.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class StartPageController {
        private Stage primarystage=new Stage();
        private Stage stage;
        private ArrayList<Person> users = new ArrayList<>();
        private Person user;

        public void initFunction(Stage primarystage , ArrayList<Person> users , Person user){
                this.users = users;
                this.user = user;
        }
        public void  initFunction1(Stage stage){
                this.stage = stage;
        }

        @FXML
        private Button BTNlodin;

        @FXML
        private Button BTNregister;

        @FXML
        void loginAc(ActionEvent event) throws IOException { ///////***  varad page login
                Stage stage=(Stage)BTNlodin.getScene().getWindow();
                stage.close();
                FXMLLoader loader= new FXMLLoader(this.getClass().getResource("../LoginView.fxml"));
                loader.load();
                LoginController loginPageController=loader.getController();
                loginPageController.initFunction(primarystage);
                primarystage.setScene(new Scene((Parent) loader.getRoot()));
                primarystage.show();
        }

        @FXML
        void registerAc(ActionEvent event) throws IOException{ // varag page regiser
                Stage stage=(Stage)BTNregister.getScene().getWindow();
                stage.close();
                FXMLLoader loader= new FXMLLoader(this.getClass().getResource("../RegisterView.fxml"));
                loader.load();
                primarystage.setScene(new Scene((Parent) loader.getRoot()));
                primarystage.show();
        }
}

