package test.java;
import main.java.FlightMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestFlightMap {

    private FlightMap flightMap;

    public void setUp() {
        flightMap = FlightMap.getInstance();
    }

    @Test
    public void testPrintHeading() {
        String input = "L\n" +
                       "L L 1";

        String expectedOutput = "Destination  Flight Route from L  Total Cost";

        String actualOutput = FlightMap.getInstance().run(input);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testSingleDestination() {
        String input = "P\n" +
                       "P Q 200";

        String expectedOutput = "Destination  Flight Route from P  Total Cost\n" +
                                "Q            P, Q                 $200      ";

        String actualOutput = FlightMap.getInstance().run(input);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testMultipleDestination() {
        String input = "P\n" +
                       "P W 200\n" +
                       "P R 300\n" +
                       "R X 200\n" +
                       "Q X 375\n" +
                       "W S 250\n" +
                       "S T 300\n" +
                       "T W 350\n" +
                       "W Y 500\n" +
                       "Y Z 450\n" +
                       "Y R 600";

        String expectedOutput = "Destination  Flight Route from P  Total Cost\n" +
                                "R            P, R                 $300      \n" +
                                "S            P, W, S              $450      \n" +
                                "T            P, W, S, T           $750      \n" +
                                "W            P, W                 $200      \n" +
                                "X            P, R, X              $500      \n" +
                                "Y            P, W, Y              $700      \n" +
                                "Z            P, W, Y, Z           $1150     ";

        String actualOutput = FlightMap.getInstance().run(input);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testUnreachableDestination() {
        String input = "P\n" +
                       "P Q 200\n" +
                       "A B 300";

        String expectedOutput = "Destination  Flight Route from P  Total Cost\n" +
                                "Q            P, Q                 $200      ";

        String actualOutput = FlightMap.getInstance().run(input);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testCycleInMap() {
        String input = "P\n" +
                       "P Q 200\n" +
                       "Q R 300\n" +
                       "R P 400\n";

        String expectedOutput = "Destination  Flight Route from P  Total Cost\n" +
                                "Q            P, Q                 $200      \n" +
                                "R            P, Q, R              $500      ";

        String actualOutput = FlightMap.getInstance().run(input);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testLongRoute() {
        String input = "A\n" +
                       "A B 1\n" +
                       "B C 1\n" +
                       "C D 1\n" +
                       "D E 1\n" +
                       "E F 1\n" +
                       "F G 1\n" +
                       "G H 1";

        String expectedOutput = "Destination  Flight Route from A     Total Cost\n" +
                                "B            A, B                    $1        \n" +
                                "C            A, B, C                 $2        \n" +
                                "D            A, B, C, D              $3        \n" +
                                "E            A, B, C, D, E           $4        \n" +
                                "F            A, B, C, D, E, F        $5        \n" +
                                "G            A, B, C, D, E, F, G     $6        \n" +
                                "H            A, B, C, D, E, F, G, H  $7        ";

        String actualOutput = FlightMap.getInstance().run(input);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testLargeCost() {
        String input = "P\n" +
                       "P Q 1\n" +
                       "P R 2147483647";

        String expectedOutput = "Destination  Flight Route from P  Total Cost \n" +
                                "Q            P, Q                 $1         \n" +
                                "R            P, R                 $2147483647";

        String actualOutput = FlightMap.getInstance().run(input);
        assertEquals(expectedOutput, actualOutput);
    }

}
