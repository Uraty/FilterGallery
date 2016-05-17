package com.filters.tests;

import org.junit.Assert;
import org.junit.Test;

public class ArraysComparatorTest {

    @Test
    public void compareTest() {
        Integer[] source1 = {1, 2, 3};
        Integer[] source2 = {2};
        Integer[] source3 = new Integer[3];
        source3[0] = 1; source3[1] = 2; source3[2] = 3;

        Integer[][] source4 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Integer[][] source5 = {
                {1, 2, 0},
                {4, 5, 6},
                {0, 8, 0}
        };

        Assert.assertTrue(ArraysComparator.compare(source1, source3));
        Assert.assertTrue(ArraysComparator.compare(source1, source1));
        Assert.assertFalse(ArraysComparator.compare(source1, source2));

        Assert.assertTrue(ArraysComparator.compare(source4, source4));
        Assert.assertFalse(ArraysComparator.compare(source4, source5));
    }
}
