package badlogic.demo.ecs.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledMapRender extends OrthogonalTiledMapRenderer {

	public TiledMapRender(TiledMap map, Batch batch) {
		super(map, batch);
	}

	public TiledMapRender(TiledMap map, float unitScale, Batch batch) {
		super(map, unitScale, batch);
	}

	public TiledMapRender(TiledMap map, float unitScale) {
		super(map, unitScale);
	}

	public TiledMapRender(TiledMap map) {
		super(map);
	}

	@Override
	public void renderTileLayer(TiledMapTileLayer layer) {
		getBatch().setColor(1, 1, 1, 1);
		beginRender();
		super.renderTileLayer(layer);
		endRender();
	}

}
