package multiplayer;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
 
/**
 * Klasa przechowująca klasę Pole.
 * @author Dimm
 */
public class Plansza extends Parent{
    /**
     * Domyślny konstruktor planszy.
     */
    public Plansza(){
    }
    /**
     * Klasa dziedzicząca od Rectangle, ma w sobie
     */
    public static class Pole extends Rectangle{
        /**
         * Zmienne potrzebne do inicjacji pola.
         */
        public int x, y;
        public boolean trafiony = false;
        private Plansza plansza;
        
        /**
         * Konstruktor pola.
         * @param x pozycja x planszy.
         * @param y pozycja y planszy.
         */
        public Pole(int x, int y) {
            super(x,y,26, 25);
            this.x = x;
            this.y = y;
            setFill(Color.LIGHTGRAY);
            setStroke(Color.BLACK);
        }
    }
}