package net.alexanders.socket;

import org.junit.*;

import java.io.*;

import static org.junit.Assert.*;

public class RequestParserTest{

    private static RequestParser requestParser;

    @BeforeClass
    public static void setUp() throws Exception{
        String[] sampleRequest = new String[]{"GET /favicon.ico HTTP/1.1", "Host: 127.0.0.1:8080", "Connection: keep-alive", "Accept: */*", "DNT: 1", "User-Agent: Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36", "Accept-Encoding: gzip, deflate, sdch", "Accept-Language: nl-NL,nl;q=0.8,en-US;q=0.6,en;q=0.4"};
        requestParser = new RequestParser(sampleRequest);
    }

    @Test
    public void testGetProperty() throws Exception{
        Assert.assertEquals(requestParser.getProperty("Host"), "127.0.0.1:8080");
        Assert.assertEquals(requestParser.getProperty("Connection"), "keep-alive");
        Assert.assertEquals(requestParser.getProperty("Accept"), "*/*");
        Assert.assertEquals(requestParser.getProperty("DNT"), "1");
        Assert.assertEquals(requestParser.getProperty("Accept-Encoding"), "gzip, deflate, sdch");//swap reading of properties to check if sequence matters
        Assert.assertEquals(requestParser.getProperty("User-Agent"), "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.115 Safari/537.36");
        Assert.assertEquals(requestParser.getProperty("Accept-Language"), "nl-NL,nl;q=0.8,en-US;q=0.6,en;q=0.4");
    }

    @Test
    public void testGetFilePath() throws Exception{
        Assert.assertEquals(requestParser.getFilePath(), "/favicon.ico");
    }

    @Test
    public void testGetHTTPVersion() throws Exception{
        Assert.assertEquals(requestParser.getHTTPVersion(), "HTTP/1.1");
    }
}