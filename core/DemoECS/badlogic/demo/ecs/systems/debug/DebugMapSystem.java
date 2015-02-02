package badlogic.demo.ecs.systems.debug;

import badlogic.demo.ecs.components.Bounds;
import badlogic.demo.ecs.components.Pos;
import badlogic.demo.ecs.systems.MapSystem;
import badlogic.demo.ecs.systems.ViewportSystem;
import badlogic.demo.ecs.util.Log;
import badlogic.demo.ecs.util.MapMask;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

@Wire
public class DebugMapSystem extends VoidEntitySystem implements GestureListener {
	ViewportSystem viewportSystem;
	ComponentMapper<Pos> pm;
	ComponentMapper<Bounds> bm;

	ShapeRenderer shapeRender;
	MapSystem mapSystem;
	MapMask wallMask;

	private Viewport viewport;
	private Vector2 touchpoint;
	float radius;
	private float MAX_RADIUS = 20;

	public DebugMapSystem() {
		super();
	}

	@Override
	protected void initialize() {
		super.initialize();
		shapeRender = new ShapeRenderer();
		viewport = viewportSystem.getGameViewport();
		touchpoint = new Vector2();
		wallMask = mapSystem.getMask("wall");
		radius = MAX_RADIUS;
	}

	@Override
	protected void begin() {
		super.begin();
		shapeRender.setProjectionMatrix(viewport.getCamera().combined);
		shapeRender.begin(ShapeType.Line);
	}

	@Override
	protected void processSystem() {

		Entity entity = world.getManager(TagManager.class).getEntity("ball");
		Pos pos = pm.get(entity);
		Bounds bounds = bm.get(entity);
		shapeRender.rect(pos.x,
				pos.y , bounds.getWidth(),
				bounds.getHeight());
		if (radius > 0) {
			Gdx.gl.glLineWidth(2);
			shapeRender.setColor(Color.RED);
			shapeRender.circle(touchpoint.x, touchpoint.y, radius);
			radius -= world.delta * 40;
		} else {
			radius = 0;
		}
	}

	@Override
	protected void end() {
		super.end();
		shapeRender.end();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		viewport.unproject(touchpoint.set(x, y));
		System.out.println("is Ingrid: "
				+ wallMask.atScreen(touchpoint.x, touchpoint.y));
		radius = MAX_RADIUS;
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

}
