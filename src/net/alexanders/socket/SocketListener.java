package net.alexanders.socket;

import java.io.*;
import java.net.*;

public class SocketListener
{
    private ServerSocket serverSocket;
    private Socket socket;

    public SocketListener(int port) throws IOException{
        serverSocket = new ServerSocket(port);
    }

    public Socket awaitInput() throws IOException{
        socket = this.serverSocket.accept();
        return socket;
    }
    public BufferedReader getReader() throws IOException{
        return new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    public PrintWriter getSender() throws IOException{
        return new PrintWriter(socket.getOutputStream());
    }

    public void close() throws IOException{
        socket.close();
    }
}
