package com.filters.filterset.matrixfilter;

import com.filters.filterset.Matrix.*;

public class MatrixFilter implements AbstractFilter{

    private AbstractFilterMatrix[] matrices;

    public MatrixFilter(AbstractFilterMatrix... matrices) {
        setMatrices(matrices);
    }

    private AbstractFilterMatrix defaultMatrix = new FilterMatrix(new float[][] {{1f}});
    public void setMatrices(AbstractFilterMatrix... matrices) {
        this.matrices = new AbstractFilterMatrix[4];
        if (matrices.length == 1) {
            this.matrices[0] = defaultMatrix;
            for (int i = 1; i < 4; i++)
                this.matrices[i] = matrices[0];
        }
        else {
            int startIndex = this.matrices.length - matrices.length;
            for (int i = 0; i < matrices.length; i++)
                this.matrices[i + startIndex] = matrices[i];
            for (int i = 0; i < startIndex; i++)
                this.matrices[i] = defaultMatrix;
        }
    }

    private MatrixInt source;
    @Override
    public void setSource(MatrixInt source) {
        this.source = source;
    }

    @Override
    public int[][] apply() {
        return apply(new int[source.getHeight()][source.getWidth()]);
    }

    @Override
    public int[][] apply(int[][] result) {
        for (int i = 0; i < matrices.length; i++) {
            ChannelMatrix channelMatrix = new ChannelMatrix(source, i);
            MatrixApplicator applicator = new MatrixApplicator(matrices[i]);
            long mask = 0xFFFFFFFF00000000L | channelMatrix.getLeftMask();
            int maxPixel = 255 << (3 - i) * 8;

            int xStart = (int) ((matrices[i].getWidth() - 0.1f)/2);
            int xEnd = result[0].length - xStart - 1;
            int x = xStart, y = matrices[i].getHeight()/2;

            for (SubMatrixInt sub : source.subIterator(matrices[i].getWidth(), matrices[i].getHeight())) {
                channelMatrix.setSource(sub);
                long pixel = applicator.apply(channelMatrix);
                pixel &= mask >> 8;
                result[y][x] |= (pixel & mask) > 0? maxPixel : (int) pixel;
                if (x >= xEnd) {
                    x = xStart; y++;
                } else
                    x++;
            }
        }
        return result;
    }
}
