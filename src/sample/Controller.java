package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    @FXML
    GridPane order_grid_pane;
    ArrayList<Button> prButtons = new ArrayList<>();
    ArrayList<Label> countLabels = new ArrayList<>();
    ArrayList<Integer> rowIndeces = new ArrayList<>();





    public void addToOrder(MouseEvent mouseEvent){
//
        Button btn = (Button)(mouseEvent.getSource());
        for(Button o: prButtons){
            if(o.getText() == btn.getText()){
                changeCount(null, +1, prButtons.indexOf(o) );
                return;
            }
        }
        Button newButton = new Button(btn.getText());
        Label newLabel = new Label("1x");
        newLabel.getStyleClass().add("big_label");

        Button roundButton = new Button("+");
        roundButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeCount(event, 1, -1);
            }
        });
        roundButton.getStyleClass().add("round_button");
        Button roundButton2 = new Button("-");
        roundButton2.getStyleClass().add("round_button");
        roundButton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeCount(event, -1, -1);
            }
        });

        int row = order_grid_pane.getRowCount();
        int col = 0;

//        //inserting
//
        order_grid_pane.add(newLabel,0, row);
        order_grid_pane.add(newButton,1, row);
        order_grid_pane.add(roundButton,2, row);
        order_grid_pane.add(roundButton2,3, row);

        prButtons.add(newButton);
        countLabels.add(newLabel);
        rowIndeces.add(row);

    }
    // operation > 0: add, < 0: substract
    public void changeCount(ActionEvent mouseEvent, int operation, int i){

        int index;
        if(i != -1){
            index = i;
        }else{
            Node node = (Node) (mouseEvent.getSource());
            index = GridPane.getRowIndex(node);
            if(index == -1){
                System.out.println("some error in addOneMore()");
                return;
            }
        }
        System.out.println("index = " + index);
        // get prev value and set new
        int count = Integer.parseInt(countLabels.get(index).getText().replace("x", ""));
        if(operation > 0){
            count += 1;
        }else{
            count -= 1;
            if(count <= 0){
                // to do: implement replacing in case of deleting position
                count = 0;
            }
        }

        countLabels.get(index).setText(String.valueOf(count)+"x");
        
    }
    public void setDiscount(MouseEvent mouseEvent) {
        openInNewWindow("views/addDiscount.fxml", "Wybierz zniżkę");
    }

    public void setPayment(MouseEvent mouseEvent) {
        openInNewWindow("views/payment.fxml", "Wybierz metodę płatności");
    }

    public void setNote(MouseEvent mouseEvent){
        openInNewWindow("views/addNote.fxml", "Dodaj notatkę");
    }

    public void openInNewWindow(String relativePath, String windowTitle){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(relativePath));
            /*
             * if "fx:controller" is not set in fxml
             * fxmlLoader.setController(NewWindowController);
             */
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle(windowTitle);
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);

            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public void showMenuPanel(MouseEvent mouseEvent) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("views/menuPanel.fxml"));
            /*
             * if "fx:controller" is not set in fxml
             * fxmlLoader.setController(NewWindowController);
             */
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);

            int width = (int) order_grid_pane.getScene().getWindow().getWidth();

            stage.setX(width - 150);
            stage.setY(45);
            stage.setWidth(150);

            stage.setScene(scene);
            System.out.println("hello3");
            stage.setAlwaysOnTop(true);
            stage.showAndWait();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }
}
