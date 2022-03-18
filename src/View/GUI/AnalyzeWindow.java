package View.GUI;

import Model.ProgramState;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AnalyzeWindow {
    public AnalyzeWindow(ProgramState state) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("analyze_window.fxml"));
            Scene scene = new Scene(loader.load(), 1024, 768);
            stage.setScene(scene);

            AnalyzeWindowController controller = loader.getController();
            controller.setState(state);
            stage.show();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
