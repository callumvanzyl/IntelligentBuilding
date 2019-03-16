package entity;

import world.World;

public class Airplane extends DrawableEntity {
    Airplane(World world) {
        super(world);
        this.isCollidable = true;
        this.spritePath = "/res/sprites/ModelAirplane.png";
    }

    @Override
    public String getName() {
        return "Model Airplane";
    }

    @Override
    public String getDescription() {
        return "A true-to-life replica of an airplane (minus the flying)";
    }
}
