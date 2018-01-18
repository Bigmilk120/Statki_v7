package MenuGlowne;

import javafx.stage.Stage;

/**
 * Klasa obsługująca zdarzenia.
 * @author Dimm
 */

public class Controller{
    
    /**
     * Funkcja służąca do włączania gry Gracz vs Komputer.
     * @param actionEvent obsługuje kliknięcie w przycisk.
     */
    public void handleButtonAction(javafx.event.ActionEvent actionEvent) {
        try{
           Statki.Main m = new Statki.Main();
           m.start(new Stage());
           
        }catch(Exception e){
            System.out.println("Can't load window");
        }
    }
    
    /**
     * Funkcja służąca do włączania gry z pozycji Serwera.
     * @param actionEvent obsługuje kliknięcie w przycisk.
     */
    public void handleButtonAction2(javafx.event.ActionEvent actionEvent) {
        try{
          
           multiplayer.Serwer m = new multiplayer.Serwer();
           m.start(new Stage());

        }catch(Exception e){
            System.out.println("Can't load window");
        }
    }
    
    /**
     * Funkcja służąca do włączania gry z pozycji Clienta.
     * @param actionEvent obsługuje kliknięcie w przycisk.
     */
    public void handleButtonAction3(javafx.event.ActionEvent actionEvent) {
        try{
           multiplayer.Client m = new multiplayer.Client();
           m.start(new Stage());
           
        }catch(Exception e){
            System.out.println("Can't load window");
        }
    }
}
