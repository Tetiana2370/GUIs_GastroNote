package sample.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.views.enums.Payment;

import java.util.Random;

public class ReportController {



    @FXML
    Button btn;
    @FXML
    GridPane gridPane;

    public void initialize(){
        var columns = gridPane.getColumnConstraints();
        int row = 0;
        for(Payment p : Payment.values()){
            Label l = new Label(p.toString());
            l.getStyleClass().add("report_label");

            gridPane.add(l, 0, row);
            System.out.println(l.getText());
            row++;
        }

        double sum = 0;

        for(int i=0; i < row; i++){
            Random rand = new Random();
            double val = Math.abs(rand.nextInt()%5000);
            sum+= val;
            Label l = new Label(String.valueOf(val));
            l.getStyleClass().add("report_label");

            gridPane.add(l, 1, i);

        }


//        add total
        Label l1 = new Label("Obrót całkowity");
        l1.getStyleClass().add("report_label_total");
        gridPane.add(l1, 0, row);

        Label l2 = new Label(String.valueOf(sum));
        l2.getStyleClass().add("report_label_total");
//
        gridPane.add(l2, 1, row);




    }

    public void show(MouseEvent mouseEvent){
//        var columns = gridPane.getColumnConstraints();
//
//        for(Payment p : Payment.values()){
//            Label l = new Label(p.toString());
//            System.out.println(l.getText());
//        }

    }

    public void close(MouseEvent mouseEvent){
        ((Stage) (btn.getScene().getWindow())).close();
    }
}
