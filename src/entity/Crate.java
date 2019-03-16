package entity;

import world.World;

public class Crate extends DrawableEntity {
    Crate(World world) {
        super(world);
        this.isCollidable = true;
        this.spritePath = "/res/sprites/Crate.png";
    }

    @Override
    public String getName() {
        return "Wooden Crate";
    }

    @Override
    public String getDescription() {
        return "A heavy crate. Unfortunately, it is locked.";
    }
}
