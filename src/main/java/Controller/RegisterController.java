package Controller;
import Model.Person;
import Model.Regex;
import Model.UserFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Random;

public class RegisterController {
       private Stage registerS = new Stage();
       private Stage dialogStage;
       public void setDialogStage(Stage dialogStage) {this.dialogStage = dialogStage;}
       UserFile userFile=new UserFile();
       Regex  regex = new Regex();
       String code1;

    public void initFunction(Stage registerS){
       this.registerS=registerS;
    }

        @FXML
        private TextField nameFLD;

        @FXML
        private TextField codeFLD;

        @FXML
        private TextField passFLD;


        @FXML
        private Button backBTN;

        @FXML
        private Button loginBTN;

        @FXML
        private TextField lastFLD;

        @FXML
        private TextField userFLD;
        @FXML
        private TextField emailFLD;

        @FXML
        private Button BTNreg;

        @FXML
       private void backAc(ActionEvent event) throws IOException {  // show StartPage
            Stage stage = (Stage) backBTN.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../StartPageView.fxml"));
            loader.load();
            registerS.setScene(new Scene(loader.getRoot()));
            registerS.show();

        }

        @FXML
        private void regAc(ActionEvent event) throws IOException{

            if (isInputValid()) {  // going to function check
                String password = doHashing(passFLD.getText()); // geting pass and make it hash
                Person user;
                user = new Person(nameFLD.getText(), lastFLD.getText(), userFLD.getText(),password, emailFLD.getText());
                if( userFile.userNotExist(user) && userFile.emailNotExist(emailFLD.getText())) {// takrari nabotan user snd email
                    userFile.addUser(user);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.initOwner(dialogStage);
                    alert.setTitle("Invalid Fields");
                    alert.setHeaderText("Please correct invalid fields");
                    alert.setContentText("Username is exist");

                    alert.showAndWait();
                }

                Stage stage = (Stage) BTNreg.getScene().getWindow();
                stage.close();
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../StartPageView.fxml"));
                loader.load();
                registerS.setScene(new Scene((Parent) loader.getRoot()));
                registerS.setTitle("gdsk program");
                registerS.show();
            }

        }

        @FXML
        private void checkAc(ActionEvent event){  // to check fld email if its null or  ok with regex
            if(emailFLD.getText()!=null&& emailFLD.getText().length() != 0 && regex.emailRegex(emailFLD.getText())==true  ){
                sendEmail(emailFLD.getText());
            }else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Invalid Fields");
                alert.setHeaderText("Please correct invalid fields");
                alert.showAndWait();
            }

        }
        @FXML
        private void loginAc(ActionEvent event)throws IOException{
            Stage StratPageS = new Stage();
            Stage stage=(Stage)loginBTN.getScene().getWindow();
            stage.close();
            LoginController loginController = new LoginController();
            Stage stage1 = new Stage();
            FXMLLoader loader= new FXMLLoader(this.getClass().getResource("../LoginView.fxml"));
            loader.load();
            loginController.initFunction(stage1);
            StratPageS.setScene(new Scene((Parent) loader.getRoot()));
            StratPageS.show();

        }
        private boolean isInputValid() {  // for check if the filed being null and regex
        String errorMessage = "";


        if (nameFLD.getText() == null || nameFLD.getText().length() == 0 || regex.nameRegex(nameFLD.getText()) == false) {
            errorMessage += "No valid first name!\n";
        }

        if (lastFLD.getText() == null || lastFLD.getText().length() == 0 || regex.nameRegex(lastFLD.getText()) == false) {
            errorMessage += "No valid last name!\n";
        }

        if (userFLD.getText() == null || userFLD.getText().length() == 0 || regex.usernameRegex(userFLD.getText()) == false) {
            errorMessage += "No valid USERNAME!\n";
        }

        if (passFLD.getText() == null || passFLD.getText().length() == 0 || regex.passwordRegex(passFLD.getText()) == false) {
            errorMessage += "No valid PASSWORD!\n";

        }
        if ( userFile.emailNotExist(emailFLD.getText())==false|| emailFLD.getText() == null || emailFLD.getText().length() == 0 || regex.emailRegex(emailFLD.getText()) == false ) {
            errorMessage += "No valid Email!\n";

        }
        if (codeFLD.getText() == null || codeFLD.getText().length() == 0 ||codeFLD.getText().equals(code1)==false) {
            errorMessage += "No valid code!\n";

        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }


    }
    public String doHashing(String password){   // the pass wil be hash
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(password.getBytes());
            byte[] bytes = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for(byte b : bytes){
                stringBuilder.append(String.format("%02x",b));
            }
            return String.valueOf(stringBuilder);

        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public void sendEmail(String email){   // for sending the code to email
        // Recipient's email ID needs to be mentioned.
        String to = email;

        // Sender's email ID needs to be mentioned
        String from = "dorikhbazgdsk7@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("dorikhbazgdsk7@gmail.com", "09106411044khabaz");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            int a[]=new int[4];   // genarat a random code
            Random rand=new Random();
            String code="";
            for(int i=0;i<4;i++) {
                a[i] = rand.nextInt(10);
                String s = Integer.toString(a[i]);
                code +=s;
            }
             code1= code;
            // Now set the actual message
            message.setText(code);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }


}

