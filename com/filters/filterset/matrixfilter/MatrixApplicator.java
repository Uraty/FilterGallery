package com.filters.filterset.matrixfilter;

import com.filters.filterset.Matrix.AbstractFilterMatrix;
import com.filters.filterset.Matrix.AbstractMatrixInt;

public class MatrixApplicator implements AbstractMatrixApplicator{

    private AbstractFilterMatrix matrix;

    public MatrixApplicator(AbstractFilterMatrix matrix) {
        setMatrix(matrix);
    }

    @Override
    public void setMatrix(AbstractFilterMatrix matrix) {
        this.matrix = matrix;
    }

    private static final long mask = 0xFFFFFFFFL;
    @Override
    public long apply(AbstractMatrixInt source) {
        long result = 0;
        for (int x = 0; x < matrix.getWidth(); x++)
            for (int y = 0; y < matrix.getHeight(); y++)
                result += (mask & source.get(x, y)) * matrix.get(x, y);
        return result;
    }
}
