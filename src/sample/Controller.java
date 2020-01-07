package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    User user = CurrentUser.getInstance();

    @FXML
    GridPane order_grid_pane;
    @FXML
    Label orderSum;

    ArrayList<Button> prButtons = new ArrayList<>();
    ArrayList<Label> countLabels = new ArrayList<>();
    ArrayList<Integer> rowIndexes = new ArrayList<>();


    public void addToOrder(MouseEvent mouseEvent){

        Button btn = (Button)(mouseEvent.getSource());
        for(Button o: prButtons){
            if(o.getText().equals(btn.getText())){
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

        //insert elements to gridPane
        order_grid_pane.add(newLabel,0, row);
        order_grid_pane.add(newButton,1, row);
        order_grid_pane.add(roundButton,2, row);
        order_grid_pane.add(roundButton2,3, row);

        prButtons.add(newButton);
        countLabels.add(newLabel);
        rowIndexes.add(row);
        //add to current order
        user.getCurrentOrder().addOne(btn.getText());
        //change sum on main screen(right-bottom)
        orderSum.setText(String.valueOf(user.getCurrentOrder().getSum()));



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
            user.getCurrentOrder().addOne(prButtons.get(index).getText());
        }else{
            count -= 1;
            user.getCurrentOrder().removeOne(prButtons.get(index).getText());
            if(count <= 0){
                // to do: implement replacing in case of deleting position
                count = 0;
                clearOrder();
                showOrderedProducts();
            }
        }

        System.out.println("count is : " + count);
        Label countLabel = (Label)(order_grid_pane.getChildren().get(order_grid_pane.getColumnCount()*(index)));
        countLabel.setText(String.valueOf(count) + "x");
//        countLabels.get(index).setText(String.valueOf(count)+"x");



        //change sum on main screen(right-bottom)
        orderSum.setText(String.valueOf(user.getCurrentOrder().getSum()));

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(relativePath));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle(windowTitle);
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);


            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(order_grid_pane.getScene().getWindow());

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

    public void showOpenOrders(MouseEvent mouseEvent) {

        openInNewWindow("openOrders.fxml", "GastroNote");
        // clearing window with current window
        clearOrder();
        openSelectedOrder();

    }

    public void openSelectedOrder(){
        showOrderedProducts();
    }


    public void showOrderedProducts(){

        Order currentOrder = user.getCurrentOrder();
        if(user.getCurrentOrder().isEmpty()){
            clearOrder();
            System.out.println("row_count + " + order_grid_pane.getRowCount() );
            return;
        }
        for(Product pr : currentOrder.getItems().keySet()){

            Label newLabel = new Label(String.valueOf(currentOrder.getItems().get(pr)) + "x");
            newLabel.getStyleClass().add("big_label");

            Button roundButton = new Button("+");
            roundButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    changeCount(event, 1, -1);
                }
            });

            Button newButton = new Button(pr.getName());

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

            //insert elements to gridPane
            order_grid_pane.add(newLabel,0, row);
            order_grid_pane.add(newButton,1, row);
            order_grid_pane.add(roundButton,2, row);
            order_grid_pane.add(roundButton2,3, row);


            // fill productButtons, countLabel & rowIndexes to manipulate current order
            prButtons.add(newButton);
            countLabels.add(newLabel);
            rowIndexes.add(row);

        }

        //change sum on main screen(right-bottom)
        orderSum.setText(String.valueOf(user.getCurrentOrder().getSum()));
    }


    public void clearOrder(){
        order_grid_pane.getChildren().remove(0, order_grid_pane.getRowCount() * order_grid_pane.getColumnCount());
        countLabels.clear();
        prButtons.clear();
        rowIndexes.clear();
    }







}
