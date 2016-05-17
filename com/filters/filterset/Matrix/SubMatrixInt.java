package com.filters.filterset.Matrix;

public class SubMatrixInt implements AbstractMatrixInt{

    private int[][] source;
    private int x, y, width, height;

    public SubMatrixInt(int[][] source) {
        this(source, 0, 0);
    }
    public SubMatrixInt(int[][] source, int x, int y) {
        this(source, 0, 0, 0, 0);
    }
    public SubMatrixInt(int[][] source, int x, int y, int width, int height) {
        this.source = source;
        setSubXY(x, y);
        this.width = width;
        this.height = height;
    }

    public void setSubXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getWidth() {
        return width != 0? width : source[0].length - x;
    }
    @Override
    public int getHeight() {
        return height != 0? height : source.length - y;
    }

    @Override
    public int get(int x, int y) {
        return source[y + this.y][x + this.x];
    }

    @Override
    public void set(int content, int x, int y) {
        source[y + this.y][x + this.x] = content;
    }
}
