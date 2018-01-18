package multiplayer;

import Statki.Plansza.Pole;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Klasa potrzebna do sprawdzania i tworzenia statków.
 * @author Krzysztof Bigos i Damian Czyż
 */

public class Statek extends Parent{

    /**
     * Zmienne potrzebne do sprawdzania i tworzenia statków.
     */
    
    final private Integer typStatku;
    private boolean poziomo = true;
    private Integer iloscPkt;

    
    /**
     * Funkcje sprawdzające czy można w danym miejscu na planszy stworzyć statek.
     * @param x pozycja x statku na planszy.
     * @param y pozycja y statku na planszy.
     * @param dl długość statku.
     * @param poziomo zmienna sprawdzająca czy statek jest poziomo czy pionowo.
     * @param plansza referencja do klasy Serwer/Client
     * @return zwraca czy można ustawić w danym miejscu statek.
     */
    
    final boolean sprawdz(int x,int y, int dl, boolean poziomo,Serwer plansza){
        
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
    final boolean sprawdz(int x,int y, int dl, boolean poziomo,Client plansza){
        
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
    
    /**
     * Konstruktory tworzące nowy statek.
     * @param typ zmienna zawierająca długość statki.
     * @param poz zmienna sprawdzająca czy statek jest poziomo czy pionowo.
     * @param event zmienna przechowująca event.
     * @param plansza referenecja do klasy Serwer/Client.
     */
    
    public Statek(Integer typ, boolean poz,  MouseEvent event, Client plansza){
        this.typStatku = typ;
        this.poziomo = poz;
        

        Plansza.Pole p3 = (Plansza.Pole)event.getSource();
        
        if(poz)
        {
            if(sprawdz(p3.x,p3.y,typ,true,plansza))
            for (int i = 0; i < typ; i++) {
                Plansza.Pole p4 = plansza.getPole(p3.x,p3.y,plansza.planszaG);
                plansza.Gracz[p3.x][p4.y] = 1;
                p3.x++;
                p4.setFill(Color.BROWN);
                p4.setStroke(Color.BLACK);                
            }
        }else
        {

           if(sprawdz(p3.x,p3.y,typ,false,plansza))
            for (int i = 0; i < typ; i++) {
                Plansza.Pole p4 = plansza.getPole(p3.x, p3.y,plansza.planszaG);
                plansza.Gracz[p3.x][p4.y] = 1;
                p3.y++;
                p4.setFill(Color.BROWN);
                p4.setStroke(Color.BLACK);
               
            }
        }

    }
    public Statek(Integer typ, boolean poz,  MouseEvent event, Serwer plansza){
        this.typStatku = typ;
        this.poziomo = poz;
        

        Plansza.Pole p3 = (Plansza.Pole)event.getSource();
        
        if(poz)
        {
            if(sprawdz(p3.x,p3.y,typ,true,plansza))
            for (int i = 0; i < typ; i++) {
                Plansza.Pole p4 = plansza.getPole(p3.x,p3.y,plansza.planszaG);
                plansza.Gracz[p3.x][p4.y] = 1;
                p3.x++;
                p4.setFill(Color.BROWN);
                p4.setStroke(Color.BLACK);                
            }
        }else
        {

           if(sprawdz(p3.x,p3.y,typ,false,plansza))
            for (int i = 0; i < typ; i++) {
                Plansza.Pole p4 = plansza.getPole(p3.x, p3.y,plansza.planszaG);
                plansza.Gracz[p3.x][p4.y] = 1;
                p3.y++;
                p4.setFill(Color.BROWN);
                p4.setStroke(Color.BLACK);
               
            }
        }

    }

}
