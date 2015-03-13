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
        if(pluginFolder.listFiles() == null){
            HTTPServer.logger.log(LogLevel.INFO, "Plugin folder empty not loading any plugins.");
        }else{
            HTTPServer.logger.log(LogLevel.INFO, "Loading plugins.");
            for(File file : pluginFolder.listFiles()){
                if(file.getName().substring(file.getName().lastIndexOf('.')).equalsIgnoreCase(".jar")){
                    HTTPServer.logger.log(LogLevel.INFO, "Loading plugin: " + file.getName().substring(0, file.getName().lastIndexOf('.')));
                    loadPlugin(file);
                }
            }
        }
    }

    public void loadPlugin(File plugin){
        URL url = null;
        try{
            url = plugin.toURI().toURL();
        }catch(MalformedURLException e){
            HTTPServer.logger.log(LogLevel.WARNING, "Plugin "+plugin.getName().substring(0, plugin.getName().lastIndexOf('.'))+" could not be found.");
            return;
        }
        URL[] urls = new URL[]{url};
        ClassLoader cl = new URLClassLoader(urls);
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(cl.getResourceAsStream("plugininfo")));
            String line = reader.readLine();
            if(line == null){
                HTTPServer.logger.log(LogLevel.WARNING, "Plugin "+plugin.getName().substring(0, plugin.getName().lastIndexOf('.'))+" didn't have a proper plugininfo file.");
                return;
            }else{
                try{
                    plugins.add((IPlugin)cl.loadClass(line).cast(IPlugin.class));
                }catch(ClassCastException cce){
                    HTTPServer.logger.log(LogLevel.WARNING, "Plugin "+plugin.getName().substring(0, plugin.getName().lastIndexOf('.'))+" it's plugin class wasn't a IPlugin class.");
                    return;
                }
            }
        }catch(ClassNotFoundException|IOException ex){
            HTTPServer.logger.log(LogLevel.WARNING, "Plugin "+plugin.getName().substring(0, plugin.getName().lastIndexOf('.'))+ "it's class couldn't be found or the plugininfo file was missing.");
        }
    }

    public void initialisePlugins(){
        for(IPlugin plugin : plugins){
            try{
                plugin.init();
            }catch(Exception e){
                HTTPServer.logger.log(LogLevel.ERROR, "Plugin "+plugin.getName()+" threw an exception while initialising");
            }
        }
    }

    public void stopPlugins(){
        for(IPlugin plugin : plugins){
            try{
                plugin.stop();
            }catch(Exception e){
                HTTPServer.logger.log(LogLevel.ERROR, "Plugin "+plugin.getName()+" threw an exception while stopping");
            }
        }
    }
}
