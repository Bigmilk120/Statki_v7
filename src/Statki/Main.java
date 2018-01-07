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
import javafx.scene.layout.VBox;
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
    double layoutX = 0.5;
    double layoutY = 0.5;
    Pole pole2;
    VBox planszaG;
    VBox planszaK;
    int typ = 4;
    int ilosc = 1;

    public boolean rozpoczecie = false;


    public void dodajStatkiG(int typ, int ilosc, boolean poz, MouseEvent event){
        Pole p3 = (Pole)event.getSource();
        if(poz)
        {
            for (int i = 0; i < typ; i++) {
                Pole p4 = getPole(p3.x, p3.y);
                p3.x++;
                p4.setFill(Color.BROWN);
                p4.setStroke(Color.BLACK);
            }
        }else
        {
            for (int i = 0; i < typ; i++) {
                Pole p4 = getPole(p3.x, p3.y);
                p3.y++;
                p4.setFill(Color.BROWN);
                p4.setStroke(Color.BLACK);
            }
        }
    }



    public void dodajPlanszeGracz(){


        planszaG = new VBox();

        for (int y = 0; y < 10; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 10; x++) {
                Pole c = new Pole(x, y);
                row.getChildren().add(c);

                c.addEventFilter(MouseEvent.MOUSE_PRESSED, event ->
                {
                    dodajStatkiG(typ,ilosc,event.getButton() == MouseButton.PRIMARY,event);
                    if((typ==4&&ilosc==1)||(typ==3&&ilosc==2)||(typ==2&&ilosc==3)||(typ==1&&ilosc==4)){
                        typ--;
                        ilosc=0;
                    }
                        ilosc++;
                });
            }

            planszaG.getChildren().add(row);
        }

        planszaG.setLayoutX(80.0);
        planszaG.setLayoutY(228.0);
        planszaG.setPrefHeight(291);
        planszaG.setPrefWidth(308);

    }

    public void dodajPlanszeKomputer(){

        planszaK = new VBox();

        layoutX = 0.5;
        layoutY = 0.5;

        for (int y = 0; y < 10; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 10; x++) {
                Pole c = new Pole(x, y);
                row.getChildren().add(c);
            }

            planszaK.getChildren().add(row);
        }

                // pole.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {

                //   if(event.getButton() == MouseButton.PRIMARY)  pole.setFill(Color.DARKGRAY);
                //    if(event.getButton() == MouseButton.SECONDARY)  pole.setFill(Color.DARKBLUE);

                //});
        planszaK.setLayoutX(477.0);
        planszaK.setLayoutY(228.0);
        planszaK.setPrefHeight(291);
        planszaK.setPrefWidth(308);

    }

    private void ustawienieZdjec() {
        tlo = new ImageView();
        tlo.setImage(new Image(getClass().getResource("tlo.png").toExternalForm()));
        tlo.setFitWidth(800.0);
        tlo.setFitHeight(600.0);
        tlo.setId("tlo");
        tlo.setOpacity(0.5);

        planszaI = new ImageView();
        planszaI.setImage(new Image(getClass().getResource("plansza.png").toExternalForm()));
        planszaI.setLayoutX(50.0);
        planszaI.setLayoutY(200.0);
        planszaI.setFitHeight(291.0);
        planszaI.setFitWidth(303.0);
        planszaI.setSmooth(false);

        planszaII = new ImageView();
        planszaII.setImage(new Image(getClass().getResource("plansza.png").toExternalForm()));
        planszaII.setLayoutX(447.0);
        planszaII.setLayoutY(200.0);
        planszaII.setFitHeight(291.0);
        planszaII.setFitWidth(303.0);
        planszaII.setSmooth(false);
    }

    public Pole getPole(int x, int y) {
        return (Pole)((HBox)planszaG.getChildren().get(y)).getChildren().get(x);
    }

    private Parent gvk(){

        BorderPane root = new BorderPane();
        root.setPrefSize(800, 600);

        ustawienieZdjec();
        dodajPlanszeGracz();
        dodajPlanszeKomputer();

    /*  planszaK.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for(Node node : planszaK.getChildren())

            }
        });

      */
        System.out.println(getPole(2,3));


      /*  planszaG.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                Pole pole = (Pole) event.getSource();
                pole.setFill(Color.DARKGRAY);
            }
        });*/



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
