package net.alexanders.plugin;

public interface IRequestListener
{
    public void onRequestReceived(RequestReceivedEvent event);

    public void onResponseSent(ResponseEvent event);

    public void onFileLoad(FileLoadingEvent event);
}
