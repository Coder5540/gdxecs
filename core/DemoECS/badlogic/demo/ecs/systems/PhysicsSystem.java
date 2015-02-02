package badlogic.demo.ecs.systems;

import badlogic.demo.ecs.components.Physics;
import badlogic.demo.ecs.util.Log;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.math.Vector2;

@Wire
public class PhysicsSystem extends VoidEntitySystem {

	private ComponentMapper<Physics> pm;

	private Vector2 vTmp = new Vector2();

	@Override
	protected void processSystem() {

	}

	public void push(Entity entity, float rotation, float force) {
		if (pm.has(entity)) {
			vTmp.set(force, 0).setAngle(rotation);
			final Physics physics = pm.get(entity);
			physics.vx += vTmp.x;
			physics.vy += vTmp.y;
		}
	}

	public void stopPhysic(Entity entity) {
		if (pm.has(entity)) {
			final Physics physics = pm.get(entity);
			physics.vx = 0;
			physics.vy = 0;
		}
	}

	public void clampVelocity(Entity entity, float minSpeed, float maxSpeed) {
		if (pm.has(entity)) {
			final Physics physics = pm.get(entity);
			vTmp.set(physics.vx, physics.vy).clamp(minSpeed, maxSpeed);
			physics.vx = vTmp.x;
			physics.vy = vTmp.y;
		}
	}

}
