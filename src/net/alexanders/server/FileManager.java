package net.alexanders.server;

import java.io.*;

public class FileManager
{
    private String error500 = ResponseCodes.ERROR500.getPage(); //for performance reasons
    private String basePath;
    private ResponseCodes code;
    private String mimetype;
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
        if(file.getName().contains(".")){
            mimetype = MimeTypes.checkMimeType(file.getName()).getMimeType();
        }else{
            mimetype = MimeTypes.text.getMimeType();
        }
        String content = error500;
        code = ResponseCodes.INFO200;
        if(!file.exists()){
            content = ResponseCodes.ERROR404.getPage();
            code = ResponseCodes.ERROR404;
            mimetype = MimeTypes.html.getMimeType();
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
                mimetype = MimeTypes.html.getMimeType();
            }
        }
        return content;
    }

    public ResponseCodes getCode(){
        return code;
    }

    public String getMimeType(){
        return mimetype;
    }
}
