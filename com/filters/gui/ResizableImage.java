package com.filters.gui;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ResizableImage extends ImageView {

    @Override
    public double minHeight(double width) {
        return 40;
    }

    @Override
    public double minWidth(double height) {
        return 40;
    }

    @Override
    public double maxHeight(double width) {
        return 1000;
    }

    @Override
    public double maxWidth(double height) {
        return 1000;
    }

    @Override
    public double prefHeight(double width) {
        return getImage() == null? minHeight(width) : getImage().getHeight();
    }

    @Override
    public double prefWidth(double height) {
        return 200;
    }

    @Override
    public void resize(double width, double height) {
        setFitHeight(height);
        setFitWidth(width);
    }

    @Override
    public boolean isResizable() {
        return true;
    }
}
