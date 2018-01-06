package Statki;

import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Statek extends Parent{

    private Integer typStatku;
    private boolean poziomo = true;
    private Integer iloscPkt;
    private Double xMyszy;
    private Double yMyszy;

    public Statek(Integer typ, boolean poziomo){
        this.typStatku = typ;
        this.poziomo = poziomo;


        HBox hbox = new HBox();
        for (int i = 0; i < typ; i++) {
            Rectangle pole = new Rectangle(25, 25);
            pole.setFill(null);
            pole.setStroke(Color.BLACK);
            hbox.getChildren().add(pole);
        }
        getChildren().add(hbox);
    }


}
