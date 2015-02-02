package badlogic.demo.ecs.systems;

import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

@Wire
public class PlayerControllerSystem extends VoidEntitySystem implements
		InputProcessor {
	Entity e;
	PhysicsSystem physicsSystem;
	public static final int KEY_DEFAULT = -1;
	int keycode = -KEY_DEFAULT;

	public PlayerControllerSystem() {
		super();
	}

	public Entity getEntity() {
		return e;
	}

	public void setEntity(Entity entity) {
		this.e = entity;
	}

	@Override
	protected void processSystem() {
		if (keycode == KEY_DEFAULT)
			return;
		if (keycode == Keys.RIGHT) {
			physicsSystem.push(e, 0, 100f);
			physicsSystem.clampVelocity(e, 0, 100f);
		}
		if (keycode == Keys.LEFT) {
			physicsSystem.push(e, 180, 100f);
			physicsSystem.clampVelocity(e, 0, 100f);
		}
		if (keycode == Keys.UP) {
			physicsSystem.push(e, 90, 100f);
			physicsSystem.clampVelocity(e, 0, 100f);
		}
		if (keycode == Keys.DOWN) {
			physicsSystem.push(e, 270, 100f);
			physicsSystem.clampVelocity(e, 0, 100f);
		}

	}

	@Override
	public boolean keyDown(int keycode) {
		if (e == null)
			return false;
		// physicsSystem.stopPhysic(e);
		// if (keycode == Keys.RIGHT) {
		// physicsSystem.push(e, 0, 100f);
		//
		// } else if (keycode == Keys.LEFT) {
		// physicsSystem.push(e, 180, 100f);
		//
		// } else if (keycode == Keys.UP) {
		// physicsSystem.push(e, 90, 100f);
		// }
		// if (keycode == Keys.DOWN) {
		// physicsSystem.push(e, 270, 100f);
		//
		// }
		this.keycode = keycode;
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		this.keycode = KEY_DEFAULT;
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
