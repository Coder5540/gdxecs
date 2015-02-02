package badlogic.demo.ecs.systems;

import badlogic.demo.ecs.components.Gravity;
import badlogic.demo.ecs.components.Physics;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;

@Wire
public class GravitySystem extends EntityProcessingSystem {

    public static final int GRAVITY_FACTOR = 50;

    ComponentMapper<Physics> pm;
    ComponentMapper<Gravity> gm;

    @SuppressWarnings("unchecked")
	public GravitySystem() {
        super(Aspect.getAspectForAll(Gravity.class, Physics.class));
    }

    @Override
    protected void process(Entity e) {
        final Physics physics = pm.get(e);
        final Gravity gravity = gm.get(e);

        if (gravity.enabled) {
            physics.vy += gravity.y * GRAVITY_FACTOR * world.delta;
            physics.vx += gravity.x * GRAVITY_FACTOR * world.delta;
            System.out.println("Gravity");
        }
    }
}
