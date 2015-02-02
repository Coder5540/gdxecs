package badlogic.demo.ecs.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bounds extends Component {
	public float x1;
	public float y1;
	public float x2;
	public float y2;

	public Bounds(float width, float height) {
		this.x1 = this.y1 = 0;
		this.x2 = width;
		this.y2 = height;
	}

	public Bounds(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public Bounds(TextureRegion region) {
		this.x1 = this.x2 = 0;
		this.x2 = region.getRegionWidth();
		this.y2 = region.getRegionHeight();
	}

	public float cx() {
		return x1 + (x2 - x1) / 2;
	}

	public float cy() {
		return y1 + (y2 - y1) / 2;
	}

	public float getWidth() {
		return x2 - x1;
	}

	public float getHeight() {
		return Math.abs(y2 - y1);
	}

}
