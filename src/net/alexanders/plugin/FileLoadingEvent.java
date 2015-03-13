package net.alexanders.plugin;

import net.alexanders.server.*;

import java.io.*;

public class FileLoadingEvent
{
    private File file;
    private FileManager fileManager;
    private String fileContent;

    public void setFileContent(String content){
        this.fileManager.setContent(content);
        this.fileContent = content;
    }

    public File getFile(){
        return file;
    }

    public void setFile(File file){
        this.file = file;
    }

    public String getFileContent(){
        return fileContent;
    }

    public FileManager getFileManager(){
        return fileManager;
    }

    public void setFileManager(FileManager fileManager){
        this.fileManager = fileManager;
    }
}
