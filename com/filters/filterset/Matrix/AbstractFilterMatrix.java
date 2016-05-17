package com.filters.filterset.Matrix;

public interface AbstractFilterMatrix {
    void set(float content, int x, int y);
    float get(int x, int y);
    int getWidth();
    int getHeight();
}
