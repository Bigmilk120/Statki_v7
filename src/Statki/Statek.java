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
                if(plansza.getPole(x+i, y,plansza.planszaG).getFill()==Color.BROWN || plansza.getPole(x, y,plansza.planszaG) == null)
                    return false;
                else{
                    if(plansza.getPole(x+i,y-1,plansza.planszaG).getFill()==Color.BROWN||plansza.getPole(x+i,y+1,plansza.planszaG).getFill()==Color.BROWN)
                        return false;
                    else{
                        if(plansza.getPole(x-1,y,plansza.planszaG).getFill()==Color.BROWN||plansza.getPole(x-1,y-1,plansza.planszaG).getFill()==Color.BROWN||plansza.getPole(x-1,y+1,plansza.planszaG).getFill()==Color.BROWN||plansza.getPole(x+dl,y,plansza.planszaG).getFill()==Color.BROWN||plansza.getPole(x+dl,y-1,plansza.planszaG).getFill()==Color.BROWN||plansza.getPole(x+dl,y+1,plansza.planszaG).getFill()==Color.BROWN)
                            return false;
                    }
                }
            }
        }else{
            for(int i=0;i<dl;i++){
                if(plansza.getPole(x,y+i,plansza.planszaG).getFill()==Color.BROWN)
                    return false;
                else{
                    if(plansza.getPole(x-1,y+i,plansza.planszaG).getFill()==Color.BROWN||plansza.getPole(x+1,y+i,plansza.planszaG).getFill()==Color.BROWN)
                        return false;
                    else{
                        if(plansza.getPole(x,y-1,plansza.planszaG).getFill()==Color.BROWN||plansza.getPole(x-1,y-1,plansza.planszaG).getFill()==Color.BROWN||plansza.getPole(x+1,y-1,plansza.planszaG).getFill()==Color.BROWN||plansza.getPole(x,y+dl,plansza.planszaG).getFill()==Color.BROWN||plansza.getPole(x-1,y+dl,plansza.planszaG).getFill()==Color.BROWN||plansza.getPole(x+1,y+dl,plansza.planszaG).getFill()==Color.BROWN)
                            return false;
                    }
                }
            }
        }
        return true;
    }

    public Statek(Integer typ, boolean poz,  MouseEvent event, Main plansza){
        this.typStatku = typ;
        this.poziomo = poz;
        

        Plansza.Pole p3 = (Plansza.Pole)event.getSource();
        
        if(poz)
        {
            if(sprawdz(p3.x,p3.y,typ,true,plansza))
            for (int i = 0; i < typ; i++) {
                Plansza.Pole p4 = plansza.getPole(p3.x,p3.y,plansza.planszaG);
                p3.x++;
                p4.setFill(Color.BROWN);
                p4.setStroke(Color.BLACK);
            }
        }else
        {
           if(sprawdz(p3.x,p3.y,typ,false,plansza))
            for (int i = 0; i < typ; i++) {
                Plansza.Pole p4 = plansza.getPole(p3.x, p3.y,plansza.planszaG);
                p3.y++;
                p4.setFill(Color.BROWN);
                p4.setStroke(Color.BLACK);
            }
        }
    }

}
