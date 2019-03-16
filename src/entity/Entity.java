package entity;

import world.World;

import java.io.Serializable;

/**
 * A basic entity that can be added to an entity group and inserted into the world.
 */

public abstract class Entity implements Serializable {
    public Boolean isCollidable = false; // Will be ignored by pathfinding if true
    private Boolean isDestroyed = false; // If this is true, the entity will be disposed of on the next tick
    World world;

    public Entity(World world) {
        this.world = world;
    }

    /**
     * Marks an entity to be destroyed.
     * It will be destructed the next time it is handled within an entity group.
     */
    public void destroy() {
        isDestroyed = true;
    }

    public Boolean getIsCollidable() {
        return isCollidable;
    }

    public Boolean getIsDestroyed() {
        return isDestroyed;
    }

    public String getName() { // This method must be overridden in a subclass if the entity has a name
        return "Unknown";
    }

    public String getDescription() { // This method must be overridden in a subclass if the entity has a description
        return "";
    }
}