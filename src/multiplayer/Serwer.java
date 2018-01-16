package multiplayer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
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
public class Serwer extends Application
{
    BorderPane root;
    public ImageView tlo;
    public ImageView planszaI;
    public ImageView planszaII;
    private ImageView plansza_p;
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
                                ilosc_statkow++;
                           }else
                               ilosc--;
                           
                            if((typ==4&&ilosc==1)||(typ==3&&ilosc==2)||(typ==2&&ilosc==3)||(typ==1&&ilosc==4)){
                                typ--;
                                ilosc=0;
                            }
                                ilosc++;       
                                
                    }catch(Exception e){}
                    
                    if(ilosc_statkow == 10)
                    {                        
                        dodajPlanszePrzeciwnik();
                        root.getChildren().add(planszaK);
                        plansza_p.setVisible(false);
                        
                        try{
                            polaczenie();
                            wysylanie();  
                            odbieranie();
                        }catch(Exception e){}
                    }
                    
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
                        
                        Plansza.Pole p = (Plansza.Pole)event.getSource();   
                        gra(event);

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
        
        plansza_p = new ImageView();
        plansza_p.setImage(new Image(getClass().getResource("/obrazy/plansza_p.png").toExternalForm()));
        plansza_p.setLayoutX(477.0);
        plansza_p.setLayoutY(228.0);
        plansza_p.setFitHeight(259.0);
        plansza_p.setFitWidth(269.0);
        plansza_p.setSmooth(false);
    }
          
    public Plansza.Pole getPole(int x, int y,VBox plansza) {
        return (Plansza.Pole)((HBox)plansza.getChildren().get(y)).getChildren().get(x);
    }
    
    private Parent gvk(){

        root = new BorderPane();
        root.setPrefSize(800, 600);

        ustawienieZdjec();
        dodajPlanszeGracz();
        przegrana();
        wygrana();
        
        
        root.getChildren().add(tlo);
        root.getChildren().add(planszaI);
        root.getChildren().add(planszaII);
        root.getChildren().add(planszaG);
        root.getChildren().add(napis);
        root.getChildren().add(napis2);
        root.getChildren().add(plansza_p);
 
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

                           napis2.setVisible(true); }
                  }
                  else{
                      getPole(p.x,p.y,planszaK).setFill(Color.YELLOW);
                      getPole(p.x,p.y,planszaK).trafiony = true;
                      tura_gracza=false;
                 }

          }
   
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
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(gvk());
        primaryStage.setTitle("Serwer");
        primaryStage.setScene(scene);
        primaryStage.setWidth(806.0);
        primaryStage.setHeight(630.0);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public static void main(String[] args) throws IOException { 
        launch(args);
    }              
    
    /**
     * Zmienne i Metody do Multiplayer
    */
    
    ServerSocket sersock;
    Socket sockS;
    Socket sockC;
    OutputStream ostream;
    PrintWriter pwrite;
    InputStream istream;
    BufferedReader receiveRead;
    
    void polaczenie() throws Exception{
        
        sersock = new ServerSocket(3000);
       
        System.out.println("Serwer dziala");
        
        sockS = sersock.accept();  
       // sockC = new Socket("192.168.1.18", 3000);

        ostream = sockS.getOutputStream(); 
        pwrite = new PrintWriter(ostream, true);
        
        istream = sockS.getInputStream();
        receiveRead = new BufferedReader(new InputStreamReader(istream));
               
    }
    
    void wysylanie()throws Exception{
        
        for(int i=1;i<=10;i++)
            for(int j=1;j<=10;j++)
            {       
                pwrite.print(Gracz[i][j]);
            }
            
        pwrite.flush();
 
        }
    
    void odbieranie() throws Exception{

         
       int r;
         
         for(int i=1;i<=10;i++)
         {
             for(int j=1;j<=10;j++)
            {
                r = receiveRead.read();
                
                if(r == 48)
                  Przeciwnik[i][j] = 0;
                 if(r == 49)
                  Przeciwnik[i][j] = 1;

            }
             System.out.println();
         }

    }
       

}                        
