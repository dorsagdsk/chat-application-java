import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
public class MainAsid extends Application{

    @Override
    public  void start(Stage primaryStage){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("StartPageView.fxml"));
        try{
            loader.load();
        }
        catch (IOException e){

            e.printStackTrace();
        }

        primaryStage.setScene(new Scene( loader.<Parent>getRoot()));
        primaryStage.setTitle("app");
        primaryStage.show();
        //primaryStage.setResizable(false);

    }
    public static void main(String[] args) {launch(args);}
}
