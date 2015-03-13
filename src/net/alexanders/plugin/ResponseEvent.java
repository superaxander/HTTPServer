package net.alexanders.plugin;

import net.alexanders.socket.*;

public class ResponseEvent
{
    private String responseHeader;
    private String responseContent;
    private RequestParser requestParser;

    public String getResponseHeader(){
        return responseHeader;
    }

    public void setResponseHeader(String responseHeader){
        this.responseHeader = responseHeader;
    }

    public String getResponseContent(){
        return responseContent;
    }

    public void setResponseContent(String responseContent){
        this.responseContent = responseContent;
    }

    public RequestParser getRequestParser(){
        return requestParser;
    }

    public void setRequestParser(RequestParser requestParser){
        this.requestParser = requestParser;
    }
}
