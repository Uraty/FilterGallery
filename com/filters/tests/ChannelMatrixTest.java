package com.filters.tests;

import com.filters.filterset.Matrix.ChannelMatrix;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChannelMatrixTest {

    @Test
    public void channelMaskTest() {
        ChannelMatrix matrix = new ChannelMatrix(null, 3);
        Assert.assertEquals(Integer.toHexString(0xFF), Integer.toHexString(matrix.getMask()));
        Assert.assertEquals(Integer.toHexString(0xFFFFFF00), Integer.toHexString(matrix.getLeftMask()));
        matrix = new ChannelMatrix(null, 2);
        Assert.assertEquals(Integer.toHexString(0xFF00), Integer.toHexString(matrix.getMask()));
        Assert.assertEquals(Integer.toHexString(0xFFFF0000), Integer.toHexString(matrix.getLeftMask()));
        matrix = new ChannelMatrix(null, 1);
        Assert.assertEquals(Integer.toHexString(0xFF0000), Integer.toHexString(matrix.getMask()));
        Assert.assertEquals(Integer.toHexString(0xFF000000), Integer.toHexString(matrix.getLeftMask()));
        matrix = new ChannelMatrix(null, 0);
        Assert.assertEquals(Integer.toHexString(0xFF000000), Integer.toHexString(matrix.getMask()));
        Assert.assertEquals(Integer.toHexString(0x00000000), Integer.toHexString(matrix.getLeftMask()));
    }

}