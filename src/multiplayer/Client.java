package multiplayer;

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    
   /**
    *Inicjowanie zmiennych globalnych które są potrzebne do prawidłowego działania gry.
    */
    public BorderPane root;
    public ImageView tlo;
    public ImageView planszaI;
    public ImageView planszaII;
    private ImageView plansza_p;
    private ImageView napis;
    private ImageView napis2;
    private ImageView start_b; 
    public VBox planszaG;
    public VBox planszaK;
    private int typ;
    private int ilosc;
    public boolean tura_gracza=false;
    private int tura=0;
    private int wynik_przeciwnika=0;
    public int iloscPktZyciaGracza = 20;
    public int iloscPktZyciaPrzeciwnika = 20;
    public int ilosc_statkow=0;

    public int[][] Przeciwnik = new int[12][12];
    public int[][] Gracz = new int[12][12];
    
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
     * Funkcja ustawiająca planszę. Obsługuje listener na pola, który wywołuje funkcję sprawdz().
     * Sprawdza również ilość statków które są na planszy.
     * Gdy gracz ustawi sowje statki na planszy zostaje nawiązanie z przeciwnikiem ( gdy wykona tą samą czynność ), 
     * a następnie zostają przestłane pozycję statków gracza przeciwnikowi i równocześnie odbierane pozycje statków przeciwnika
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
                        try{  
                           polaczenie();
                           wysylanieTab();
                           odbieranieTab();
                          }catch(Exception e){};
                        
                        dodajPlanszePrzeciwnik();
                        root.getChildren().add(planszaK);
                        plansza_p.setVisible(false);     
                        
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
      
      /**
     * Funkcja ustawiająca planszę. Obsługuje listener który 'zarząda' grą tzn:
     * po kliknieciu na dane pole przeciwnika warunek sprawdza czy dane pole
     * nie zostalo juz wcześniej kliknięte, jesli nie: koloruje je na wskazany kolor
     * oraz zlicza tury
     */
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
                        if(Przeciwnik[p.x][p.y]==1&&getPole(p.x,p.y,planszaK).trafiony==false){
                            getPole(p.x,p.y,planszaK).setFill(Color.RED);
                            getPole(p.x,p.y,planszaK).trafiony = true;
                            iloscPktZyciaPrzeciwnika--;
                            tura++;
                        }else if(getPole(p.x,p.y,planszaK).trafiony==false){
                            getPole(p.x,p.y,planszaK).setFill(Color.YELLOW);
                            getPole(p.x,p.y,planszaK).trafiony = true;
                            tura++;
                        }
                        if(iloscPktZyciaPrzeciwnika==0){
                            planszaK.setVisible(false);
                            System.out.println("Zakonczono na turze: "+tura);
                            try {
                                sprawdzanie_wygranej();
                            } catch (IOException ex) {
                            } 
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
        
        plansza_p = new ImageView();
        plansza_p.setImage(new Image(getClass().getResource("/obrazy/plansza_p.png").toExternalForm()));
        plansza_p.setLayoutX(477.0);
        plansza_p.setLayoutY(228.0);
        plansza_p.setFitHeight(259.0);
        plansza_p.setFitWidth(269.0);
        plansza_p.setSmooth(false);
    }
         
    /**
     * Funkcja zwracająca pole, na podstawie parametrów x i y.
     * @param x to pozycja x na planszy.
     * @param y to pozycja y na planszy.
     * @param plansza plansza z której pobieramy x i y.
     * @return zwraca pole tego x i y.
     */
    public Plansza.Pole getPole(int x, int y,VBox plansza) {
        return (Plansza.Pole)((HBox)plansza.getChildren().get(y)).getChildren().get(x);
    }
    
    /**
     * Funkcja ma za zadanie dodanie obiektów graficznych do rodzica.
     * @return zwraca głównego rodzica wszystkich innych obiektów.
     */
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
        root.getChildren().add(plansza_p);
        root.getChildren().add(napis2);
        return root;
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
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(gvk());
        primaryStage.setTitle("Client");
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
    public static void main(String[] args) throws IOException  {   launch(args);}
    
    /**
 * Zmienne potrzebne do nawiązania połączenie z przeciwnikiem oraz na 
 * obsługę tego połącznia
*/
    ServerSocket sersock;
    Socket sockS;
    Socket sockC;
    OutputStream ostream;
    PrintWriter pwrite;
    InputStream istream;
    BufferedReader receiveRead;

    /**
 * Funkcja służaca do nawiązania połączenia.
 * Tworzony jest socket ktory łączy sie z podanym adressem ip
 * na podanym porcie.
 * @throws Exception rzuca wyjątki gdy wystąpi bład w połączeniu
 */
    void polaczenie() throws Exception{

        sockC = new Socket("127.0.01", 3000);

        System.out.println("Serwer dziala");                      

        ostream = sockC.getOutputStream(); 
        pwrite = new PrintWriter(ostream, true);

        istream = sockC.getInputStream();
        receiveRead = new BufferedReader(new InputStreamReader(istream));

    }

    /**
    * Funkcja służąca do wysylania tablicy z pozycjami statków przeciwnikowi.
    * @throws Exception rzuca wyjątki.
    */
    void wysylanieTab()throws Exception{

        for(int i=1;i<=10;i++)
            for(int j=1;j<=10;j++)
            {       
                pwrite.print(Gracz[i][j]);
            }

        pwrite.flush();

        }

    /**
    * Funkcja służąca do odbierania tablicy z pozycjami statków przeciwnika.
    * @throws Exception rzuca wyjątki.
    */
    void odbieranieTab() throws Exception{

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
         }
    }

    /**
    * Funkcja sprawdzająca kto jest zwycięzcą gry.
    * Wysyła ilość tur gracza przeciwnikowi oraz odbiera od niego, jego ilość tur.
    * Na tej podstawie wyswietlan odpowiedni komunikat na ekranie. 
    * @throws IOException 
    */
    void sprawdzanie_wygranej() throws IOException{
    sockC = new Socket("25.56.43.225", 3001);

    System.out.println("Serwer dziala");                      

    ostream = sockC.getOutputStream(); 
    pwrite = new PrintWriter(ostream, true);

    istream = sockC.getInputStream();
    receiveRead = new BufferedReader(new InputStreamReader(istream));

    pwrite.println(tura);
    pwrite.flush();

    wynik_przeciwnika=Integer.valueOf(receiveRead.readLine());
    System.out.println(wynik_przeciwnika);
    if(tura<=wynik_przeciwnika)
        napis2.setVisible(true);
    else
        napis.setVisible(true);

} 
        
}

    
