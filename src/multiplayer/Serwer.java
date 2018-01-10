package multiplayer;

import java.io.*;
import java.net.*;
public class Serwer
{
  public static void main(String[] args) throws Exception
  {
      ServerSocket sersock = new ServerSocket(7890);
      
      Socket sock = sersock.accept( );                          
  
      
      OutputStream ostream = sock.getOutputStream(); 
      
      PrintWriter pwrite = new PrintWriter(ostream, true);
 
                             
      InputStream istream = sock.getInputStream();
      BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
 
      String x = "1",y="1",x_p="0",y_p="0";            
      while(true)
      {
        if((x_p = receiveRead.readLine())!=null)
                System.out.println(x_p);
        
        
        pwrite.println(x);             
        pwrite.flush();
      }               
    }                    
}                        
