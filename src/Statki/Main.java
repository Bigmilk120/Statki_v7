package Statki;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.*;
import Statki.Plansza.Pole;
import java.util.Random;
import static javafx.application.Application.launch;

public class Main extends Application {

    /**
    *Inicjowanie zmiennych globalnych które są potrzebne do prawidłowego działania gry.
    */
    
    private ImageView tlo;
    private ImageView planszaI;
    private ImageView planszaII;
    private ImageView napis;
    private ImageView napis2;
    public VBox planszaG;
    public VBox planszaK;
    private int typ;
    private int ilosc;
    private boolean tura_gracza=true;
    private int tura=0;
    private int iloscPktZyciaGracza = 20;
    private int iloscPktZyciaKomputera = 20;
    private int ilosc_statkow=0;
  
    private int[][] Komputer = new int[12][12];
    public int[][] Gracz = new int[12][12];
    
    /**
     * Funkcja pobiera parametr długości statku po czym losuje jego pozycje.
     */
    void losuj_statek(int dl){
        Random r = new Random();

        int K_los = r.nextInt(2);

        // Statek poziomo
        if(K_los == 0)
        {
            int X_los = r.nextInt(11-dl)+1;
            int Y_los = r.nextInt(10)+1;

            while(sprawdz(X_los,Y_los,dl,true) == false)
            {
                X_los = r.nextInt(11-dl)+1;
                Y_los = r.nextInt(10)+1;
            }

            // Deklarowanie statku (4-pola)
            for(int i=0;i<dl;i++) {
                Komputer[X_los+i][Y_los] = 1;
                //getPole(X_los+i,Y_los,planszaK).setFill(Color.BROWN);
            }
            //new Statek(X_los,Y_los,dl,true,this,planszaK);


            // Statek pionowo
        }else if(K_los == 1){
            int X_los = r.nextInt(10)+1;
            int Y_los = r.nextInt(11-dl)+1;

            while(sprawdz(X_los,Y_los,dl,false) == false)
            {
                X_los = r.nextInt(10)+1;
                Y_los = r.nextInt(11-dl)+1;
            }

            //Deklarowanie statku

            for(int i=0;i<dl;i++)
            {
                Komputer[X_los][Y_los+i] = 1;
               // getPole(X_los,Y_los+i,planszaK).setFill(Color.BROWN);
            }
            //new Statek(X_los,Y_los,dl,false,this,planszaK);



        }
    }
    
    /**
     * Funkcja ustawiająca statki komputera na planszy.
     */
    public void ustawStatkiK(){
        losuj_statek(4);

        for(int i =0 ;i<2;i++)
        losuj_statek(3);

        for(int i =0 ;i<3;i++)
        losuj_statek(2);

        for(int i =0 ;i<4;i++)
        losuj_statek(1);
    }
    
   /**
    * Funkcja sprawdzająca poprawność ustawienia pozycji przez gracza.
    * @param x to pozycja x na planszy.
    * @param y to pozycja y na planszy.
    * @param dl to dlugosc statku.
    * @param poziomo to zmienna sprawdzajaca czy statek jest poziomo czy pionowo.
    * @param plansza to plansza na ktorej ma byc statek.
    * @return zwraca wartosc true i false w zależności od przebiegu sprawdzenia.
    */
    boolean sprawdz(int x,int y, int dl, boolean poziomo,VBox plansza){
        
        if(poziomo){
            for(int i=0;i<dl;i++){
                if(this.getPole(x+i, y,plansza).getFill()==Color.BROWN || this.getPole(x+i, y,plansza) == null)
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
    
    /**
     * Funkcja sprawdzająca poprawność ustawienia pozycji przez komputer.
     * @param x to pozycja x na planszy.
     * @param y to pozycja y na planszy.
     * @param dl to długość statku.
     * @param kier to zmianna sprawdzająca kierunek (true dla poziomo, false dla pionowo)
     * @return zwraca wartość true i false w zależności od przebiegu sprawdzenia. 
     */
    boolean sprawdz(int x,int y, int dl, boolean kier){
 
        if(kier){
            for(int i=0;i<dl;i++){
                if(Komputer[x+i][y] !=0)
                    return false;
                else{
                    if(Komputer[x+i][y-1] == 1||Komputer[x+i][y+1] == 1)
                        return false;
                    else{
                        if(Komputer[x-1][y] == 1||Komputer[x-1][y-1] == 1||Komputer[x-1][y+1] == 1||Komputer[x+dl][y] == 1||Komputer[x+dl][y-1] == 1||Komputer[x+dl][y+1] == 1)
                            return false;
                    }
                }
            }
        }else{
            for(int i=0;i<dl;i++){
                if(Komputer[x][y+i] !=0)
                    return false;
                else{
                    if(Komputer[x-1][y+i] == 1||Komputer[x+1][y+i] == 1)
                        return false;
                    else{
                        if(Komputer[x][y-1] == 1||Komputer[x-1][y-1] == 1||Komputer[x+1][y-1] == 1||Komputer[x][y+dl] == 1||Komputer[x-1][y+dl] == 1||Komputer[x+1][y+dl] == 1)
                            return false;
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * Funkcja ustawiająca planszę. Obsługuje listener na pola, który wywołuje funkcję sprawdz().
     * Sprawdza również ilość statków które są na planszy.
     */
    public void dodajPlanszeGracz(){

        planszaG = new VBox();
        ilosc = 1;
        typ = 4;

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
                        boolean poziom = event.getButton() == MouseButton.PRIMARY;
                                
                           Plansza.Pole p = (Plansza.Pole)event.getSource();
                           if(sprawdz(p.x,p.y,typ,poziom,planszaG)){
                                new Statek(typ,poziom,event,this);   
                                ++ilosc_statkow;
                           }else
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
    
    /**
     * Funkcja ustawiająca planszę. Obsługuje listener na pola, który wywołuje funkcję sprawdz().
     * Sprawdza również ilość statków które są na planszy.
     */
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
                        if(ilosc_statkow>=10){
                            
                            Pole p = (Pole)event.getSource();  
                            gra(event);
                        }
                        
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
    
    /**
     * Funkcja ustawiąca zdjęcia, w tym tło i plansze.
     */
    private void ustawienieZdjec() {
        tlo = new ImageView();
        tlo.setImage(new Image(getClass().getResource("/obrazy/tlo.png").toExternalForm()));
        tlo.setFitWidth(800.0);
        tlo.setFitHeight(600.0);
        tlo.setId("tlo");
        tlo.setOpacity(0.5);

        planszaI = new ImageView();
        planszaI.setImage(new Image(getClass().getResource("/obrazy/plansza.png").toExternalForm()));
        planszaI.setLayoutX(50.0);
        planszaI.setLayoutY(200.0);
        planszaI.setFitHeight(291.0);
        planszaI.setFitWidth(303.0);
        planszaI.setSmooth(false);

        planszaII = new ImageView();
        planszaII.setImage(new Image(getClass().getResource("/obrazy/plansza.png").toExternalForm()));
        planszaII.setLayoutX(447.0);
        planszaII.setLayoutY(200.0);
        planszaII.setFitHeight(291.0);
        planszaII.setFitWidth(303.0);
        planszaII.setSmooth(false);
    }
    
    /**
     * Funkcja zwracająca pole, na podstawie parametrów x i y.
     * @param x to pozycja x na planszy.
     * @param y to pozycja y na planszy.
     * @param plansza plansza z której pobieramy x i y.
     * @return zwraca pole tego x i y.
     */
    public Pole getPole(int x, int y,VBox plansza) {
        return (Pole)((HBox)plansza.getChildren().get(y)).getChildren().get(x);
    }
    
    /**
     * Funkcja ma za zadanie dodanie obiektów graficznych do rodzica.
     * @return zwraca głównego rodzica wszystkich innych obiektów.
     */
    private Parent gvk(){

        BorderPane root = new BorderPane();
        root.setPrefSize(800, 600);

        ustawienieZdjec();
        dodajPlanszeGracz();
        dodajPlanszeKomputer();
        przegrana();
        wygrana();
        
        root.getChildren().add(tlo);
        root.getChildren().add(planszaI);
        root.getChildren().add(planszaII);
        root.getChildren().add(planszaG);
        root.getChildren().add(planszaK);
        root.getChildren().add(napis);
        root.getChildren().add(napis2);
        ustawStatkiK();

        return root;
    }
    
    /**
     * Funkcja zarządza trafianiem gracza w pole komputera.
     * @param e to event myszki.
     */
    public void gra(MouseEvent e){
            Pole p=(Pole)e.getSource();

            if(!getPole(p.x,p.y,planszaK).trafiony)
                if(Komputer[p.x][p.y] == 1){
                    getPole(p.x,p.y,planszaK).setFill(Color.RED);
                    getPole(p.x,p.y,planszaK).trafiony = true;
                    iloscPktZyciaKomputera--;
                     if(iloscPktZyciaKomputera == 0 ) {
                         try        
                            {
                                Thread.sleep(1000);
                            } 
                            catch(InterruptedException ex) 
                            {
                                Thread.currentThread().interrupt();
                            }
                         napis2.setVisible(true); }
                }
                else{
                    getPole(p.x,p.y,planszaK).setFill(Color.YELLOW);
                    getPole(p.x,p.y,planszaK).trafiony = true;
                    tura_gracza=false;
               
             Random rand=new Random();          
                   int x,y;

                   x = rand.nextInt(10)+1;
                   y = rand.nextInt(10)+1;

                   boolean traf = getPole(x, y, planszaG).trafiony;

                   if(traf)
                   {
                       do{
                           
                           x = rand.nextInt(10)+1;
                           y = rand.nextInt(10)+1;  
                       }while(getPole(x, y, planszaG).trafiony);                              
                   }
                     
                
                   
                   
                 if(Gracz[x][y] == 1)
                 {
                     iloscPktZyciaGracza--;
                     if(iloscPktZyciaGracza == 0 )  {  
                         try        
                            {
                                Thread.sleep(1000);
                            } 
                            catch(InterruptedException ex) 
                            {
                                Thread.currentThread().interrupt();
                            }
                         napis.setVisible(true);
                     }
                     getPole(x,y,planszaG).setFill(Color.RED);
                     getPole(x, y, planszaG).trafiony =  true; 

                     do{
                     
                         do{
                             
                           x = rand.nextInt(10)+1;
                           y = rand.nextInt(10)+1;  
                       }while(getPole(x, y, planszaG).trafiony);
                         
                       if(Gracz[x][y] == 1)
                       {                    
                            getPole(x,y,planszaG).setFill(Color.RED);
                            getPole(x, y, planszaG).trafiony =  true; 
                            iloscPktZyciaGracza--;
                            if(iloscPktZyciaGracza == 0 )  {  napis.setVisible(true);}
                       }else
                       {
                           getPole(x,y,planszaG).setFill(Color.YELLOW);
                           getPole(x, y, planszaG).trafiony =  true;
                       }     
                     }while(Gracz[x][y] == 1);                                  
                     
                 }else{
                           getPole(x,y,planszaG).setFill(Color.YELLOW);
                           getPole(x, y, planszaG).trafiony =  true;
                           tura_gracza=true;
                       }     
            }

        }
    
    /**
     * Funkcja wywołująca się na końcu gry, informuje że gracz przegrał.
     */
    public void przegrana(){
        
        napis = new ImageView();
        napis.setImage(new Image(getClass().getResource("/obrazy/napisP.png").toExternalForm()));
        napis.setFitWidth(800.0);
        napis.setFitHeight(600.0);
        napis.setLayoutX(0.0);
        napis.setLayoutY(0.0);
        napis.setId("Przegrana"); 
        napis.setVisible(false);
    }
    
    /**
     * Funkcja wywołująca się na końcu gry, informuje że gracz wygrał.
     */
    public void wygrana(){
        
        napis2 = new ImageView();
        napis2.setImage(new Image(getClass().getResource("/obrazy/napisW.png").toExternalForm()));
        napis2.setFitWidth(800.0);
        napis2.setFitHeight(600.0);
        napis2.setLayoutX(0.0);
        napis2.setLayoutY(0.0);
        napis2.setId("Wygrana");    
        napis2.setVisible(false);
    }
    
    /**
     * Funkcja dziedziczona z klasy runnable
     * @param primaryStage jest to stage potrzebny do stworzenia okna gry.
     * @throws Exception rzuca wyjątki jeśli nie można otworzyć okna.
     */
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
    
    /**
     * Główna klasa gry
     * @param args parametr potrzebny do włączenia gry.
     */
    public static void main(String[] args) {
        launch(args);
    }
}