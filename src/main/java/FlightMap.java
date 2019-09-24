package main.java;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class FlightMap {

    private static FlightMap instance;

    private Map<String, HashMap<String, Integer>> flightMap;
    private String start;

    private HashMap<String, Pair<String, Integer>> priceMap;
    private int maxPathLength;
    private int maxPriceLength;

    public String run(String inputString) {
        this.reset();
        this.parseContent(inputString);
        this.computePrices();
        return this.generateOutput();
    }

    private void reset() {
        this.flightMap = new HashMap<>();
        this.priceMap = new HashMap<>();
        this.maxPathLength = 1;
        this.maxPriceLength = 1;
    }

    private void parseContent(String inputString) {
        String[] lines = inputString.split("\n");
        this.start = lines[0].trim();
        for(int i  = 1; i < lines.length; i++) {
            String line = lines[i].trim();
            String[] elements = line.split(" ");

            String from = elements[0];
            String to = elements[1];
            Integer price = Integer.parseInt(elements[2]);

            // construct nodes in flight map
            if(!this.flightMap.containsKey(from)) {
                this.flightMap.put(from, new HashMap<>());
            }
            if(!this.flightMap.containsKey(to)) {
                this.flightMap.put(to, new HashMap<>());
            }

            // construct edge in flight map
            if(!this.flightMap.get(from).containsKey(to)) {
                this.flightMap.get(from).put(to, price);
            }
        }
    }

    private void computePrices() {
        this.priceMap.put(this.start, new Pair<>(this.start, 0));

        Queue<String> openList = new LinkedList<>();
        openList.add(start);

        while(!openList.isEmpty()) {
            String from = openList.remove();
            String pathToHere = priceMap.get(from).getKey();
            Integer priceToHere = priceMap.get(from).getValue();

            HashMap<String, Integer> outgoingEdges = flightMap.get(from);
            for(String to: outgoingEdges.keySet()) {
                if(priceMap.containsKey(to)) {
                    continue;
                }

                Integer priceOfEdge = outgoingEdges.get(to);

                // insert price for the location
                String path = pathToHere + ", " + to;
                Integer price = priceToHere + priceOfEdge;
                priceMap.put(to, new Pair<>(path, price));

                // update max path length
                this.maxPathLength = Math.max(this.maxPathLength, path.length());

                // update max price length
                String priceString = "$" + price;
                this.maxPriceLength = Math.max(this.maxPriceLength, priceString.length());

                // insert edge to queue
                openList.add(to);
            }
        }
    }

    private String generateOutput() {
        StringBuilder sb = new StringBuilder("");

        // build headline
        int pathWidth = Math.max(21, this.maxPathLength + 2);
        int priceWidth = Math.max(10, this.maxPriceLength);
        String headline = String.format("%-13s%-" + pathWidth + "s%-" + priceWidth + "s\n",
                "Destination",
                "Flight Route from " + this.start,
                "Total Cost");
        sb.append(headline);

        for(String dest: priceMap.keySet()) {
            if(dest == this.start) {
                continue;
            }

            sb.append(String.format("%-13s%-" + pathWidth + "s$%-" + (priceWidth - 1) + "d\n",
                    dest,
                    priceMap.get(dest).getKey(),
                    priceMap.get(dest).getValue()));
        }

        // remove last end line character
        sb.setLength(Math.max(sb.length() - 1, 0));

        return sb.toString();
    }

    public static FlightMap getInstance() {
        if(instance == null) {
            instance = new FlightMap();
        }
        return instance;
    }

}
