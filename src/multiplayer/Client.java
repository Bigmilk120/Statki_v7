package multiplayer;

import java.io.*;
import java.net.*;

public class Client{
   
    public void main(String[] args) throws Exception
    {
        Socket sock = new Socket("192.168.0.24",7890);
        OutputStream ostream = sock.getOutputStream();
        
        PrintWriter pwrite = new PrintWriter(ostream,true);
        
        InputStream istream = sock.getInputStream();
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
        
        String x = "0",y="0",x_p="0",y_p="0";
        
        while(true)
        {
            pwrite.println(x);
            pwrite.flush(); 
            
            pwrite.println(y);
            pwrite.flush();  
            
            if((x_p = receiveRead.readLine())!=null)
                System.out.println(x_p);
        }
    }
}
