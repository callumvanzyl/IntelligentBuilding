package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/**
 * A group of entities.
 * Allows for entities to be manipulated in batches.
 */
public class EntityGroup implements Serializable {
    private ArrayList<Entity> entities = new ArrayList<>();

    /**
     * Add a drawable entity to the group.
     * This is a separate function as drawable entities are drawn according to their priority.
     * @param entity The entity to add to the group
     */
    public void add(DrawableEntity entity) {
        entities.add(entity);
        // This sorts the list so that entities with a lower drawing priority are placed at the front of the list,
        // this makes sure that they are drawn below all entities with a higher priority
        if (entities.size() > 1) { // If there are multiple entities in the group...
            for (int i = entities.size() - 1; i > 0; i--) {
                if (entities.get(i - 1) instanceof DrawableEntity) { // We only care about other drawable entities
                    DrawableEntity currentEntity = (DrawableEntity) entities.get(i - 1);
                    if (entity.getDrawingPriority() < currentEntity.getDrawingPriority()) { // Makes sure that entities with a lower drawing priority are placed at the front of the list
                        Collections.swap(entities, i, i - 1); // Swap the current and previous entity
                    }
                }
            }
        }
    }

    /**
     * Add an entity to the group
     * @param entity The entity to add to the group
     */
    public void add(Entity entity) {
        entities.add(entity);
    }

    /**
     * Combine this entity group with another, resulting in it containing all entities from both this and the other group.
     * @param other The entity group to be combined with
     */
    public void combine(EntityGroup other) {
        this.entities.addAll(Arrays.asList(other.getEntities()));
    }

    /**
     * Destroys every entity in the group
     */
    public void destroy() {
        entities.forEach(Entity::destroy);
    }

    /**
     * Updates every entity in the group
     * Will also invoke the garbage collector on destroyed entities
     */
    public void update() {
        Iterator<Entity> itr = entities.iterator();
        while (itr.hasNext()){ // While there are entities in the group...
            Entity entity  = itr.next();

            if (entity.getIsDestroyed()) { // If an entity is destroyed, remove it from the group, it will be garbage collected.
                itr.remove();
                continue;
            }

            if (entity instanceof IThinker) // If an entity is a thinker...
                ((IThinker) entity).update(); // Invoke its update method.
        }
    }

    public Entity[] getEntities() {
        return entities.toArray(new Entity[0]);
    }
}
