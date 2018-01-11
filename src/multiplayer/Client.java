package multiplayer;

import java.io.*;
import java.net.*;
import java.util.Random;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Client extends Application{
    
        public ImageView tlo;
    public ImageView planszaI;
    public ImageView planszaII;
    ImageView napis;
    ImageView napis2;
    VBox planszaG;
    VBox planszaK;
    int typ;
    int ilosc;
    public boolean tura_gracza=true;
    int tura=0;
    public int iloscPktZyciaGracza = 20;
    public int iloscPktZyciaPrzeciwnika = 20;
    public int ilosc_statkow=0;
    
    int[][] Przeciwnik = new int[12][12];
    int[][] Gracz = new int[12][12];
    
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
    
    public void dodajPlanszeGracz(){

        planszaG = new VBox();
        ilosc = 1;
        typ = 4;

        for (int y = 0; y < 12; y++) {
            HBox row = new HBox();
            if(y==0||y==11)
                row.setVisible(false);
            for (int x = 0; x < 12; x++) {
                
                Plansza.Pole c = new Plansza.Pole(x, y);
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
      
    public void dodajPlanszePrzeciwnik(){
        
        try{
        odbieranie();
        }catch(Exception e){ System.out.println(e);};
        planszaK = new VBox();


        for (int y = 0; y < 12; y++) {
            HBox row = new HBox();
            if(y==0||y==11)
                row.setVisible(false);
            for (int x = 0; x < 12; x++) {
                Plansza.Pole c = new Plansza.Pole(x, y);
                if(x==0||x==11)
                    c.setVisible(false);
                
               c.addEventFilter(MouseEvent.MOUSE_PRESSED, event ->{
                        
                   if(ilosc_statkow == 10){
                        Plansza.Pole p = (Plansza.Pole)event.getSource();   
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
    
    private void ustawienieZdjec() {
        tlo = new ImageView();
        tlo.setImage(new Image(getClass().getResource("tlo.png").toExternalForm()));
        tlo.setFitWidth(800.0);
        tlo.setFitHeight(600.0);
        tlo.setId("tlo");
        tlo.setOpacity(0.5);

        planszaI = new ImageView();
        planszaI.setImage(new Image(getClass().getResource("plansza.png").toExternalForm()));
        planszaI.setLayoutX(50.0);
        planszaI.setLayoutY(200.0);
        planszaI.setFitHeight(291.0);
        planszaI.setFitWidth(303.0);
        planszaI.setSmooth(false);

        planszaII = new ImageView();
        planszaII.setImage(new Image(getClass().getResource("plansza.png").toExternalForm()));
        planszaII.setLayoutX(447.0);
        planszaII.setLayoutY(200.0);
        planszaII.setFitHeight(291.0);
        planszaII.setFitWidth(303.0);
        planszaII.setSmooth(false);
    }
          
    public Plansza.Pole getPole(int x, int y,VBox plansza) {
        return (Plansza.Pole)((HBox)plansza.getChildren().get(y)).getChildren().get(x);
    }
    
    private Parent gvk(){

        BorderPane root = new BorderPane();
        root.setPrefSize(800, 600);

        ustawienieZdjec();
        dodajPlanszeGracz();
        dodajPlanszePrzeciwnik();
        przegrana();
        wygrana();
        
        root.getChildren().add(tlo);
        root.getChildren().add(planszaI);
        root.getChildren().add(planszaII);
        root.getChildren().add(planszaG);
        root.getChildren().add(planszaK);
        root.getChildren().add(napis);
        root.getChildren().add(napis2);
        for(int i=1;i<=10;i++){
            for(int j=1;j<=10;j++){
                System.out.print(Przeciwnik[i][j]);
            }
            System.out.println();
        }

        return root;
    }
    
    public void gra(MouseEvent e){
            Plansza.Pole p=(Plansza.Pole)e.getSource();

            if(!getPole(p.x,p.y,planszaK).trafiony)
                if(Przeciwnik[p.x][p.y] == 1){
                    getPole(p.x,p.y,planszaK).setFill(Color.RED);
                    getPole(p.x,p.y,planszaK).trafiony = true;
                    iloscPktZyciaPrzeciwnika--;
                     if(iloscPktZyciaPrzeciwnika == 0 ) {
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
                         napis.setVisible(true);}
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
    
    public void przegrana(){
        
        napis = new ImageView();
        napis.setImage(new Image(getClass().getResource("napisP.png").toExternalForm()));
        napis.setFitWidth(800.0);
        napis.setFitHeight(600.0);
        napis.setLayoutX(0.0);
        napis.setLayoutY(0.0);
        napis.setId("Przegrana"); 
        napis.setVisible(false);
    }
    
    public void wygrana(){
        
        napis2 = new ImageView();
        napis2.setImage(new Image(getClass().getResource("napisW.png").toExternalForm()));
        napis2.setFitWidth(800.0);
        napis2.setFitHeight(600.0);
        napis2.setLayoutX(0.0);
        napis2.setLayoutY(0.0);
        napis2.setId("Wygrana");    
        napis2.setVisible(false);
    }
   
    void odbieranie() throws Exception{
        Socket sock = new Socket("192.168.1.18",7890);
        OutputStream ostream = sock.getOutputStream();
        
        PrintWriter pwrite = new PrintWriter(ostream,true);
        
        InputStream istream = sock.getInputStream();
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
        
        String x = "0",y="0",x_p="0",y_p="0";
        
        System.out.println(receiveRead.read());
        
        /*
        for(int i=1;i<=10;i++){
          for(int j=1;j<=10;j++){
                  Przeciwnik[i][j]=receiveRead.read();
                  System.out.println("Wyslalo sie!");
          }
      }    */   
    } 

    @Override
        public void start(Stage primaryStage) throws Exception {
            Scene scene = new Scene(gvk());
            primaryStage.setTitle("Client");
            primaryStage.setScene(scene);
            primaryStage.setWidth(806.0);
            primaryStage.setHeight(630.0);
            primaryStage.setResizable(false);
            primaryStage.show();
        }
        public static void main(String[] args) 
        {
             launch(args);
        }
    }
    
    
