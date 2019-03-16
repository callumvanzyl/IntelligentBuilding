package io;

import world.World;

import java.io.*;

/**
 * used to serialise a world for loading and saving, including entities and such
 */
public class WorldSerializer {
    /**
     * attempt to load the world located at path
     * @param path the relative path of the world file
     * @return a loaded world, if it exists
     * @throws Exception
     */
    public World load(String path) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(path); // get world file
        ObjectInputStream input = new ObjectInputStream(fileInputStream);
        return (World) input.readObject(); // attempt to deserialise
    }

    /**
     * attempt to save a world to the file system
     * @param path the path to save to
     * @param world the world to save
     * @throws Exception
     */
    public void save(String path, World world) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(path); // get world file
        ObjectOutputStream output = new ObjectOutputStream(fileOutputStream);
        output.writeObject(world); // attempt to write world data to disk
        output.flush(); // release resources
        output.close();
    }
}
