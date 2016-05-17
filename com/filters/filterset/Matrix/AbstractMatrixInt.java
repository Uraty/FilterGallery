package com.filters.filterset.Matrix;

public interface AbstractMatrixInt {
    void set(int content, int x, int y);
    int get(int x, int y);
    int getWidth();
    int getHeight();
}
