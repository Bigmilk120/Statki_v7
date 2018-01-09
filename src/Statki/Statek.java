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
    
   final boolean sprawdz(int x, int y, int dl, boolean poziomo, Main main, VBox plansza){

        if(poziomo){
            for(int i=0;i<dl;i++){
                if(main.getPole(x+i, y,plansza).getFill()==Color.BROWN)
                    return false;
                else{
                    if(main.getPole(x+i,y-1,plansza).getFill()==Color.BROWN||main.getPole(x+i,y+1,plansza).getFill()==Color.BROWN)
                        return false;
                    else{
                        if(main.getPole(x-1,y,plansza).getFill()==Color.BROWN||main.getPole(x-1,y-1,plansza).getFill()==Color.BROWN||main.getPole(x-1,y+1,plansza).getFill()==Color.BROWN||main.getPole(x+dl,y,plansza).getFill()==Color.BROWN||main.getPole(x+dl,y-1,plansza).getFill()==Color.BROWN||main.getPole(x+dl,y+1,plansza).getFill()==Color.BROWN)
                            return false;
                    }
                }
            }
        }else{
            for(int i=0;i<dl;i++){
                if(main.getPole(x,y+i,plansza).getFill()==Color.BROWN)
                    return false;
                else{
                    if(main.getPole(x-1,y+i,plansza).getFill()==Color.BROWN||main.getPole(x+1,y+i,plansza).getFill()==Color.BROWN)
                        return false;
                    else{
                        if(main.getPole(x,y-1,plansza).getFill()==Color.BROWN||main.getPole(x-1,y-1,plansza).getFill()==Color.BROWN||main.getPole(x+1,y-1,plansza).getFill()==Color.BROWN||main.getPole(x,y+dl,plansza).getFill()==Color.BROWN||main.getPole(x-1,y+dl,plansza).getFill()==Color.BROWN||main.getPole(x+1,y+dl,plansza).getFill()==Color.BROWN)
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
