package com.filters.filterset.matrixfilter;

import com.filters.filterset.Matrix.AbstractFilterMatrix;
import com.filters.filterset.Matrix.AbstractMatrixInt;

public interface AbstractMatrixApplicator {
    void setMatrix(AbstractFilterMatrix matrix);
    long apply(AbstractMatrixInt source);
}
