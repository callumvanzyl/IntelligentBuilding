package tile;

import game.Game;

import java.util.HashMap;

public class TileManager {
    private Game game;
    private HashMap<Byte, Object[]> tileTypes = new HashMap<>();

    public TileManager(Game game) {
        this.game = game;

        tileTypes.put((byte) 0, new Object[]{"Grass", "/res/sprites/Grass.png", false});

        tileTypes.put((byte) 1, new Object[]{"Building Wall", "/res/sprites/BuildingWall.png", true});
        tileTypes.put((byte) 2, new Object[]{"Concrete", "/res/sprites/Concrete.png", false});

        tileTypes.put((byte) 3, new Object[]{"Room Wall", "/res/sprites/RoomWall.png", true});
        tileTypes.put((byte) 4, new Object[]{"Wooden Planks", "/res/sprites/WoodenPlanks.png", false});

        tileTypes.put((byte) 126, new Object[]{"Green Planner", "/res/sprites/GreenPlanner.png", false});
        tileTypes.put((byte) 127, new Object[]{"Red Planner", "/res/sprites/RedPlanner.png", false});
    }

    public Tile getTileById(byte id) {
        Object[] tileType = tileTypes.get(id);
        return new Tile(game.getWorld(), (String) tileType[0], (String) tileType[1], (Boolean) tileType[2]);
    }

    public int getTileSize() {
        return 32;
    }
}
