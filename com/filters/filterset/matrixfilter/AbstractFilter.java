package com.filters.filterset.matrixfilter;

import com.filters.filterset.Matrix.MatrixInt;

public interface AbstractFilter {
    void setSource(MatrixInt source);
    int[][] apply();
    int[][] apply(int[][] result);
}
