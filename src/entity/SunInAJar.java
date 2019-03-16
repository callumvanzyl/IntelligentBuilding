package entity;

import world.World;

public class SunInAJar extends DrawableEntity implements IThinker {
    SunInAJar(World world) {
        super(world);
        this.isCollidable = true;
        this.spritePath = "/res/sprites/SunInAJar.png";
    }

    @Override
    public void update() {

    }

    @Override
    public String getName() {
        return "Sun In A Jar";
    }

    @Override
    public String getDescription() {
        return "Dramatically increases the temperature of the room it is placed in";
    }
}
