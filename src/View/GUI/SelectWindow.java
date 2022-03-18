package View.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SelectWindow extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("select_window.fxml"));
        Scene scene = new Scene(loader.load(), 1024, 768);
        stage.setScene(scene);
        stage.show();
    }

    public static void Main(String[] args) {
        launch();
    }
}
