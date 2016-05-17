package com.filters.tests;

import com.filters.filterset.Matrix.*;
import com.filters.filterset.matrixfilter.MatrixApplicator;
import org.junit.Assert;
import org.junit.Test;

public class MatrixApplicatorTest {

    int[][] testImage = {
            {0xFF010101, 0xFF020202, 0xFF030303},
            {0xFF040404, 0xFF050505, 0xFF060606},
            {0xFF070707, 0xFF080808, 0xFF090909}
    };

    @Test
    public void applyMatrixTest() {
        int[][] test = {
                {2, 4, 8},
                {2, 4, 8},
                {4, 8, 16}
        };
        float[][] matrix = {
                {2, 4, 2},  // sum = 4 + 16 + 16 = 36
                {1, 2, 1},  // sum = 2 + 8 + 8 = 18
                {2, 5, 2}   // sum = 8 + 40 + 32 = 80
        };                  // sum = 134
        MatrixApplicator applicator = new MatrixApplicator(new FilterMatrix(matrix));
        int result = (int) applicator.apply(new MatrixInt(test));
        Assert.assertEquals(134, result);
    }

    @Test
    public void channelTest() {
        float[][] matrix = {
                {1f, 1f, 1f},       // sum = 6
                {.5f, 2f, .5f},     // sum = 15
                {1f, 2f, 1f}        // sum = 32
        };                          // final sum = 53 (0x35)
        ChannelMatrix c = new ChannelMatrix(new MatrixInt(testImage), 2);
        MatrixApplicator applicator = new MatrixApplicator(new FilterMatrix(matrix));
        int result = (int) applicator.apply(c);
        Assert.assertEquals(0x00003500, result);
    }
}
