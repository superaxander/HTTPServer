package net.alexanders.plugin;

import net.alexanders.socket.*;

import java.io.*;

public class RequestReceivedEvent
{
    private RequestParser requestParser;
    private PrintWriter writer;

    public RequestParser getRequestParser(){
        return requestParser;
    }

    public void setRequestParser(RequestParser requestParser){
        this.requestParser = requestParser;
    }

    public PrintWriter getWriter(){
        return writer;
    }

    public void setWriter(PrintWriter writer){
        this.writer = writer;
    }

    public void setEnableNormalResponse(Boolean enableNormalResponse){
        this.requestParser.enableNormalResponse = enableNormalResponse;
    }
}
