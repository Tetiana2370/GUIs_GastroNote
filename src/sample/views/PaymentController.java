package sample.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import sample.CurrentUser;
import sample.Order;
import sample.User;
import sample.views.enums.Payment;


public class PaymentController implements Editable{

    @FXML
    RadioButton rb1, rb2, rb3, rb4, rb5;
    Payment accepted, chosen;
    Order currentOrder = CurrentUser.getInstance().getCurrentOrder();

    public void set(ActionEvent actionEvent) {
        if (rb1 != null && rb1.isSelected()) {
            this.chosen = Payment.CASH;
            currentOrder.setPayment(Payment.CASH);
        } else if (rb2 != null && rb2.isSelected()) {
            this.chosen = Payment.CARD;
            currentOrder.setPayment(Payment.CARD);
        } else if (rb3 != null && rb3.isSelected()) {
            this.chosen = Payment.TRANSFER;
            currentOrder.setPayment(Payment.TRANSFER);
        } else if (rb4 != null && rb4.isSelected()) {
            this.chosen = Payment.ONLINE;
            currentOrder.setPayment(Payment.ONLINE);
        } else if (rb5 != null && rb5.isSelected()) {
            this.chosen = null;
        }
    }

    public void cancel(ActionEvent actionEvent) {
        this.chosen = this.accepted;
        close();
    }

    public void accept(ActionEvent actionEvent) {
        this.accepted = this.chosen;
        close();
    }

    public void close(){
        ((Stage) rb1.getScene().getWindow()).close();
    }

}
