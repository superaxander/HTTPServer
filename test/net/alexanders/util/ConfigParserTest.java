package net.alexanders.util;

import org.junit.*;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

public class ConfigParserTest{

    private static File tempFile;
    private static ConfigParser configParser;

    @BeforeClass
    public static void setUp() throws Exception{
        tempFile = File.createTempFile("test", ".txt", new File(System.getProperty("user.dir")));
        FileWriter fileWriter = new FileWriter(tempFile);
        fileWriter.write("hello=world\nworld=hello");
        fileWriter.flush();
        fileWriter.close();
        configParser = new ConfigParser(tempFile);
    }

    @AfterClass
    public static void cleanUp() throws Exception{
        Assert.assertTrue(tempFile.delete());
    }

    @Test
    public void testGetProperties() throws Exception{
        Properties testProperties = new Properties();
        testProperties.put("hello", "world");
        testProperties.put("world", "hello");
        Assert.assertTrue(configParser.getProperties().equals(testProperties));
    }

    @Test
    public void testGetProperty() throws Exception{
        Assert.assertEquals("hello", configParser.getProperty("world"));
        Assert.assertEquals("world", configParser.getProperty("hello"));
    }

    @Test
    public void testPropertyExists() throws Exception{
        Assert.assertTrue(configParser.propertyExists("hello"));
        Assert.assertTrue(configParser.propertyExists("world"));
    }
}