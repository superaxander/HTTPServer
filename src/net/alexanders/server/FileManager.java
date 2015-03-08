package net.alexanders.server;

import java.io.*;
import java.util.*;

public class FileManager
{
    private String error500 = ResponseCodes.ERROR500.getPage(); //for performance reasons
    private String basePath;
    private ResponseCodes code;
    public FileManager(String basePath){
        this.basePath = basePath;
    }

    public String getFile(String path){
        File file;
        if(path.equals("/")){
            file = new File(this.basePath+"/index.html");
        }else{
            file = new File(this.basePath + path);
        }
        System.out.println(file.getAbsolutePath());
        String content = error500;
        code = ResponseCodes.INFO200;
        if(!file.exists()){
            content = ResponseCodes.ERROR404.getPage();
            code = ResponseCodes.ERROR404;
        }else if(file.canRead()){
            try{
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                bufferedReader.lines().forEach(stringBuilder::append);
                bufferedReader.close();
                content = stringBuilder.toString();
            }catch(IOException e){
                e.printStackTrace();
                content = error500;
                code = ResponseCodes.ERROR500;
            }
        }
        return content;
    }

    public ResponseCodes getCode(){
        return code;
    }
}
