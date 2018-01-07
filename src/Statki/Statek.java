package Statki;

import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Statek extends Parent{

    private Integer typStatku;
    private boolean poziomo = true;
    private Integer iloscPkt;
    private Double xMyszy;
    private Double yMyszy;

    public Statek(Integer typ, boolean poz,  MouseEvent event, Main planszaG){
        this.typStatku = typ;
        this.poziomo = poz;

        Plansza.Pole p3 = (Plansza.Pole)event.getSource();

        if(poz)
        {
            for (int i = 0; i < typ; i++) {
                Plansza.Pole p4 = planszaG.getPole(p3.x,p3.y);
                p3.x++;
                p4.setFill(Color.BROWN);
                p4.setStroke(Color.BLACK);
            }
        }else
        {
            for (int i = 0; i < typ; i++) {
                Plansza.Pole p4 = planszaG.getPole(p3.x, p3.y);
                p3.y++;
                p4.setFill(Color.BROWN);
                p4.setStroke(Color.BLACK);
            }
        }
    }


}
