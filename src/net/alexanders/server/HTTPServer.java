package net.alexanders.server;

import net.alexanders.socket.*;
import net.alexanders.util.*;

import java.io.*;
import java.util.*;

public class HTTPServer
{
    public static Logger logger;
    public static FileManager fileManager;
    public boolean running = true;
    public static void main(String[] args){
        logger = new Logger(false, System.getProperty("user.home")+"/WEBLOGFILE");
        fileManager = new FileManager(System.getProperty("user.home")+"/www");
        System.out.println(System.getProperty("user.dir"));
        HTTPServer server = new HTTPServer();
        server.start();
    }

    public void start(){
        try{
            SocketListener socketListener = new SocketListener(8080);

            while(running){
                System.out.println("Awaiting input");
                socketListener.awaitInput();
                BufferedReader reader = socketListener.getReader();
                PrintWriter output = socketListener.getSender();
                List<String> lines = new ArrayList<String>();
                String line = null;
                while ((line = reader.readLine()) != null && !line.equals("")) {
                    lines.add(line);
                }
                if(lines.size() > 0){
                    RequestParser requestParser = new RequestParser(lines.toArray(new String[lines.size()]));
                    String content = fileManager.getFile(requestParser.getFilePath());
                    if(fileManager.getCode() == ResponseCodes.INFO200){
                        output.println("HTTP/1.1 200 OK");
                        output.println("Content-Type: text/html");
                        output.println("Server: bot");
                        output.println("");
                        output.println(content);
                        output.flush();
                        output.close();
                    }else{
                        output.println("HTTP/1.1 "+fileManager.getCode().toString()+" "+fileManager.getCode().getMessage());
                        output.println("Content-Type: text/html");
                        output.println("Server: bot");
                        output.println("");
                        output.println(content);
                        output.flush();
                        output.close();
                    }
                }
            }
            socketListener.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}
