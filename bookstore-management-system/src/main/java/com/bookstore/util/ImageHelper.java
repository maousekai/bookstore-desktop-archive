package com.bookstore.util;

import javafx.scene.image.Image;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageHelper {

    private static final Path PROJECT_ROOT =
            Paths.get(System.getProperty("user.dir"));

    private static final Path IMAGE_ROOT =
            PROJECT_ROOT.resolve("userdata").resolve("images");

    public static Image loadImage(String imageName, String type) {
        if (imageName == null || imageName.isBlank()) return null;

        String folder = type.equals("BOOK") ? "books" : "vpp";

        Path imagePath = IMAGE_ROOT.resolve(folder).resolve(imageName);
        File imageFile = imagePath.toFile();

        if (imageFile.exists()) {
            return new Image(imageFile.toURI().toString());
        }

        System.err.println("Không tìm thấy ảnh: " + imagePath.toAbsolutePath());
        return null;
    }
}
