package entity;

import world.World;

public class OrangeChair extends DrawableEntity {
    OrangeChair(World world) {
        super(world);
        this.isCollidable = true;
        this.spritePath = "/res/sprites/LeatherChair.png";
    }

    @Override
    public String getName() {
        return "Leather Chair";
    }

    @Override
    public String getDescription() {
        return "A comfortable and stylish chair";
    }
}
