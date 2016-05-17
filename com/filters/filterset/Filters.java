package com.filters.filterset;

import com.filters.filterset.Matrix.MatrixInt;
import com.filters.filterset.matrixfilter.AbstractFilter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class Filters implements Iterable<String> {

    private FilterGallery gallery;
    public Filters(String filePath) {
        gallery = new FilterGallery(readFile(filePath));
    }

    private int[][] source;
    private ImageActions actions = new ImageActions();
    public ImageActions with(BufferedImage source) {
        this.source = new int[source.getHeight()][source.getWidth()];
        for (int y = 0; y < source.getHeight(); y++)
            for (int x = 0; x < source.getWidth(); x++)
                this.source[y][x] = source.getRGB(x, y);
        return actions;
    }

    public ImageActions with(int[][] source) {
        this.source = source;
        return actions;
    }

    public ImageActions with(String imagePath) {
        try {

            with(ImageIO.read(new File(imagePath)));
        } catch (IOException e) {
            throw new IllegalArgumentException("Wrong file path. File doesn't exist.");
        }
        return actions;
    }

    @Override
    public Iterator<String> iterator() {
        return gallery.iterator();
    }

    public class ImageActions {

        int [][] result = source;
        private ResultActions actions = new ResultActions();

        public ResultActions filter(String filterName) {
            AbstractFilter filter = gallery.get(filterName);
            if (filter != null) {
                filter.setSource(new MatrixInt(source));
                result = filter.apply();
            }
            return actions;
        }

        public ResultActions filter(String filterName, int count) {
            AbstractFilter filter = gallery.get(filterName);
            if (filter != null && count > 0 && count < 100) {
                int[][][] buffers = new int[2][source.length][source[0].length];
                int resultBuffer = 0, sourceBuffer = 1;
                buffers[sourceBuffer] = source;
                for (int i = 0; i < count; i++) {
                    filter.setSource(new MatrixInt(buffers[sourceBuffer]));
                    filter.apply(buffers[resultBuffer]);
                    resetBuffer(buffers[sourceBuffer]);
                    resultBuffer = sourceBuffer;
                    sourceBuffer = resultBuffer != 1? 1 : 0;
                }
                result = buffers[sourceBuffer];
            }
            return actions;
        }

        private void resetBuffer(int[][] buffer) {
            for (int i = 0; i < buffer.length; i++)
                for (int j = 0; j < buffer[i].length; j++)
                    buffer[i][j] = 0;
        }

        public class ResultActions {
            public void save(String path, String format) {
                try {
                    ImageIO.write(toBufferedImage(), format, new File (path));
                } catch (IOException e) {
                    throw new IllegalArgumentException("Can't create a file.");
                }
            }
            public int[][] toArray() {
                return result;
            }
            public BufferedImage toBufferedImage() {
                BufferedImage image = new BufferedImage(result[0].length,
                                                        result.length,
                                                        BufferedImage.TYPE_INT_RGB);
                for (int y = 0; y < image.getHeight(); y++)
                    for (int x = 0; x < image.getWidth(); x++)
                        image.setRGB(x, y, result[y][x]);
                return image;
            }
        }
    }

    private String readFile(String filePath) {
        try {
            StringBuilder filtersJson = new StringBuilder();
            Files.readAllLines(Paths.get(filePath)).forEach((String s) -> filtersJson.append(s).append("\n"));
            return filtersJson.toString();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Wrong file path. File doesn't exist.");
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't read file. Maybe file is broken.");
        }
    }
}


