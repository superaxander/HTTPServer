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

    public void loadPlugins(){
        File file  = new File(pluginFolder+"/plugin.jar");
        URL url = null;
        try{
            url = file.toURI().toURL();
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        URL[] urls = new URL[]{url};
        ClassLoader cl = new URLClassLoader(urls);

        try{
            Class cls = cl.loadClass("net.httpplugin.plugin");
            plugins.add((IPlugin)cls.cast(IPlugin.class));
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
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
