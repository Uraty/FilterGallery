package com.filters.tests;

import com.filters.filterset.Filters;

public class Main {

    public static void main(String[] args) {
        Filters filters = new Filters("D:/gallery.json");
        filters.with("D:/img.jpg").filter("relief").save("D:/img2.jpg", "jpeg");
    }
}
