package net.alexanders.server;

import net.alexanders.plugin.*;
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
    public static PluginLoader pluginLoader;
    public static void main(String[] args){
        logger = new Logger(false, System.getProperty("user.home")+"/WEBLOGFILE");
        fileManager = new FileManager(System.getProperty("user.home")+"/www");
        pluginLoader = new PluginLoader(System.getProperty("user.home")+"/HTTPPlugins");
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
                                RequestParser requestParser = new RequestParser(socketListener, socket);
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
