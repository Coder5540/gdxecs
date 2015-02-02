package badlogic.demo.ecs.systems;

import badlogic.demo.ecs.components.Bounds;
import badlogic.demo.ecs.components.Pos;
import badlogic.demo.ecs.components.Renderable;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

@Wire
public class TestRenderPlayerSystem extends EntityProcessingSystem {
	AssetSystem assetSystem;
	ViewportSystem viewportSystem;
	private SpriteBatch batch;
	private ComponentMapper<Renderable> rm;
	private ComponentMapper<Pos> pm;
	private ComponentMapper<Bounds> bm;

	public TestRenderPlayerSystem() {
		super(Aspect.getAspectForOne(Renderable.class));
	}

	@Override
	protected void initialize() {
		super.initialize();
		batch = new SpriteBatch();
	}

	@Override
	protected void begin() {
		super.begin();
		batch.setProjectionMatrix(viewportSystem.getGameCamera().combined);
		batch.begin();
	}

	@Override
	protected void process(Entity e) {
		TextureRegion region = assetSystem.getRegion(rm.get(e).getTextureId());
		batch.draw(region, pm.get(e).getX(), pm.get(e).getY(), bm.get(e)
				.getWidth(), bm.get(e).getHeight());
	}

	@Override
	protected void end() {
		super.end();
		batch.end();
	}
}
