package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OpenOrdersController {

    @FXML
    TilePane open_order_box;
    User user = CurrentUser.getInstance();
    //VBOX is connected with specific order number
    HashMap<Integer, VBox> order_tiles = new HashMap<>();


    public void initialize(){
        showOpenedOrders();
    }

    void showOpenedOrders(){
        for(var number : user.getOpenOrders()){
            createOrderTile(user.getOrder(number));
        }
    }

    void createOrderTile(Order currentOrder){
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPrefHeight(205);
        vbox.setPrefWidth(225);
        vbox.getStyleClass().add("open_order_tile");
        vbox.setId(String.valueOf(currentOrder.getIndex()));

        Label numberLabel = new Label(String.valueOf(currentOrder.getIndex()));
        numberLabel.getStyleClass().add("big_label");
        numberLabel.setPrefHeight(43);
        numberLabel.setPrefWidth(73);

        Label listLabel = new Label();
        listLabel.getStyleClass().add("open_order_list_label");
        listLabel.setId("open_order_list_" + String.valueOf(currentOrder.getIndex()));
        listLabel.setOnMouseClicked(event -> {
            Label orderLabel = (Label) event.getSource();
            System.out.println(orderLabel.getText());
            int orderIndex = Integer.parseInt(orderLabel.getId().substring("open_order_list_".length(),
                    orderLabel.getId().length()));
            System.out.println("number of order is: " + orderIndex);

            user.setCurrentOrder(orderIndex);
            closeWindow(null);

        });
        //open order on clicking

        writeOrderToLabel(listLabel, currentOrder);

        Label paymentLabel = new Label();
        paymentLabel.setId("payment_label_" + String.valueOf(currentOrder.getIndex()));
        if(currentOrder.isPaid()){
            paymentLabel.setText("ZAPŁACONE: " + String.valueOf(currentOrder.getSum()));
        }else{
            paymentLabel.setText("NIEZAPŁACONE: " + String.valueOf(currentOrder.getSum()));
        }
        paymentLabel.setPrefWidth(240);
        paymentLabel.setPrefHeight(45);
        paymentLabel.getStyleClass().add("big_label");

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setId("order_status_" + String.valueOf(currentOrder.getIndex()));
        choiceBox.setPrefHeight(27);
        choiceBox.setPrefWidth(220);

        choiceBox.setItems(FXCollections.observableArrayList("Nowe", "W przygotowaniu", "Wydane" ));


        choiceBox.setOnAction(event -> {
            int orderNumber = Integer.parseInt(choiceBox.getParent().getId());
            if(choiceBox.valueProperty().get().equals("Wydane") && user.getOrder(orderNumber).isPaid() ){
                open_order_box.getChildren().remove(choiceBox.getParent());
                user.closeOrder(user.getCurrentOrder().getIndex());
            }
            System.out.println(choiceBox.valueProperty().get());
        });

        vbox.getChildren().addAll(numberLabel, listLabel, paymentLabel, choiceBox);
        open_order_box.getChildren().add(vbox);
        order_tiles.put(currentOrder.getIndex(), vbox);
    }

    void writeOrderToLabel(Label l, Order order){
        StringBuilder sb = new StringBuilder();
        for(Product product : order.getItems().keySet()){
            sb.append(product.getName() );
            sb.append("\n");
        }

        l.setText(sb.toString());
    }

    public void showMenuPanel(MouseEvent mouseEvent) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("views/menuPanel.fxml"));

            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);

            int width = (int) open_order_box.getScene().getWindow().getWidth();

            stage.setX(width - 150);
            stage.setY(45);
            stage.setWidth(150);

            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            stage.showAndWait();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window.", e);
        }
    }

    public void closeWindow(MouseEvent mouseEvent) {
        ((Stage)(open_order_box.getScene().getWindow())).close();
    }


    public void goBack(MouseEvent mouseEvent) {

        user.setCurrentOrder(user.getEmptyOrder().getIndex());

        System.out.println("current order is " + user.getCurrentOrder().getIndex());
        System.out.println(user.getCurrentOrder().getItems());
        closeWindow(null);
    }
}
