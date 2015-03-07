package net.alexanders.server;

import java.io.*;

public class FileManager
{
    private String error500 = ErrorMessages.ERROR500.getPage(); //for performance reasons
    private String basePath;
    public FileManager(String basePath){
        this.basePath = basePath;
    }

    public String getFile(String path){
        File file = new File(this.basePath+path);
        String content = error500;
        if(!file.exists()){
            content = ErrorMessages.ERROR404.getPage();
        }else if(file.canRead()){
            try{
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                bufferedReader.lines().forEach(stringBuilder::append);
                content = stringBuilder.toString();
            }catch(IOException e){
                e.printStackTrace();
                content = ErrorMessages.ERROR500.getPage();
            }
        }
        return content;
    }
}
