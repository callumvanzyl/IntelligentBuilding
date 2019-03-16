package entity;

import world.World;

public class RoundTable extends DrawableEntity {
    RoundTable(World world) {
        super(world);
        this.isCollidable = true;
        this.spritePath = "/res/sprites/RoundTable.png";
    }

    @Override
    public String getName() {
        return "Round Table";
    }

    @Override
    public String getDescription() {
        return "A surprisingly stable wooden table.";
    }
}
