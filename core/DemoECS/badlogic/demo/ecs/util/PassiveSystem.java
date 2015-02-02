package badlogic.demo.ecs.util;

import com.artemis.systems.VoidEntitySystem;

/**
 * @author Daan van Yperen
 */
public class PassiveSystem extends VoidEntitySystem {

    public PassiveSystem() {
        setPassive(true);
    }

    @Override
    protected void processSystem() {
    }
}
