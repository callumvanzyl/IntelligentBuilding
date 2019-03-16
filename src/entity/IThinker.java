package entity;

/**
 * A thinking entity
 * To save resources, only implement this in classes that need to do an update every frame
 */
public interface IThinker {
    void update(); // This is invoked every frame, use it sparingly
}
