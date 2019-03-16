package io;

import javafx.scene.image.Image;

import java.util.HashMap;

/**
 * a cache used to store images that are fetched frequently
 * images are automatically stored when they are fetched with this class
 */
public class ImageCache {
    private HashMap<String, Image> cache = new HashMap<>();

    /**
     * fetch an image from the cache
     * @param path the relative path of the image to fetch
     * @return the image at path, if it exists
     */
    public Image getImage(String path) {
        Image cached = cache.get(path); // try get from cache
        if (cached != null) { // if it exists just return it
            return cached;
        } else {  // if it doesnt exist add it to the cache and return
            Image image = new Image(path);
            cache.put(path, image); // associate path with relevant image object
            return image;
        }
    }
}
