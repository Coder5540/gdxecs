package badlogic.demo.ecs.util;

import badlogic.demo.ecs.components.Pos;

import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Daan van Yperen
 */
public class EntityUtil{

    private static Vector2 tmp = new Vector2();

    public static float distance2( final Entity a, final Entity b)
    {
        final Pos pa = (Pos)a.getComponent(Pos.class);
        final Pos pb = (Pos)b.getComponent(Pos.class);

        return tmp.set(pa.x, pa.y).dst2(pb.x, pb.y);
    }

    public static float angle( final Entity a, final Entity b)
    {
        final Pos pa = (Pos)a.getComponent(Pos.class);
        final Pos pb = (Pos)b.getComponent(Pos.class);

        return tmp.set(pb.x, pb.y).sub(pa.x, pa.y).angle();

    }

    public static float distance( final Entity a, final Entity b)
    {
        final Pos pa = (Pos)a.getComponent(Pos.class);
        final Pos pb = (Pos)b.getComponent(Pos.class);

        return tmp.set(pa.x, pa.y).dst(pb.x, pb.y);
    }
}