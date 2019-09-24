package main.java;

import java.io.*;
import java.nio.file.*;

public class SearchMap {

    private static SearchMap instance = null;

    public String read(String inputFile) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(inputFile));
            return new String(encoded);
        }
        catch (IOException ioe) {
            return null;
        }
    }

    public String executeSearch(String input) {
        FlightMap flightMap = FlightMap.getInstance();
        String output = flightMap.run(input);
        return output;
    }

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

    public static SearchMap getInstance() {
        if(instance == null) {
            instance = new SearchMap();
        }
        return instance;
    }

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
