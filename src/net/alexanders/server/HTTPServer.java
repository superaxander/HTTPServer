package net.alexanders.server;

import net.alexanders.socket.*;
import net.alexanders.util.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class HTTPServer
{
    public static SocketListener socketListener;
    public static Logger logger;
    public static FileManager fileManager;
    public static boolean running = true;
    public static Thread serverThread;
    public static ArrayList<Thread> threads = new ArrayList<>();
    public static void main(String[] args){
        logger = new Logger(false, System.getProperty("user.home")+"/WEBLOGFILE");
        fileManager = new FileManager(System.getProperty("user.home")+"/www");
        System.out.println(System.getProperty("user.dir"));
        HTTPServer server = new HTTPServer();
        server.start();
    }

    public void start(){
        try{
            socketListener = new SocketListener(8080);
            serverThread = new Thread(() -> {
                while(!socketListener.isClosed()){
                    try{
                        System.out.println("Awaiting input");
                        final Socket socket = socketListener.awaitInput();
                        socket.setSoTimeout(Constants.SOCKET_TIMEOUT);
                        Thread asyncthread = new Thread(() -> {
                            while(!socket.isClosed() && running){
                                PrintWriter output = null;
                                try{
                                    BufferedReader reader = socketListener.getReader();
                                    output = socketListener.getSender();
                                    List<String> lines = new ArrayList<String>();
                                    String line = null;
                                    while((line = reader.readLine()) != null && !line.equals("")){
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
                                            output.println("HTTP/1.1 " + fileManager.getCode().toString() + " " + fileManager.getCode().getMessage());
                                            output.println("Content-Type: text/html");
                                            output.println("Server: bot");
                                            output.println("");
                                            output.println(content);
                                            output.flush();
                                            output.close();
                                        }
                                    }
                                }catch(IOException exception){
                                    if(output != null){
                                        output.println("HTTP/1.1 " + ResponseCodes.ERROR500.toString() + " " + ResponseCodes.ERROR500.getMessage());
                                        output.println("Content-Type: text/html");
                                        output.println("Server: bot");
                                        output.println("");
                                        output.println(ResponseCodes.ERROR500.getPage());
                                        output.flush();
                                        output.close();
                                    }
                                }
                            }
                        });
                        asyncthread.setDaemon(true);
                        asyncthread.setName("HTTP Server Connection");
                        asyncthread.start();
                        threads.add(asyncthread);
                    }catch(IOException ignored){

                    }
                }
            });
            serverThread.start();
            while(running){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                try{
                    ServerCommands.parseString(bufferedReader.readLine()).run();
                }catch(NullPointerException npe){
                    System.out.println("Invalid command!");
                }
            }
            socketListener.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}
