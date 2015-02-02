package badlogic.demo.ecs.components;

import com.artemis.Component;

public class Gravity extends Component {
    public static final float DEFAULT_Y_GRAVITY = -9.8f;
    public float x = 0;
    public float y = DEFAULT_Y_GRAVITY;
    public boolean enabled = true;

    public Gravity() {}

    public Gravity(float y) {
        this.y = y;
    }
}
