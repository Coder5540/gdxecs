package badlogic.demo.ecs.util;

import com.artemis.World;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MapGenerator {
	private TiledMap map;

	public MapGenerator() {
		super();
	}

	private void load(int level) {
		switch (level) {
		case 0:
		default:
			map = new TmxMapLoader().load("map.tmx");
			break;
		}
	}

	public void generate(int level, World world) {
		load(level);
		MapObjects objects = map.getLayers().get("map").getObjects();
		for (MapObject mapObject : objects) {
			if (mapObject instanceof RectangleMapObject) {

				Rectangle bound = ((RectangleMapObject) mapObject)
						.getRectangle();
				Vector2 position = new Vector2();
				bound.getCenter(position);
			

			}
		}
		map.dispose();
	}
}
