package com.filters.tests;

import com.filters.filterset.FilterGallery;
import com.filters.filterset.Matrix.MatrixInt;
import com.filters.filterset.matrixfilter.AbstractFilter;
import com.filters.filterset.matrixfilter.MatrixFilter;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FilterGalleryTest {

    private String testFile = "{\n  " +
            "\"filters\" : [\n    " +
            "{ \"name\" : \"test\",  \"matrices\" : [[[1,1,1],[0.5,2,0.5],[1,2,1]]]}]}";

    @Test
    public void loadTest() throws IOException {
        FilterGallery gallery = new FilterGallery(testFile);
        AbstractFilter filter = gallery.get("test");

        int[][] testImage = {
                {0xFF010101, 0xFF020202, 0xFF030303},
                {0xFF04AA04, 0xFF050505, 0xFF060606},
                {0xFF070707, 0xFF08BB08, 0xFF090909}
        };

        filter.setSource(new MatrixInt(testImage));
        int[][] result = filter.apply();
        Assert.assertEquals(Integer.toHexString(0xFF35FF35),
                Integer.toHexString(result[1][1]));
    }

}
