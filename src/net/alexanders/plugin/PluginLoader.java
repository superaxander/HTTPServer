package net.alexanders.plugin;

import net.alexanders.server.*;
import net.alexanders.util.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class PluginLoader
{
    private File pluginFolder;
    private ArrayList<IPlugin> plugins = new ArrayList<>();

    public PluginLoader(String pluginFolderPath){
        pluginFolder = new File(pluginFolderPath);
    }

    public void loadPlugin(String pluginName){
        File cfgFile  = new File(pluginFolder+pluginName+"/plugin.cfg");
        try{
            ConfigParser configParser = new ConfigParser(cfgFile);
            if(configParser.propertyExists("")){

            }
        }catch(IOException e){
            HTTPServer.logger.log(LogLevel.WARNING, "Couldn't load config for plugin: "+pluginName);
            return;
        }
        File jarFile = new File(pluginFolder+pluginName+"/"+pluginName+".jar");
        URL url = null;
        try{
            url = jarFile.toURI().toURL();
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        URL[] urls = new URL[]{url};
        ClassLoader cl = new URLClassLoader(urls);

        //try{

        //}catch(ClassNotFoundException e){
        //    e.printStackTrace();
        //}
    }

    public void initialisePlugins(){
        for(IPlugin plugin : plugins){
            try{
                plugin.init();
            }catch(Exception e){
                HTTPServer.logger.log(LogLevel.ERROR, "Plugin "+plugin.getName());
            }
        }
    }
}
