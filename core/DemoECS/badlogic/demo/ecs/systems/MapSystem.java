package badlogic.demo.ecs.systems;


import badlogic.demo.ecs.util.MapMask;

import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;

public class MapSystem extends VoidEntitySystem {
	public TiledMap map;
	public int width;
	public int height;
	public Array<TiledMapTileLayer> layers;
	public boolean isSetup;

	public MapSystem() {
		super();
		initial();
	}

	public void initial() {
		isSetup = true;
		map = new TmxMapLoader().load("map2.tmx");
		layers = new Array<TiledMapTileLayer>();
		for (MapLayer rawLayer : map.getLayers()) {
			layers.add((TiledMapTileLayer) rawLayer);
		}
		width = layers.get(0).getWidth();
		height = layers.get(0).getHeight();

	}

	public MapMask getMask(String property) {
		return new MapMask(height, width, layers, property);
	}

	/**
	 * Spawn map entities.
	 */
	protected void setup() {
		for (TiledMapTileLayer layer : layers) {

			// private HashMap<String, TiledMapTileLayer> layerIndex = new
			// HashMap<String, TiledMapTileLayer>();
			// layerIndex.put(layer.getName(), layer);

			for (int ty = 0; ty < height; ty++) {
				for (int tx = 0; tx < width; tx++) {
					final TiledMapTileLayer.Cell cell = layer.getCell(tx, ty);
					if (cell != null) {
						// final MapProperties properties = cell.getTile()
						// .getProperties();
						// if ( properties.containsKey("entity")) {
						// entitySpawnerSystem.spawnEntity(tx * G.CELL_SIZE, ty
						// * G.CELL_SIZE, properties);
						// layer.setCell(tx, ty, null);
						// }
					}
				}
			}
		}
	}

	@Override
	protected void processSystem() {
		if (!isSetup) {
			isSetup = true;
			setup();
		}
	}

}
