package com.filters.tests;

import com.filters.filterset.Matrix.MatrixInt;
import com.filters.filterset.Matrix.SubMatrixInt;
import org.junit.Assert;
import org.junit.Test;

public class MatrixTest {

    @Test
    public void cretionTest() {
        int[][] source = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        MatrixInt m = new MatrixInt(source);

        int[][] resultArray = new int[3][3];
        for (int x = 0; x < m.getWidth(); x++)
            for (int y = 0; y < m.getHeight(); y++)
                Assert.assertEquals(source[y][x], m.get(x, y));
    }

    @Test
    public void iterationTest() {
        int[][] source = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Integer[][][] iterationRestults = {
                {
                        {1, 2},
                        {4, 5}
                },
                {
                        {2, 3},
                        {5, 6}
                },
                {
                        {4, 5},
                        {7, 8}
                },
                {
                        {5, 6},
                        {8, 9}
                }
        };

        MatrixInt matrix = new MatrixInt(source);
        Integer[][] result = new Integer[2][2];
        int i = 0;
        for (SubMatrixInt sub : matrix.subIterator(2,2)) {
            result[0][0] = sub.get(0, 0);
            result[1][0] = sub.get(0, 1);
            result[0][1] = sub.get(1, 0);
            result[1][1] = sub.get(1, 1);
            Assert.assertTrue(ArraysComparator.compare(result, iterationRestults[i++]));
        }
    }
}
