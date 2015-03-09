package net.alexanders.plugin;


import com.sun.corba.se.spi.ior.iiop.*;

public interface IPlugin
{
    public IPlugin instance = null;

    public void init();
    public void stop();
    public String getName();
}
