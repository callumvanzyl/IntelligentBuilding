package tile;

import entity.DrawableEntity;
import world.World;

/**
 * a tile that can be added to the world grid
 */
public class Tile extends DrawableEntity {
    private transient String name;

    Tile(World world, String name, String spritePath, boolean isCollidable) {
        super(world);
        this.name = name;
        this.spritePath = spritePath;
        this.isCollidable = isCollidable;
    }

    @Override
    public String getName() {
        return name + " Tile";
    }
}
