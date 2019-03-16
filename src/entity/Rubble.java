package entity;

import world.World;

public class Rubble extends DrawableEntity {
    Rubble(World world) {
        super(world);
        this.isCollidable = false;
        this.spritePath = "/res/sprites/Rubble.png";
    }

    @Override
    public String getName() {
        return "Rubble";
    }

    @Override
    public String getDescription() {
        return "Decreases the speed of anyone who walks over it";
    }
}
