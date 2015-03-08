package net.alexanders.server;

public enum ResponseCodes
{
    INFO200("OK"), ERROR403("Forbidden"), ERROR404("File not found"), ERROR500("Internal Server Error");

    String message;

    ResponseCodes(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public String getPage(){
        return "<DOCTYPE html><html><head><title>"+this.message+"</title></head><body><b><h1>"+this.name()+"</h1></b><hr><h3>"+this.message+"</h3></body></html>";
    }

    @Override
    public String toString(){
        if(name().startsWith("ERROR")){
            return (name().substring(4));
        }else{
            return (name().substring(3));
        }
    }
}
