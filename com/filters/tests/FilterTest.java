package com.filters.tests;

import com.filters.filterset.Matrix.*;
import com.filters.filterset.matrixfilter.MatrixApplicator;
import com.filters.filterset.matrixfilter.MatrixFilter;
import org.junit.Assert;
import org.junit.Test;

public class FilterTest {

    int[][] testImage = {
            {0xFF010101, 0xFF020202, 0xFF030303},
            {0xFF04AA04, 0xFF050505, 0xFF060606},
            {0xFF070707, 0xFF08BB08, 0xFF090909}
    };

    @Test
    public void filterTest() {
        float[][] matrix = {
                {1f, 1f, 1f},       // sum = 6
                {.5f, 2f, .5f},     // sum = 15
                {1f, 2f, 1f}        // sum = 32
        };                          // final sum = 53 (0x35)
        MatrixFilter filter = new MatrixFilter(new FilterMatrix(matrix));
        filter.setSource(new MatrixInt(testImage));
        int[][] result = filter.apply();
        Assert.assertEquals(Integer.toHexString(0xFF35FF35),
                            Integer.toHexString(result[1][1]));
    }

    @Test
    public void fullTest() {
        int[][] testImage = {
                {0xFF000000, 0xFF020202, 0xFF040404, 0xFF060606},
                {0xFF040404, 0xFF060606, 0xFF020202, 0xFF020202},
                {0xFF080808, 0xFF080808, 0xFF040404, 0xFF040404},
                {0xFF000000, 0xFF020202, 0xFF040404, 0xFF080808},
        };
        FilterMatrix[] matrices = {
                new FilterMatrix(new float[][] {
                        {0.5f}
                }),
                new FilterMatrix(new float[][] {
                        {0.5f, 0.5f, 0.5f},
                        {0.5f, 0.5f, 0.5f},
                        {0.5f, 0.5f, 0.5f},
                })
        };
        MatrixFilter filter = new MatrixFilter(matrices[0], matrices[1], matrices[0], matrices[1]);
        filter.setSource(new MatrixInt(testImage));
        int[][] result = filter.apply();

        Assert.assertEquals(Integer.toHexString(0x7F130313),
                            Integer.toHexString(result[1][1]));
        Assert.assertEquals(Integer.toHexString(0x7F130113),
                            Integer.toHexString(result[1][2]));
        Assert.assertEquals(Integer.toHexString(0x7F130413),
                            Integer.toHexString(result[2][1]));
        Assert.assertEquals(Integer.toHexString(0x7F140214),
                            Integer.toHexString(result[2][2]));
    }

}
