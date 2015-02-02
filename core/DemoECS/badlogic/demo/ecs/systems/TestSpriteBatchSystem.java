package badlogic.demo.ecs.systems;

import badlogic.demo.ecs.components.Pos;
import badlogic.demo.ecs.util.Log;

import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

@Wire
public class TestSpriteBatchSystem extends VoidEntitySystem {
	ViewportSystem viewportSystem;
	SpriteBatch batch;
	private Texture texture;

	public TestSpriteBatchSystem() {
		super();
	}

	@Override
	protected void initialize() {
		super.initialize();
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("badlogic.jpg"));
	}

	@Override
	protected void begin() {
		super.begin();
		batch.setProjectionMatrix(viewportSystem.getGameCamera().combined);
		batch.begin();
	}

	@Override
	protected void processSystem() {
		Entity player = world.getManager(TagManager.class).getEntity("player");
		if (player == null) {
			Log.d("LOL, Player null ");
			return;
		}
		Pos pos = player.getComponent(Pos.class);
		batch.draw(texture, pos.x, pos.y);
	}

	@Override
	protected void end() {
		super.end();
		batch.end();
	}

}
