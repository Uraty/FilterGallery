package com.filters.filterset.Matrix;

public class ChannelMatrix implements AbstractMatrixInt{

    private AbstractMatrixInt source;
    private int mask, leftMask;

    public int getMask() { return mask; }
    public int getLeftMask() { return leftMask;}

    private int getChannelMask(int shift, int channelLength) {
        return shift >= 32? 0 : 0xFFFFFFFF >>> (32 - channelLength) << shift;
    }

    public ChannelMatrix(AbstractMatrixInt source, int shift, int channelLength) {
        this.source = source;
        mask = getChannelMask(shift, channelLength);
        leftMask = getChannelMask(shift + channelLength, 31);
    }
    public ChannelMatrix(AbstractMatrixInt source, int channel) {
        this(source, (3 - channel) * 8, 8);
    }

    public void setSource(AbstractMatrixInt source) {
        this.source = source;
    }

    @Override
    public void set(int content, int x, int y) {

    }

    @Override
    public int get(int x, int y) {
        return source.get(x, y) & mask;
    }

    @Override
    public int getWidth() {
        return source.getWidth();
    }

    @Override
    public int getHeight() {
        return source.getHeight();
    }
}
