package entity;

import world.World;

public class Light extends DrawableEntity implements IThinker {
    Light(World world) {
        super(world);
        this.isCollidable = true;
        this.spritePath = "/res/sprites/LightOff.png";
    }

    @Override
    public void update() {
        // The light will turn off when there is nobody in the room,
        // it's an intelligent entity!
        if (room != null && room.getNumOccupants() > 0)
            this.spritePath = "/res/sprites/LightOn.png";
        else
            this.spritePath = "/res/sprites/LightOff.png";
    }

    @Override
    public String getName() {
        return "Light";
    }

    @Override
    public String getDescription() {
        return "A power-saving lamp that automatically turns on and off";
    }
}
