package net.alexanders.util;

import java.io.*;

public class Logger
{
    public boolean verbose = false;
    public String logFilePath;

    public Logger(boolean verbose, String logFilePath){
        this.verbose = verbose;
        this.logFilePath = logFilePath;
    }

    public void log(LogLevel level, String message){
        if(verbose){
            switch(level){
                case INFO:
                    System.out.println("INFO: "+message);
                    logToFile(level, message);
                    break;
                case WARNING:
                    System.out.println("WARN: "+message);
                    logToFile(level, message);
                    break;
                case ERROR:
                    System.err.println("ERROR: "+message);
                    logToFile(level, message);
                    break;
            }
        }else if(level == LogLevel.ERROR){
            System.out.println("ERROR: "+message);
            logToFile(level, message);
        }else{
            logToFile(level, message);
        }
    }

    public void logToFile(LogLevel level, String message){
        try{
            File logFile = new File(logFilePath);
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
            writer.append(level.name()).append(": ").append(message);
            writer.flush();
            writer.close();
        }catch(IOException e){
            System.err.println("ERROR: Couldn't open log file");
            e.printStackTrace();
        }
    }
}
