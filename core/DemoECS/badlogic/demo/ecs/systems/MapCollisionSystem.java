package badlogic.demo.ecs.systems;

import badlogic.demo.ecs.components.Bounds;
import badlogic.demo.ecs.components.Physics;
import badlogic.demo.ecs.components.Pos;
import badlogic.demo.ecs.util.MapMask;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;


@Wire
public class MapCollisionSystem extends EntityProcessingSystem {

    private static boolean DEBUG = false;

    private MapSystem mapSystem;
    private AssetSystem assetSystem;

    private boolean initialized;
    private MapMask solidMask;

    private ComponentMapper<Physics> ym;
    private ComponentMapper<Pos> pm;
    private ComponentMapper<Bounds> bm;

    public MapCollisionSystem() {
        super(Aspect.getAspectForAll(Physics.class, Pos.class, Bounds.class));
    }

    @Override
    protected void begin() {
        if (!initialized) {
            initialized = true;
            solidMask = mapSystem.getMask("wall");
        }
    }

    @Override
    protected void end() {
    }

    @Override
    protected void process(Entity e) {
        final Physics physics = ym.get(e);
        final Pos pos = pm.get(e);
        final Bounds bounds = bm.get(e);

        //  no math required here.
        if (physics.vx != 0 || physics.vy != 0) {

            float px = pos.x + physics.vx * world.delta;
            float py = pos.y + physics.vy * world.delta;

            if ((physics.vx > 0 && collides(px + bounds.x2, py + bounds.y1 + (bounds.y2 - bounds.y1) * 0.5f)) ||
                    (physics.vx < 0 && collides(px + bounds.x1, py + bounds.y1 + (bounds.y2 - bounds.y1) * 0.5f))) {
                physics.vx = physics.bounce > 0 ? -physics.vx * physics.bounce : 0;
                px = pos.x;
            }

            if ((physics.vy > 0 && collides(px + bounds.x1 + (bounds.x2 - bounds.x1) * 0.5f, py + bounds.y2)) ||
                    (physics.vy < 0 && collides(px + bounds.x1 + (bounds.x2 - bounds.x1) * 0.5f, py + bounds.y1))) {
                physics.vy = physics.bounce > 0 ? -physics.vy * physics.bounce : 0;
            }
        }

    }

    private boolean collides(final float x, final float y) {
        return solidMask.atScreen(x, y);
    }
}