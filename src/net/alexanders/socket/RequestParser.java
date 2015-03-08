package net.alexanders.socket;

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
        return path;
    }
}
