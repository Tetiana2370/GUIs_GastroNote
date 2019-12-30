package sample.views;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AddNoteController implements Editable{


   @FXML
   TextArea textArea;
   private String note;

   @Override
   public void set(ActionEvent actionEvent) {
      if(textArea.getText() != null){
         this.note = textArea.getText();
      }
   }

   @Override
   public void accept(ActionEvent actionEvent) {
      set(actionEvent);
      //...data base actions

      close();
   }

   @Override
   public void cancel(ActionEvent actionEvent) {
      this.note = "";
      close();
   }

   @Override
   public void close() {
      ((Stage)(textArea.getScene().getWindow())).close();
   }
}
