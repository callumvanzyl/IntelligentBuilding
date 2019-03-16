package entity;

import world.World;

public class Doormat extends DrawableEntity {
    Doormat(World world) {
        super(world);
        this.isCollidable = false;
        this.spritePath = "/res/sprites/Doormat.png";
    }

    @Override
    public String getName() {
        return "Doormat";
    }

    @Override
    public String getDescription() {
        return "A doormat.";
    }
}
