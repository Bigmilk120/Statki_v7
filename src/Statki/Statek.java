package Statki;
 
import Statki.Plansza.Pole;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Statek extends Parent{

    private Integer typStatku;
    private boolean poziomo = true;
    public int iloscPkt;
    
   final boolean sprawdz(int x, int y, int dl, boolean poziomo, Main plansza, VBox planszaW){

        if(poziomo){
            for(int i=0;i<dl;i++){
                if(plansza.getPole(x+i, y,planszaW).getFill()==Color.BROWN || plansza.getPole(x, y,planszaW) == null)
                    return false;
                else{
                    if(plansza.getPole(x+i,y-1,planszaW).getFill()==Color.BROWN||plansza.getPole(x+i,y+1,planszaW).getFill()==Color.BROWN)
                        return false;
                    else{
                        if(plansza.getPole(x-1,y,planszaW).getFill()==Color.BROWN||plansza.getPole(x-1,y-1,planszaW).getFill()==Color.BROWN||plansza.getPole(x-1,y+1,planszaW).getFill()==Color.BROWN||plansza.getPole(x+dl,y,planszaW).getFill()==Color.BROWN||plansza.getPole(x+dl,y-1,planszaW).getFill()==Color.BROWN||plansza.getPole(x+dl,y+1,planszaW).getFill()==Color.BROWN)
                            return false;
                    }
                }
            }
        }else{
            for(int i=0;i<dl;i++){
                if(plansza.getPole(x,y+i,planszaW).getFill()==Color.BROWN)
                    return false;
                else{
                    if(plansza.getPole(x-1,y+i,planszaW).getFill()==Color.BROWN||plansza.getPole(x+1,y+i,planszaW).getFill()==Color.BROWN)
                        return false;
                    else{
                        if(plansza.getPole(x,y-1,planszaW).getFill()==Color.BROWN||plansza.getPole(x-1,y-1,planszaW).getFill()==Color.BROWN||plansza.getPole(x+1,y-1,planszaW).getFill()==Color.BROWN||plansza.getPole(x,y+dl,planszaW).getFill()==Color.BROWN||plansza.getPole(x-1,y+dl,planszaW).getFill()==Color.BROWN||plansza.getPole(x+1,y+dl,planszaW).getFill()==Color.BROWN)
                            return false;
                    }
                }
            }
        }
        return true;
    }

    public Statek(Integer typ, boolean poz,  MouseEvent event, Main main, VBox plansza){
        this.typStatku = typ;
        this.poziomo = poz;
        this.iloscPkt = typ;

        Plansza.Pole p3 = (Plansza.Pole)event.getSource();

        if(poz)
        {
            if(sprawdz(p3.x,p3.y,typ,true,main, plansza))
            for (int i = 0; i < typ; i++) {
                Plansza.Pole p4 = main.getPole(p3.x,p3.y,plansza);
                p3.x++;
                p4.setFill(Color.BROWN);
                p4.setStroke(Color.BLACK);
            }
        }else
        {
            if(sprawdz(p3.x,p3.y,typ,true,main,plansza))
            for (int i = 0; i < typ; i++) {
                Plansza.Pole p4 = main.getPole(p3.x, p3.y,plansza);
                p3.y++;
                p4.setFill(Color.BROWN);
                p4.setStroke(Color.BLACK);
            }
        }
    }

    public Statek(int x, int y,Integer typ, boolean poz, Main main, VBox plansza){
        this.typStatku = typ;
        this.poziomo = poz;
        this.iloscPkt = typ;

        if(poz)
        {
            if(sprawdz(x,y,typ,true,main, plansza))
                for (int i = x; i < x+typ; i++) {
                    Pole p4 = main.getPole(i,y,plansza);
                    p4.setFill(Color.BROWN);
                    p4.setStroke(Color.BLACK);

                }
        }else
        {
            if(sprawdz(x,y,typ,false,main,plansza))
                for (int i = y; i < y+typ; i++) {
                    Pole p4 = main.getPole(x, i,plansza);
                    p4.setFill(Color.BROWN);
                    p4.setStroke(Color.BLACK);
                }
        }
    }

}
