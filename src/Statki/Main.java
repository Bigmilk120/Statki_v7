package Statki;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.swing.*;
import javafx.scene.image.*;

import Statki.Plansza.Pole;
import Statki.Plansza.Pole.*;


import static javafx.scene.input.MouseEvent.*;

public class Main extends Application {

    public ImageView tlo;
    public ImageView planszaI;
    public ImageView planszaII;
    public Plansza gracz, komputer;
    GridPane planszaG;
    GridPane planszaK;


    public void dodajPlanszeGracz(){

       planszaG = new GridPane();

        for(int x=0;x<10;x++){

            for(int y=0;y<10;y++) {

                Pole pole = new Pole(x,y);
                GridPane.setRowIndex(pole,x);
                GridPane.setColumnIndex(pole,y);

                pole.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> pole.setFill(Color.DARKGRAY));

                planszaG.getChildren().add(pole);
            }
        }



        planszaG.setLayoutX(80.0);
        planszaG.setLayoutY(228.0);
        planszaG.setPrefHeight(291);
        planszaG.setPrefWidth(308);

    }

    public void dodajPlanszeKomputer(){

        planszaK = new GridPane();

        for(int x=0;x<10;x++){

            for(int y=0;y<10;y++) {

                Pole pole2 = new Pole(x, y);
                GridPane.setRowIndex(pole2, x);
                GridPane.setColumnIndex(pole2, y);

                planszaK.getChildren().add(pole2);

                pole2.setFill(Color.LIGHTGRAY);
                pole2.setStroke(Color.BLACK);

                pole2.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {

                    if (event.getButton() == MouseButton.PRIMARY) pole2.setFill(Color.DARKGRAY);
                    if (event.getButton() == MouseButton.SECONDARY) pole2.setFill(Color.DARKBLUE);

                });

            }
        }



        planszaK.setLayoutX(477.0);
        planszaK.setLayoutY(228.0);
        planszaK.setPrefHeight(291);
        planszaK.setPrefWidth(308);

    }

    private void ustawienieZdjec() {
        tlo = new ImageView();
        tlo.setImage(new Image(getClass().getResource("/obrazy/tlo.png").toExternalForm()));
        tlo.setFitWidth(800.0);
        tlo.setFitHeight(600.0);
        tlo.setId("tlo");
        tlo.setOpacity(0.5);

        planszaI = new ImageView();
        planszaI.setImage(new Image(getClass().getResource("/obrazy/plansza.png").toExternalForm()));
        planszaI.setLayoutX(50.0);
        planszaI.setLayoutY(200.0);
        planszaI.setFitHeight(291.0);
        planszaI.setFitWidth(303.0);
        planszaI.setSmooth(false);

        planszaII = new ImageView();
        planszaII.setImage(new Image(getClass().getResource("/obrazy/plansza.png").toExternalForm()));
        planszaII.setLayoutX(447.0);
        planszaII.setLayoutY(200.0);
        planszaII.setFitHeight(291.0);
        planszaII.setFitWidth(303.0);
        planszaII.setSmooth(false);
    }

    private Parent gvk(){

        BorderPane root = new BorderPane();
        root.setPrefSize(800, 600);

        ustawienieZdjec();
        dodajPlanszeGracz();
        dodajPlanszeKomputer();


        root.getChildren().add(tlo);
        root.getChildren().add(planszaI);
        root.getChildren().add(planszaII);
        root.getChildren().add(planszaG);
        root.getChildren().add(planszaK);

        return root;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        Scene scene = new Scene(gvk());
        primaryStage.setTitle("Gracz kontra Komputer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
