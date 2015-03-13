package net.alexanders.server;

import org.junit.*;

import static org.junit.Assert.*;

public class MimeTypesTest{
    
    @Test
    public void testCheckMimeType() throws Exception{
        Assert.assertEquals(MimeTypes.checkMimeType("1.html"), MimeTypes.html);
        Assert.assertEquals(MimeTypes.checkMimeType("2.htm"), MimeTypes.html);
        Assert.assertEquals(MimeTypes.checkMimeType("3.HTML"), MimeTypes.html);
        Assert.assertEquals(MimeTypes.checkMimeType(".css"), MimeTypes.css);
        Assert.assertEquals(MimeTypes.checkMimeType(".doesthishaveacorrectextension"), MimeTypes.text);
        Assert.assertEquals(MimeTypes.checkMimeType("doesn't exist"), MimeTypes.text);
        Assert.assertEquals(MimeTypes.checkMimeType(".txt"), MimeTypes.html);
    }

    @Test
    public void testGetMimeType() throws Exception{
        Assert.assertEquals(MimeTypes.html.getMimeType() , "text/html");
        Assert.assertEquals(MimeTypes.css.getMimeType() , "text/css");
        Assert.assertEquals(MimeTypes.text.getMimeType() , "text/plain");
    }
}