package com.filters.tests;

import com.filters.filterset.Matrix.FilterMatrix;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class FilterMatrixTest {

    @Test
    public void normalize() throws Exception {
        float[][] result = {
                {.1f, .2f, .3f},
                {.1f, .2f, .1f}
        };
        FilterMatrix matrix = new FilterMatrix(new float[][] {
                {1f, 2f, 3f},
                {1f, 2f, 1f}
        });
        matrix.normalize();
        for (int y = 0; y < result.length; y++)
            for (int x = 0; x < result.length; x++)
                Assert.assertEquals(result[y][x], matrix.get(x,y), 0.1f);
    }

    public void speedTest() {
        int size = 1000;
        Random r = new Random();
        int[][] a = new int[size][size];
        for (int x = 0; x < a.length; x++)
            for (int y = 0; y < a[0].length; y++)
                a[x][y] = r.nextInt();

        long start = System.currentTimeMillis();
        byte [][][] res = new byte[4][size][size];
        for (int c = 0; c < res.length; c++)
            for (int x = 0; x < res[0].length; x++)
                for (int y = 0; y < res[0][0].length; y++)
                    res[c][x][y] = (byte) (a[x][y] >> (c + 5));
        int[][] b = new int[size][size];
        for (int x = 0; x < a.length; x++)
            for (int y = 0; y < a[0].length; y++)
                b[x][y] = res[0][x][y] & res[1][x][y] << 2 & res[2][x][y] << 1 & res[3][x][y] << 2;
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}