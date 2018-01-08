package Statki;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

 

public class Plansza extends Parent{

    private GridPane plansza = new GridPane();
    public boolean przeciwnik = true;
    public int statki = 4;
    public static int iloscStatkow = 10;
    
    public Plansza(){
    }

    public static class Pole extends Rectangle{

        public int x, y;
        public Statek statek = null;
        public boolean wasShot = false;

        private Plansza plansza;

        public Pole(int x, int y) {
            super(x,y,26, 25);
            this.x = x;
            this.y = y;
            setFill(Color.LIGHTGRAY);
            setStroke(Color.BLACK);
        }

        public Pole(int x, int y, Statek statek) {
            super(x,y,26, 25);
            this.x = x;
            this.y = y;
            this.statek=statek;
            setFill(Color.LIGHTGRAY);
            setStroke(Color.BLACK);
        } 
    }

}