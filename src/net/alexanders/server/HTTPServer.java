package net.alexanders.server;

import net.alexanders.socket.*;

import java.io.*;

public class HTTPServer
{
    public SocketListener socketListener;
    public BufferedReader reader;
    public PrintWriter output;
    public boolean running = true;
    public static void main(String[] args){
        HTTPServer server = new HTTPServer();
        server.start();
    }

    public void start(){
        try{
            socketListener = new SocketListener(8080);

            while(running){
                System.out.println("Awaiting input");
                socketListener.awaitInput();
                reader = socketListener.getReader();
                output = socketListener.getSender();
                String string = reader.readLine();
                System.out.println(string);
                output.println("HTTP/1.0 200 OK");
                output.println("Content-Type: text/html");
                output.println("Server: ALEXANDERSWEBSERVICES");
                output.println("");
                output.println("<b>Thank You!</b>");
                output.flush();
            }
            socketListener.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}
