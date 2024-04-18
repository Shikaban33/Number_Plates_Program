import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DatabaseOp;
import java.io.IOException;
import numberPlateClass.numberPlate;


public class StartGui extends Application {

    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("View/main-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Main page");
        stage.setScene(scene);
        stage.show();
        //DatabaseOp.connectToDb();
    }
    public static void main(String[] args) {launch();}
}
