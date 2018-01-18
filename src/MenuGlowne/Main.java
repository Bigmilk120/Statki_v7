package MenuGlowne;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Główna klasa gry, zawierająca główne menu.
 * @author Dimm
 */
public class Main extends Application  {
    /**
     * Funckja inicjująca menu główne.
     * @throws java.lang.Exception
    */
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
        primaryStage.setTitle("Statki");
        primaryStage.setScene(new Scene(root, 800, 600));        
        primaryStage.show();
        
    }

    /**
     * Funkcja wywołująca menu główne.
     * @param args zmienne dziedziczące z runnable, potrzebne do włączenia gry.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
