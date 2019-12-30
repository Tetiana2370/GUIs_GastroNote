package sample.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

public class AddDiscountController implements Editable {
    @FXML
    RadioButton rb1, rb2, rb3, rb4, rb5; //5 is maximum
    //temporary groups
    double chosenDiscountValue = 0, acceptedDiscountValue = 0;
    // to do: add window closing on "Anuluj" and "OK" buttons
    // to do: save chosen discount and show it when the discount window is opened again
    public void set(ActionEvent actionEvent) {
        if(rb1!= null && rb1.isSelected()){
            this.chosenDiscountValue = 15;
        }else if(rb2!= null && rb2.isSelected()){
            this.chosenDiscountValue = 25;
        }else if(rb3!= null && rb3.isSelected()){
            this.chosenDiscountValue = 50;
        }else if(rb4!= null && rb4.isSelected()){
            this.chosenDiscountValue = 100;
        }else if(rb5!= null && rb5.isSelected()){
            this.chosenDiscountValue = 0;
        }

    }

    public void accept(ActionEvent actionEvent){
        if(this.chosenDiscountValue != 0){
            this.acceptedDiscountValue = this.chosenDiscountValue;
        }
        close();
    }

    public void cancel(ActionEvent actionEvent) {
        this.chosenDiscountValue = 0;
        close();
    }

    public void close(){
        ((Stage) rb1.getScene().getWindow()).close();
    }


}
