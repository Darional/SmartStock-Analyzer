import java.io.*;
import java.net.*;

public class Client {
    private Socket s = null;
    BufferedReader in = null;
    PrintWriter out = null;

    public Client(String addr, int port){
        try{
            s = new Socket(addr, port);
            System.out.println("Connected");

            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
        } 
        catch (UnknownHostException u){
            System.out.println(u);
            return;
        }
        catch (IOException i){
            System.out.println(i);
            return;
        }

        Thread listenThread = new Thread(()->{
            String serverMessage = "";
            try {
                while ((serverMessage = in.readLine()) != null){
                    System.out.println("Servidor: "+ serverMessage);
                }
            } catch(IOException e){
                System.err.println(e);
            }
        });
        listenThread.start();

        String m = "";
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
        while(!m.equals("bye")){
            try{
                m = consoleInput.readLine();
                out.println(m);
                out.flush();
            }
            catch (IOException i){
                System.out.println(i);
            }
        }
        try{
            out.println(m);
            out.flush();
            in.close();
            out.close();
            s.close();
        }
        catch(IOException i){
            System.out.println(i);
        }
    }
    public static void main(String[] args){
        Client c = new Client("localhost",5000);
    }
}