package sample.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuPanelController {
    @FXML
    Button btn;

    public void hidePanel(MouseEvent mouseEvent) {
        ((Stage)(btn.getScene().getWindow())).close();

    }


    public void showReport(MouseEvent mouseEvent) {
        openInNewWindow("report.fxml");
    }

    public void openInNewWindow(String relativePath){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(relativePath));
            /*
             * if "fx:controller" is not set in fxml
             * fxmlLoader.setController(NewWindowController);
             */
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);

            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public void close(MouseEvent mouseEvent){
        ((Stage) (btn.getScene().getWindow())).close();
    }
}
