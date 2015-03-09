package net.alexanders.server;

import net.alexanders.util.*;

import java.io.*;

public enum ServerCommands
{
    EXIT(new Runnable(){
        @Override
        public void run(){
            HTTPServer.running = false;
            try{
                HTTPServer.socketListener.close();
            }catch(IOException e){
                e.printStackTrace();
                System.exit(2);
            }
            for(Thread thread : HTTPServer.threads){
                try{
                    thread.join();
                }catch(InterruptedException e){
                    e.printStackTrace();
                    System.exit(2);
                }
            }
            try{
                HTTPServer.serverThread.join();
            }catch(InterruptedException e){
                e.printStackTrace();
                System.exit(2);
            }

            HTTPServer.logger.log(LogLevel.INFO, "Shutting down as requested by command");
            System.exit(0);
        }
    }, new String[]{"quit", "q", "stop"}, "Stop the server from running"),
    HELP(new Runnable(){
        @Override
        public void run(){
            System.out.println("Commands:");
            for(ServerCommands command : ServerCommands.values()){
                System.out.println(command.name()+": "+command.getDescription());
            }
        }
    }, new String[]{"?", "h"}, "Get a list of commands available");


    Runnable runnable;
    String[] aliases;
    String description;

    ServerCommands(Runnable runnable, String[] aliases, String description){
        this.runnable = runnable;
        this.aliases = aliases;
        this.description = description;
    }

    public String[] getAliases(){
        return aliases;
    }

    public String getDescription(){
        return description;
    }

    public static ServerCommands parseString(String str){
        for(ServerCommands command : ServerCommands.values()){
            if(str.equalsIgnoreCase(command.name())||parseAlias(str, command)){
                return command;
            }
        }
        return null;
    }

    private static boolean parseAlias(String str, ServerCommands command){
        for(String alias : command.getAliases()){
            if(alias.equals(str)) return true;
        }
        return false;
    }
    public void run(){
        runnable.run();
    }
}
