package saffi.hartal.hw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by saffi on 18/05/17.
 * Assumptions from the file:
 * values Int [0..100], Integers are implied from the example though not stated.
 * The algorithm would work for other numbers as well.
 * <p>
 * The chosen algorithm is simple:
 * 1. build a sumLists triangle step by step
 * 2. each step we choose the max cell above between the two possible options.
 * 3. add that list to the new line  resulting in the max sum per cell of the last line processed.
 * 4. taking the max sum of the last result as the max achievable sum.
 * Details:
 * Sum would be less then 100*100 = 10,000 so we can use Integer.
 * Guideline : code simplicity not memory/speed efficiency/parallelism.
 * We can do it in place, we can use primitive types ... but it would not make the code simpler.
 * Memory footprint could be 101 cells with addition to the input stream if in place algorithm is used..
 * in place algorithm is shorter but not as clear:
 */
/*
    void inPlaceAdd(Stream<Integer> line) {
        int idx = 0;
        int prev = 0;
        for (Integer v : cells) {
            cells.set(idx, Math.max(prev, v));
            prev = v;
            idx++;
        }
        cells.add(prev);
        idx = 0;
        Iterator<Integer> lineIt = line.iterator();
        while (lineIt.hasNext()) {
            Integer v = lineIt.next();
            cells.set(idx, v + cells.get(idx));
            idx += 1;
        }
    }
*/




public class Triangle {

    private int level = 0;
    private ArrayList<Integer> cells = new ArrayList<>();

    Triangle() {
    }

    /**
     * Main method for reading a file representing triangle
     *
     * @param path
     * @return Integer the largest sum trailing from top to bottom.
     * @throws IOException
     */
    public static Integer getMaxSumForTriangleFile(Path path) throws IOException {
        // parsing
        Stream<String> strLines = Files.lines(path).filter( line -> !line.trim().isEmpty() );
        Stream<ArrayList<Integer>> integerLines = strLines.map(Triangle::convertLineToIntegers);

        // digesting
        Triangle triangle = new Triangle();
        integerLines.forEach(triangle::addNextLine);
        return triangle.getMax();
    }


    /**
     * digest additional line
     *
     * @param line
     */
    void addNextLine(ArrayList<Integer> line) {
        level += 1;
        assert (line.size() == level);

        ArrayList<Integer> preparedMax = maxCellValueAbove(cells);
        assert preparedMax.size() == line.size();
        // add the current line.
        this.cells = sumLists(line, preparedMax);
    }

    /**
     * get the final result by finding the max value.
     *
     * @return
     */
    Integer getMax() {
        return Collections.max(cells);
    }


    /**
     * function which takes a line of cells resulting with a list update the cells to represent the max value for each cell
     * @param cells
     */
    static ArrayList<Integer> maxCellValueAbove(ArrayList<Integer> cells) {
        ArrayList<Integer> res = new ArrayList<>(cells.size());
        // the first position has only one cell above
        Integer prev = 0;

        // take the highest of the Cells to be used for the next line same position child
        for (Integer v : cells) {
            res.add(Math.max(prev, v));
            prev = v;
        }
        // the last cell is a parent of the next line
        res.add(prev);
        return res;
    }

    /**
     * sum two Integer arrays having same size.
     * @return ArrayList<Integer>
     */
    static ArrayList<Integer> sumLists(ArrayList<Integer> arr1, ArrayList<Integer> arr2) {
        assert arr1.size() == arr2.size();
        ArrayList<Integer> sumLine = new ArrayList<>(arr1.size());
        for (int i = 0; i < arr1.size(); i++) {
            sumLine.add(arr1.get(i) + arr2.get(i));
        }
        return sumLine;
    }


    /**
     * parses line into integer array list
     * @param lineStr String
     * @return ArrayList<Integer>
     */
    private static ArrayList<Integer> convertLineToIntegers(String lineStr) {
        return convertLineToIntegerStream(lineStr).
                collect(Collectors.toCollection(ArrayList<Integer>::new));
    }

    /**
     * parse Line to Integer Stream
     *
     * @param lineStr String
     * @return Stream<Integer>
     */
    private static Stream<Integer> convertLineToIntegerStream(String lineStr) {
        return Stream.of(lineStr.split("([ \t])+")).map(Integer::parseInt);
    }


    public static void main(String[] args) throws IOException {
        String filename = "src/main/java/saffi/hartal/hw/triangle (1) (5) (19).txt";
        if (args.length > 0) {
            filename = args[0];
        }
        Path path = Paths.get(filename);
        Integer res = getMaxSumForTriangleFile(path);

        System.out.println("filename " + filename + " sum is : " + res);
    }

}