package badlogic.demo.ecs.systems;

import badlogic.demo.ecs.components.Bounds;
import badlogic.demo.ecs.components.Physics;
import badlogic.demo.ecs.components.Pos;
import badlogic.demo.ecs.components.WallSensor;
import badlogic.demo.ecs.util.Log;
import badlogic.demo.ecs.util.MapMask;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;

/**
 * Constrain movement to map collision.
 * <p/>
 * Inteded to clamp physics calculations.
 *
 * @author Daan van Yperen
 */
@Wire
public class WallSensorSystem extends EntityProcessingSystem {

	private MapSystem mapSystem;

	private boolean initialized;
	private MapMask solidMask;

	private ComponentMapper<Physics> ym;
	private ComponentMapper<Pos> pm;
	private ComponentMapper<Bounds> bm;
	private ComponentMapper<WallSensor> ws;

	public WallSensorSystem() {
		super(Aspect.getAspectForAll(WallSensor.class, Pos.class, Bounds.class));
	}

	@Override
	protected void begin() {
		if (!initialized) {
			initialized = true;
			solidMask = mapSystem.getMask("solid");
		}
	}

	@Override
	protected void end() {
	}

	@Override
	protected void process(Entity e) {

		final Pos pos = pm.get(e);
		final Bounds bounds = bm.get(e);

		float px = pos.x;
		float py = pos.y;

		final WallSensor wallSensor = ws.get(e);

		final boolean onFloor = collides(px + bounds.x1
				+ (bounds.x2 - bounds.x1) * 0.5f, py + bounds.y1 - 1);
		final boolean onCeiling = collides(px + bounds.x1
				+ (bounds.x2 - bounds.x1) * 0.5f, py + bounds.y2 + 1);
		final boolean onEastWall = collides(px + bounds.x2 + 1, py + bounds.y1
				+ (bounds.y2 - bounds.y1) * 0.5f);
		final boolean onWestWall = collides(px + bounds.x1 - 1, py + bounds.y1
				+ (bounds.y2 - bounds.y1) * 0.5f);

		wallSensor.onVerticalSurface = onEastWall || onWestWall;
		wallSensor.onFloor = onFloor;
		wallSensor.onHorizontalSurface = onCeiling || wallSensor.onFloor;

		wallSensor.wallAngle = onFloor ? 90 : onCeiling ? -90 : onEastWall ? 0
				: onWestWall ? 180 : 90;

		if (wallSensor.onAnySurface()) {
			throw new NullPointerException("Adsads");
		}

	}

	private boolean collides(final float x, final float y) {
		return solidMask.atScreen(x, y);
	}
}
