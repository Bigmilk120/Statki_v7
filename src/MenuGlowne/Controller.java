package MenuGlowne;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.awt.*;
import java.awt.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import Statki.Main.*;

import static javax.swing.text.StyleConstants.Background;

public class Controller{

    public void handleButtonAction(javafx.event.ActionEvent actionEvent) {
        try{
           Statki.Main m = new Statki.Main();
           m.start(new Stage());
           
        }catch(Exception e){
            System.out.println("Can't load window");
        }
    }

}