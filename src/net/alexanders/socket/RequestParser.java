package net.alexanders.socket;

import net.alexanders.plugin.*;
import net.alexanders.server.*;
import net.alexanders.util.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class RequestParser
{
    private String[] request;
    public boolean enableNormalResponse = true;

    public RequestParser(SocketListener socketListener, Socket socket){
        PrintWriter output = null;
        try{
            BufferedReader reader = socketListener.getReader();
            output = socketListener.getSender();
            List<String> lines = new ArrayList<>();
            String line = null;
            while((line = reader.readLine()) != null && !line.equals("")){
                lines.add(line);
            }
            if(lines.size() > 0){
                String header = "";
                PluginProvider.loadRequestReceivedListeners(this, output);
                this.request = lines.toArray(new String[lines.size()]);
                String content = HTTPServer.fileManager.getFile(this.getFilePath());
                System.out.println(this.getHTTPVersion());
                if(enableNormalResponse){
                    if(HTTPServer.fileManager.getCode() == ResponseCodes.INFO200){
                        header = "HTTP/1.1 200 OK\nContent-Type:" + HTTPServer.fileManager.getMimeType()+"\nServer: bot";
                        output.println(header);
                        output.println("");
                        output.println(content);
                        output.flush();
                        output.close();
                        socket.close();
                    }else{
                        header = "HTTP/1.1 " + HTTPServer.fileManager.getCode().toString() + " " + HTTPServer.fileManager.getCode().getMessage()+"\nContent-Type: " + HTTPServer.fileManager.getMimeType()+
                                "Server: bot";
                        output.println(header);
                        output.println("");
                        output.println(content);
                        output.flush();
                        output.close();
                        socket.close();
                    }
                }else{
                    //plugins should have sent the headers by now
                    output.println("");
                    output.println(content);
                    socket.close();
                }
                PluginProvider.loadResponseEventListeners(this, header, content);
            }
        }catch(IOException exception){
            if(output != null){
                output.println("HTTP/1.1 " + ResponseCodes.ERROR500.toString() + " " + ResponseCodes.ERROR500.getMessage());
                output.println("Content-Type: "+HTTPServer.fileManager.getMimeType());
                output.println("Server: bot");
                output.println("");
                output.println(ResponseCodes.ERROR500.getPage());
                output.flush();
                output.close();
                try{
                    socket.close();
                }catch(IOException ignored){
                }
            }
        }
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
