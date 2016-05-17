package com.filters.gui;

import com.filters.filterset.Filters;
import com.filters.gui.Model.Action;
import com.filters.gui.Model.History;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Controller {
    @FXML
    private ImageView imageBefore;
    @FXML
    private ImageView imageAfter;
    @FXML
    private ListView filtersList;

    private Action actionLoad = new Action() {
        @Override
        public void doAction() {
            buffer = original;
        }
    };

    public void onLoadImage(ActionEvent actionEvent) {
        Image image = new Image("file:" + getImagePath());
        original = SwingFXUtils.fromFXImage(image, null);
        history.add(actionLoad);
        actionLoad.doAction();
        imageBefore.setImage(SwingFXUtils.toFXImage(original, null));
    }

    public void onSave(ActionEvent actionEvent) {
        try {
            ImageIO.write(buffer, "jpeg", new File(getSaveImagePath()));
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't create a file.");
        }
    }

    private class ActionFilter implements Action {
        private String filterName;
        ActionFilter(String filterName) { this.filterName = filterName; }
        @Override
        public void doAction() {
            result = filters.with(buffer).filter(filterName).toBufferedImage();
            buffer = result;
        }
    };
    private History history = new History();

    @FXML
    private void initialize() {
        filtersList.getSelectionModel().selectedItemProperty().addListener(listListener);
    }

    private String selectedFilter;
    private BufferedImage original, buffer, result;
    private ChangeListener<String> listListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            SwingFXUtils.fromFXImage(imageBefore.getImage(), buffer);
            result = filters.with(buffer).
                    filter(selectedFilter = newValue).
                    toBufferedImage();
            imageAfter.setImage(SwingFXUtils.toFXImage(result, null));
        }
    };

    public void onUndo(ActionEvent actionEvent) {
        history.undo();
        imageBefore.setImage(SwingFXUtils.toFXImage(buffer, null));
        seeFilterResult();
    }


    private String getSaveImagePath() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save as");
        String path = chooser
                .showSaveDialog(filtersList.getScene().getWindow())
                .getPath();
        if (!path.matches(".+\\.jpg")) path += ".jpg";
        return path;
    }
    private String getImagePath() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select image to filter");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpg*"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        return chooser
                .showOpenDialog(filtersList.getScene().getWindow())
                .getPath();
    }
    private String getFiltersJSONPath() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select filters JSON file");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Filters", "*.json"));
        return chooser
                .showOpenDialog(filtersList.getScene().getWindow())
                .getPath();
    }

    private Filters filters;
    public void onLoadFilters(ActionEvent actionEvent) {
        filters = new Filters(getFiltersJSONPath());
        ObservableList namesOfFilters = FXCollections.observableArrayList();
        filters.forEach(f -> namesOfFilters.add(f));
        filtersList.setItems(namesOfFilters);
    }

    private void seeFilterResult() {
        String filterName = filtersList
                .getSelectionModel()
                .selectedItemProperty()
                .getValue().toString();
        listListener.changed(null, null, filterName);
    }

    public void onApply(ActionEvent actionEvent) {
        imageBefore.setImage(SwingFXUtils.toFXImage(result, null));
        buffer = result;
        seeFilterResult();
        history.add(new ActionFilter(selectedFilter));
    }
}
