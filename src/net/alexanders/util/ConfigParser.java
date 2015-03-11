package net.alexanders.util;

import net.alexanders.server.*;

import java.io.*;
import java.util.*;

public class ConfigParser
{
    private ArrayList<String> lines = new ArrayList<>();
    private Properties properties = new Properties();
    public ConfigParser(File file) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        reader.lines().forEach(lines::add);
        reader.close();
        this.parse();
    }

    private void parse(){
        lines.forEach(line -> properties.put(line.substring(0, line.indexOf('=')), line.substring(line.indexOf('=')+1)));
    }

    public Properties getProperties(){
        return properties;
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

    public boolean propertyExists(String key){
        return properties.getProperty(key) != null;
    }
}
