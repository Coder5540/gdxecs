package badlogic.demo.ecs.components;

import com.artemis.Component;

public class Pos extends Component {
	public float x;
	public float y;

	public Pos() {
		super();
	}

	public Pos(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}
