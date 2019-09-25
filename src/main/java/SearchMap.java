package main.java;

import java.io.*;
import java.nio.file.*;

/**
 * Search map class that contains main function that handles command line
 * arguments and executes the program, file read/write utilities, and wrapper
 * method around flight map search.
 */
public class SearchMap {

    /**
     * Singleton instance for SearchMap class.
     */
    private static SearchMap instance = null;

    /**
     * Reads content of an input file.
     * @param inputFile name of the input file.
     * @return content of the input file in string format.
     */
    public String read(String inputFile) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(inputFile));
            return new String(encoded);
        }
        catch (IOException ioe) {
            return null;
        }
    }

    /**
     * Executes flight map search for the input.
     * @param input flight map input in string format.
     * @return reachable destinations, the paths to them, and the cost in
     * string format.
     */
    public String executeSearch(String input) {
        FlightMap flightMap = FlightMap.getInstance();
        String output = flightMap.run(input);
        return output;
    }

    /**
     * Writes flight map search result to file.
     * @param output string to output to file.
     * @param outputFile file to which the output is saved.
     * @return whether the output process is finished successfully.
     */
    public boolean write(String output, String outputFile) {
        try {
            File file = new File(outputFile);
            FileWriter fw = new FileWriter(file);
            fw.write(output);
            fw.flush();
            fw.close();
        } catch (IOException ioe) {
            return false;
        }
        return true;
    }

    /**
     * Get singleton instance for SearchMap class.
     * @return the instance for SearchMap class.
     */
    public static SearchMap getInstance() {
        if(instance == null) {
            instance = new SearchMap();
        }
        return instance;
    }

    /**
     * Main function for the program that parses command line, reads input
     * file, executes flight map search, and saves output to designated
     * file.
     * @param args command line arguments.
     * @throws IOException if an input or output error occurred.
     */
    public static void main(String[] args) throws IOException {
        if(args.length != 2) {
            System.out.println("Command Line Format: java main.java.SearchMap input_file output_file");
        }

        String inputFile = args[0];
        String outputFile = args[1];

        SearchMap searchMap = SearchMap.getInstance();
        String inputString = searchMap.read(inputFile);
        if(inputString == null) {
            throw new IOException("Input file loading failed.");
        }

        String outputString = searchMap.executeSearch(inputString);
        searchMap.write(outputString, outputFile);
    }

}
