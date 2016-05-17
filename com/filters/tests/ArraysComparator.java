package com.filters.tests;

public final class ArraysComparator {

    public static boolean compare(Number[] one, Number[] two) {
        if (one.length != two.length) return false;
        for (int i = 0; i < one.length; i++)
            if (!one[i].equals(two[i]))
                return false;
        return true;
    }

    public static boolean compare(Number[][] one, Number[][] two) {
        if (one.length != two.length) return false;
        for (int i = 0; i < one.length; i++)
            if (!compare(one[i], two[i]))
                return false;
        return true;
    }

}
