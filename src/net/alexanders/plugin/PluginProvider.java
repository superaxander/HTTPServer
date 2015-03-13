package net.alexanders.plugin;

import net.alexanders.server.*;
import net.alexanders.socket.*;

import java.io.*;
import java.util.*;

/**
 * Provides interface for both plugins and the server
 */
public class PluginProvider
{
    public static ArrayList<IRequestListener> requestListeners = new ArrayList<>();

    public static void addRequestListener(IRequestListener listener){
        requestListeners.add(listener);
    }

    public static void loadRequestReceivedListeners(RequestParser requestParser, PrintWriter writer){
        RequestReceivedEvent event = new RequestReceivedEvent();
        event.setRequestParser(requestParser);
        event.setWriter(writer);
        for(IRequestListener listener : requestListeners){
            listener.onRequestReceived(event);
        }
    }

    public static void loadFileLoadingListeners(FileManager fileManager, File file, String fileContent){
        FileLoadingEvent event = new FileLoadingEvent();
        event.setFileManager(fileManager);
        event.setFile(file);
        event.setFileContent(fileContent);
        for(IRequestListener listener : requestListeners){
            listener.onFileLoad(event);
        }
    }

    public static void loadResponseEventListeners(RequestParser requestParser, String responseHeader, String responseContent){
        ResponseEvent event = new ResponseEvent();
        event.setRequestParser(requestParser);
        event.setResponseContent(responseContent);
        event.setResponseHeader(responseHeader);
        for(IRequestListener listener : requestListeners){
            listener.onResponseSent(event);
        }
    }
}
