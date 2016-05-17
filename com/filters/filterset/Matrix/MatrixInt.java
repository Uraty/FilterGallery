package com.filters.filterset.Matrix;

import java.util.Iterator;

public class MatrixInt implements AbstractMatrixInt {

    private int[][] source;

    public MatrixInt(int[][] source) {
        this.source = source;
    }

    @Override
    public int getWidth() {
        return source[0].length;
    }
    @Override
    public int getHeight() {
        return source.length;
    }

    @Override
    public int get(int x, int y) {
        return source[y][x];
    }
    @Override
    public void set(int content, int x, int y) {
        source[y][x] = content;
    }

    public Iterable<SubMatrixInt> subIterator(int width, int height) {
        return new Iterable<SubMatrixInt>() {
            @Override
            public Iterator<SubMatrixInt> iterator() {
                return new SubIterator(width, height);
            }
        };
    }

    private class SubIterator implements Iterator<SubMatrixInt> {

        private int x, y;
        private int maxX, maxY;
        private SubMatrixInt subMatrix = new SubMatrixInt(source);

        public SubIterator(int width, int height) {
            maxX = getWidth() - width;
            maxY = getHeight() - height;
        }

        @Override
        public boolean hasNext() {
            return y <= maxY;
        }

        @Override
        public SubMatrixInt next() {
            subMatrix.setSubXY(x, y);
            if (++x > maxX) {
                x = 0;
                y++;
            }
            return subMatrix;
        }
    }

}
