package badlogic.demo.ecs.components;

import com.artemis.Component;

/**
 * @author Daan van Yperen
 */
public class WallSensor extends Component {
    public boolean onFloor = false;
    public boolean onHorizontalSurface = false;
    public boolean onVerticalSurface = false;

    public float wallAngle;

    public boolean onAnySurface() {
        return onHorizontalSurface || onVerticalSurface;
    }
}
