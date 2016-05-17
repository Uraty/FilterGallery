package com.filters.filterset.Matrix;

import java.util.Iterator;

public class FilterMatrix implements AbstractFilterMatrix {

    private float[][] matrix;

    public FilterMatrix(float[][] source) {
        matrix = source;
    }

    public FilterMatrix normalize() {
        float sum = 0;
        for (int x = 0; x < matrix[0].length; x++)
            for (int y = 0; y < matrix.length; y++)
                sum += matrix[y][x];
        for (int x = 0; x < matrix[0].length; x++)
            for (int y = 0; y < matrix.length; y++)
                matrix[y][x] = matrix[y][x] / sum;
        return this;
    }

    @Override
    public float get(int x, int y) {
        return matrix[y][x];
    }

    @Override
    public void set(float content, int x, int y) {
        matrix[y][x] = content;
    }

    @Override
    public int getWidth() {
        return matrix[0].length;
    }
    @Override
    public int getHeight() {
        return matrix.length;
    }
}
