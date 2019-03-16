package entity;

import world.World;

public class Toaster extends DrawableEntity {
    Toaster(World world) {
        super(world);
        this.isCollidable = true;
        this.spritePath = "/res/sprites/Toaster.png";
    }

    @Override
    public String getName() {
        return "Toaster";
    }

    @Override
    public String getDescription() {
        return "An unplugged toaster";
    }
}
