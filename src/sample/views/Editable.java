package sample.views;

import javafx.event.ActionEvent;

public interface Editable {

    void set(ActionEvent actionEvent);
    void accept(ActionEvent actionEvent);
    void cancel(ActionEvent actionEvent);
    void close();

}
