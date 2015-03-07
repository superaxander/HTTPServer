package net.alexanders.server;

public enum ErrorMessages
{
    ERROR404("File not found"), ERROR500("Internal Server Error");

    String message;

    ErrorMessages(String message){
        this.message = message;
    }

    public String getPage(){
        return "<DOCTYPE html><html><head><title>"+this.message+"</title></head><body><b><h1>"+this.name()+"</h1></b><hr><h3>"+this.message+"</h3></body></html>";
    }
}
