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
import static Statki.Plansza.iloscStatkow;
import java.util.ArrayList;
import java.util.Random;

import static javafx.scene.input.MouseEvent.*;

public class Main extends Application {

    public ImageView tlo;
    public ImageView planszaI;
    public ImageView planszaII;
    Pole pole2;
    VBox planszaG;
    VBox planszaK;
    int typ = 4;
    int ilosc = 1;
    int iloscStatkow = 10;
    public ArrayList<Statek> tabStatkow = new ArrayList<Statek>();
  
    int[][] Komputer = new int[12][12];
    

    public boolean rozpoczecie = false;

    void losuj_statek(int dl){
        Random r = new Random();

        int K_los = r.nextInt(2);

        // Statek poziomo
        if(K_los == 0)
        {
            int X_los = r.nextInt(11-dl)+1;
            int Y_los = r.nextInt(10)+1;

            while(sprawdz(X_los,Y_los,dl,true,planszaK) == false)
            {
                X_los = r.nextInt(11-dl)+1;
                Y_los = r.nextInt(10)+1;
            }

            // Deklarowanie statku (4-pola)
            for(int i=0;i<dl;i++) {
                Komputer[X_los+i][Y_los] = 1;
               // getPole(X_los+i,Y_los,planszaK).setFill(Color.BROWN);
            }
            Statek s = new Statek(X_los,Y_los,dl,true,this,planszaK);
            tabStatkow.add(s);


            // Statek pionowo
        }else if(K_los == 1){
            int X_los = r.nextInt(10)+1;
            int Y_los = r.nextInt(11-dl)+1;

            while(sprawdz(X_los,Y_los,dl,false,planszaK) == false)
            {
                X_los = r.nextInt(10)+1;
                Y_los = r.nextInt(11-dl)+1;
            }

            //Deklarowanie statku

            for(int i=0;i<dl;i++)
            {
                Komputer[X_los][Y_los+i] = 1;
                //getPole(X_los,Y_los+i,planszaK).setFill(Color.BROWN);
            }
            Statek s = new Statek(X_los,Y_los,dl,false,this,planszaK);
            tabStatkow.add(s);
        }
    }

    public void ustawStatkiK(){

        losuj_statek(4);

        for(int i =0 ;i<2;i++)
            losuj_statek(3);

        for(int i =0 ;i<3;i++)    
            losuj_statek(2);

        for(int i =0 ;i<4;i++)
            losuj_statek(1);
   
    }
   
    boolean sprawdz(int x,int y, int dl, boolean poziomo,VBox plansza){

        if(poziomo){
            for(int i=0;i<dl;i++){
                if(this.getPole(x+i, y,plansza).getFill()==Color.BROWN)
                    return false;
                else{
                    if(this.getPole(x+i,y-1,plansza).getFill()==Color.BROWN||this.getPole(x+i,y+1,plansza).getFill()==Color.BROWN)
                        return false;
                    else{
                        if(this.getPole(x-1,y,plansza).getFill()==Color.BROWN||this.getPole(x-1,y-1,plansza).getFill()==Color.BROWN||this.getPole(x-1,y+1,plansza).getFill()==Color.BROWN||this.getPole(x+dl,y,plansza).getFill()==Color.BROWN||this.getPole(x+dl,y-1,plansza).getFill()==Color.BROWN||this.getPole(x+dl,y+1,plansza).getFill()==Color.BROWN)
                            return false;
                    }
                }
            }
        }else{
            for(int i=0;i<dl;i++){
                if(this.getPole(x,y+i,plansza).getFill()==Color.BROWN)
                    return false;
                else{
                    if(this.getPole(x-1,y+i,plansza).getFill()==Color.BROWN||this.getPole(x+1,y+i,plansza).getFill()==Color.BROWN)
                        return false;
                    else{
                        if(this.getPole(x,y-1,plansza).getFill()==Color.BROWN||this.getPole(x-1,y-1,plansza).getFill()==Color.BROWN||this.getPole(x+1,y-1,plansza).getFill()==Color.BROWN||this.getPole(x,y+dl,plansza).getFill()==Color.BROWN||this.getPole(x-1,y+dl,plansza).getFill()==Color.BROWN||this.getPole(x+1,y+dl,plansza).getFill()==Color.BROWN)
                            return false;
                    }
                }
            }
        }
        return true;
    }

    public void dodajPlanszeGracz(){

        planszaG = new VBox();

        for (int y = 0; y < 12; y++) {
            HBox row = new HBox();
            if(y==0||y==11)
                row.setVisible(false);
            for (int x = 0; x < 12; x++) {

                Pole c = new Pole(x, y);
                if(x==0||x==11)
                    c.setVisible(false);
                row.getChildren().add(c);

                c.addEventFilter(MouseEvent.MOUSE_PRESSED, event ->
                {
                    try{
                        Plansza.Pole p = (Plansza.Pole)event.getSource();
                        if(sprawdz(p.x,p.y,typ,event.getButton() == MouseButton.PRIMARY,planszaG))
                            new Statek(typ,event.getButton() == MouseButton.PRIMARY,event,this,planszaG);
                        else
                            ilosc--;
                        if((typ==4&&ilosc==1)||(typ==3&&ilosc==2)||(typ==2&&ilosc==3)||(typ==1&&ilosc==4)){
                            typ--;
                            ilosc=0;
                        }
                        ilosc++;



                    }catch(Exception e){}

                });
            }


            planszaG.getChildren().add(row);
        }

        planszaG.setLayoutX(52.0);
        planszaG.setLayoutY(202.0);
        planszaG.setPrefHeight(291);
        planszaG.setPrefWidth(308);

    }

     public void dodajPlanszeKomputer(){

        planszaK = new VBox();


        for (int y = 0; y < 12; y++) {
            HBox row = new HBox();
            if(y==0||y==11)
                row.setVisible(false);
            for (int x = 0; x < 12; x++) {
                Pole c = new Pole(x, y);
                if(x==0||x==11)
                    c.setVisible(false);

                c.addEventFilter(MouseEvent.MOUSE_PRESSED, event ->{

                    Pole p = (Pole)event.getSource();

                    if(Komputer[p.x][p.y] == 1)
                    {
                        getPole(p.x,p.y,planszaK).setFill(Color.RED);
                    }                      
                    else
                        getPole(p.x,p.y,planszaK).setFill(Color.BLACK);
                });

                row.getChildren().add(c);
            }

            planszaK.getChildren().add(row);

        }

        planszaK.setLayoutX(450.0);
        planszaK.setLayoutY(201.0);
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

    public Pole getPole(int x, int y,VBox plansza) {
        return (Pole)((HBox)plansza.getChildren().get(y)).getChildren().get(x);
    }
    
    public boolean koniecGry(Statek statek){
              
            statek.iloscPkt--;
            
            if(statek.iloscPkt == 0 )
                iloscStatkow--;
            
            if(iloscStatkow == 0)
                return true;
            
            return false;
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
        ustawStatkiK();

        return root;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        Scene scene = new Scene(gvk());
        primaryStage.setTitle("Gracz kontra Komputer");
        primaryStage.setScene(scene);
        primaryStage.setWidth(806.0);
        primaryStage.setHeight(630.0);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
 