package net.alexanders.server;

import org.junit.*;

import java.io.*;

public class FileManagerTest{

    private static FileManager fileManager;
    private static File tempFile;

    @BeforeClass
    public static void setUp() throws Exception{
        fileManager = new FileManager(System.getProperty("user.dir"));
        tempFile = File.createTempFile("test", ".txt", new File(System.getProperty("user.dir")));
        FileWriter fileWriter = new FileWriter(tempFile);
        fileWriter.write("Hello World!");
        fileWriter.flush();
        fileWriter.close();
    }

    @AfterClass
    public static void cleanUp() throws Exception{
        Assert.assertTrue(tempFile.delete());
    }

    @Test
    public void testGetFile() throws Exception{
        Assert.assertEquals("Hello World!", fileManager.getFile("/"+tempFile.getName()));
    }

    @Test
    public void testGetCode() throws Exception{
        testGetFile();
        Assert.assertEquals(fileManager.getCode(), ResponseCodes.INFO200);
    }
}