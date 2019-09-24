package test.java;

import main.java.SearchMap;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class TestSearchMap {

    private SearchMap searchMap;

    public void setUp() throws FileNotFoundException, UnsupportedEncodingException {
        // create search map instance
        searchMap = SearchMap.getInstance();

        // create input file
        PrintWriter pw = new PrintWriter("input.txt", "UTF-8");
        pw.println("P");
        pw.print("P Q 1");
        pw.close();
    }

    @Test
    public void testReadFromFile() {
        String actualOutput = searchMap.read("input.txt");
        String expectedOutput = "P\n" +
                                "P Q 1";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testExecuteSearch() {
        String actualOutput = searchMap.executeSearch("P\nP Q 1");
        String expectedOutput = "Destination  Flight Route from P  Total Cost\n" +
                                "Q            P, Q                 $200      ";
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testWriteToFile() throws IOException {
        String text = "Destination  Flight Route from P  Total Cost\n" +
                      "Q            P, Q                 $200      ";
        searchMap.write(text, "output.txt");
        String outputContent = new String(Files.readAllBytes(Paths.get("output.txt")));
        assertEquals(text, outputContent);
    }

    @AfterClass
    public static void cleanUp() throws IOException {
        Files.delete(Paths.get("output.txt"));
    }

}
