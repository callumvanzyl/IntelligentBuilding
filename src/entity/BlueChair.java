package entity;

import world.World;

public class BlueChair extends DrawableEntity {
    BlueChair(World world) {
        super(world);
        this.isCollidable = true;
        this.spritePath = "/res/sprites/BlueChair.png";
    }

    @Override
    public String getName() {
        return "Denim Chair";
    }

    @Override
    public String getDescription() {
        return "A comfortable chair";
    }
}
