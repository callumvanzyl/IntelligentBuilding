package entity;

import game.Game;
import world.World;

import java.util.Arrays;

/**
 * Manages all of the active entities.
 * Can be used to retrieve a copy of an entity and place it in the world.
 */
public class EntityManager {
    // IF AN ENTITY CLASS NAME IS NOT IN THIS LIST, YOU WON'T BE ABLE TO SPAWN IT!
    private static final String[] ENABLED_ENTITIES = {
            // Moving items
            "Person",

            // Static items
            "SunInAJar",
            "Airplane",
            "BlueChair",
            "Crate",
            "Doormat",
            "OrangeChair",
            "RoundTable",
            "Toaster",

            // Intelligent items
            "Light",
            "Rubble"
    };

    Game game;

    public EntityManager(Game game) {
        this.game = game;
    }

    public String[] getEnabledEntities() {
        return ENABLED_ENTITIES;
    }

    /**
     * Gets a copy of a given entity via its class name
     * If a class with the given name does not exist within the entity package, nothing will be returned!
     * @param name The CLASS NAME of an entity
     * @return A copy of an entity with the type of the given name
     */
    public Entity getEntity(String name) {
        // Needs improvement, reflection is bad practice.
        Entity entity = null;
        if (Arrays.stream(ENABLED_ENTITIES).anyMatch(x -> x.equals(name))) { // Checks if an entity with the given name is enabled
            try {
                Class<?> clazz = Class.forName(this.getClass().getPackageName() + "." + name); // Get the class of the given name
                entity = (Entity) clazz.getDeclaredConstructor(World.class).newInstance(game.getWorld()); // Invoke the constructor and create new instance,
                                                                                                        // we do this so it has access to the core game object
            } catch (Exception e) { } // something bad has happened
        }
        return entity; // return a copy of the entity
    }
}
