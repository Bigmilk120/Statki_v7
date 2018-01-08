package Statki;

import Statki.Plansza.Pole;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
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
    public Pole Plansza[][] = new Pole[12][12];
    public Pole Plansza_G[][] = new Pole[12][12];

    
    final boolean sprawdz(int x,int y, int dl, boolean poziomo,Main plansza){
        
        if(poziomo){
            for(int i=0;i<dl;i++){
                if(plansza.getPole(x+i, y).getFill()==Color.BROWN || plansza.getPole(x, y) == null)
                    return false;
                else{
                    if(plansza.getPole(x+i,y-1).getFill()==Color.BROWN||plansza.getPole(x+i,y+1).getFill()==Color.BROWN)
                        return false;
                    else{
                        if(plansza.getPole(x-1,y).getFill()==Color.BROWN||plansza.getPole(x-1,y-1).getFill()==Color.BROWN||plansza.getPole(x-1,y+1).getFill()==Color.BROWN||plansza.getPole(x+dl,y).getFill()==Color.BROWN||plansza.getPole(x+dl,y-1).getFill()==Color.BROWN||plansza.getPole(x+dl,y+1).getFill()==Color.BROWN)
                            return false;
                    }
                }
            }
        }else{
            for(int i=0;i<dl;i++){
                if(plansza.getPole(x,y+i).getFill()==Color.BROWN)
                    return false;
                else{
                    if(plansza.getPole(x-1,y+i).getFill()==Color.BROWN||plansza.getPole(x+1,y+i).getFill()==Color.BROWN)
                        return false;
                    else{
                        if(plansza.getPole(x,y-1).getFill()==Color.BROWN||plansza.getPole(x-1,y-1).getFill()==Color.BROWN||plansza.getPole(x+1,y-1).getFill()==Color.BROWN||plansza.getPole(x,y+dl).getFill()==Color.BROWN||plansza.getPole(x-1,y+dl).getFill()==Color.BROWN||plansza.getPole(x+1,y+dl).getFill()==Color.BROWN)
                            return false;
                    }
                }
            }
        }
        return true;
    }

    public Statek(Integer typ, boolean poz,  MouseEvent event, Main planszaG){
        this.typStatku = typ;
        this.poziomo = poz;
        

        Plansza.Pole p3 = (Plansza.Pole)event.getSource();
        
        if(poz)
        {
            if(sprawdz(p3.x,p3.y,typ,true,planszaG))
            for (int i = 0; i < typ; i++) {
                Plansza.Pole p4 = planszaG.getPole(p3.x,p3.y);
                p3.x++;
                p4.setFill(Color.BROWN);
                p4.setStroke(Color.BLACK);
            }
        }else
        {
           if(sprawdz(p3.x,p3.y,typ,false,planszaG))
            for (int i = 0; i < typ; i++) {
                Plansza.Pole p4 = planszaG.getPole(p3.x, p3.y);
                p3.y++;
                p4.setFill(Color.BROWN);
                p4.setStroke(Color.BLACK);
            }
        }
    }


}
