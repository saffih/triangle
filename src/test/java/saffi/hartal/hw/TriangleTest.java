package saffi.hartal.hw;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertSame;

/**
 * Created by saffi on 18/05/17.
 */
public class TriangleTest {
    @Test
    public void example1() throws Exception {
        Triangle triangle = new Triangle();
        triangle.addNextLine(arrayToList(new int[]{3}));
        triangle.addNextLine(arrayToList(new int[]{7,4}));
        triangle.addNextLine(arrayToList(new int[]{2,4,6}));
        triangle.addNextLine(arrayToList(new int[]{8,5,9,3}));
        assertSame( triangle.getMax(), 23);
    }

    @Test
    public void maxAboveCells() throws Exception {
        int arr1[]= new int[]{1,2,3,4,5};
        ArrayList<Integer> lst1 = arrayToList(arr1);
        ArrayList<Integer> lst2 = arrayToList(arr1);
        Collections.reverse(lst2);
        ArrayList<Integer> max1 = Triangle.maxCellValueAbove(lst1);
        ArrayList<Integer> max2 = Triangle.maxCellValueAbove(lst2);

        // first and last match
        assertEquals( max1.get(0), lst1.get(0));
        assertEquals( max1.get(max1.size()-1), lst1.get(lst1.size()-1));
        // ascending array - takes right cell
        // removing the last should produce the same
        max1.remove(max1.size()-1);
        assertEquals( max1, lst1);

        // first and last match
        assertEquals( max2.get(0), lst2.get(0));
        assertEquals( max2.get(max2.size()-1), lst2.get(lst2.size()-1));
        // descending array - takes left cell
        // remove the first cell should produce the same array
        max2.remove(0);
        assertEquals( max2, lst2);

    }

    private static ArrayList<Integer> arrayToList(int[] arr1) {
        ArrayList<Integer> lst1 = new ArrayList<>();
        for(int a : arr1){
            lst1.add(a);
        }
        return lst1;
    }

    @Test
    public void sumList() throws Exception {
        ArrayList<Integer> lst1 = new ArrayList<>();
        int arr1[]= new int[]{1,2,3,4,5};
        for(int a : arr1){
            lst1.add(a);
        }
        ArrayList<Integer> lst2 = arrayToList(arr1);
        Collections.reverse(lst2);
        ArrayList<Integer> res = Triangle.sumLists(lst1, lst2);
        // resulting array having same length
        assertSame (res.size(), lst1.size());
        // all correct.
        for (int v:res) {
            assert v==6;
        }
    }


    @Test
    public void calcTriangle() throws IOException {
        String filename = "src/main/java/saffi/hartal/hw/triangle (1) (5) (19).txt";
        Path path = Paths.get(filename);
        Integer res = Triangle.getMaxSumForTriangleFile(path);
        System.out.println(res);
        assertEquals(Math.toIntExact(res),7273);
    }

}