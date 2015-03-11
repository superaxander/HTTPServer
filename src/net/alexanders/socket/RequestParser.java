package net.alexanders.socket;

import net.alexanders.server.*;
import net.alexanders.util.*;

import java.io.*;
import java.net.*;

public class RequestParser
{
    private String[] request;

    public RequestParser(String[] request){
        this.request = request;
    }

    public String getProperty(String propertyname){
        for(String line : request){
            if(line.contains(propertyname+": ")){
                return line.substring((propertyname + ": ").length());
            }
        }
        return "";
    }

    public String getFilePath(){
        String path = request[0].substring(request[0].indexOf('/'));
        path = path.substring(0, path.indexOf(' '));
        try{
            path = URLDecoder.decode(path, "UTF-8");
        }catch(UnsupportedEncodingException e){
            HTTPServer.logger.log(LogLevel.ERROR, "UTF-8 UnsupportedEncodingException");
        }
        return path;
    }

    public String getHTTPVersion(){
        return request[0].substring(request[0].lastIndexOf(getFilePath().charAt(getFilePath().length()-1))+2);
    }
}
