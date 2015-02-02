package badlogic.demo.ecs.systems;

import badlogic.demo.ecs.util.Log;
import badlogic.demo.ecs.util.TiledMapRender;

import com.artemis.annotations.Wire;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

@Wire
public class MapRenderSystem extends VoidEntitySystem {
	ViewportSystem viewportSystem;
	MapSystem mapSystem;
	private SpriteBatch batch;
	private TiledMapRender tiledMapRender;
	public MapRenderSystem() {
		super();
	}

	@Override
	protected void initialize() {
		super.initialize();
		batch = new SpriteBatch();
		tiledMapRender = new TiledMapRender(mapSystem.map);
	}

	@Override
	protected void begin() {
		super.begin();
		batch.setProjectionMatrix(viewportSystem.getGameCamera().combined);
		batch.begin();
	}

	@Override
	protected void processSystem() {
		for(TiledMapTileLayer layer : mapSystem.layers){
			renderLayer(layer);
		}
	}

	public void renderLayer(final TiledMapTileLayer layer){
		tiledMapRender.setView(viewportSystem.getGameCamera());
		tiledMapRender.renderTileLayer(layer);
	}

	@Override
	protected void end() {
		super.end();
		batch.end();
	}

}
