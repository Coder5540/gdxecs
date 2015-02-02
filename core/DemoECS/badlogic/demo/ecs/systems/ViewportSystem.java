package badlogic.demo.ecs.systems;

import badlogic.demo.ecs.R;

import com.artemis.annotations.Wire;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

@Wire
public class ViewportSystem extends VoidEntitySystem implements GestureListener {
	private ShapeRenderer shapeRenderer;
	private OrthographicCamera gameCamera;
	private OrthographicCamera uiCamera;
	private Viewport gameViewport;
	private Viewport uiViewport;

	public ViewportSystem() {
		super();

		gameCamera = new OrthographicCamera(R.WIDTH_SCREEN, R.HEIGHT_SCREEN);
		uiCamera = new OrthographicCamera(R.WIDTH_SCREEN, R.HEIGHT_SCREEN);

		gameViewport = new StretchViewport(R.WIDTH_SCREEN, R.HEIGHT_SCREEN,
				gameCamera);
		uiViewport = new StretchViewport(R.WIDTH_SCREEN, R.HEIGHT_SCREEN,
				uiCamera);
		shapeRenderer = new ShapeRenderer();
	}

	public void onGameResize(int width, int height) {
		gameViewport.update(width, height, true);
		uiViewport.update(width, height, true);
	}

	@Override
	protected void processSystem() {
		updateFling(world.delta);
	}
	@Override
	protected void end() {
		super.end();
		gameCamera.update();
		uiCamera.update();
	}
	
	float flingTimer = 0f;
	float flingTime = .6f;
	float velocityX = 0f;
	float velocityY = 0f;
	float amountX = 0f;
	float amountY = 0f;
	private boolean canfling = false;

	private void updateFling(float delta) {
		if (flingTimer > 0) {
			float alpha = flingTimer / flingTime;
			amountX = velocityX * alpha * delta;
			amountY = velocityY * alpha * delta;
			flingTimer -= delta;
			if (flingTimer <= 0) {
				velocityX = 0;
				velocityY = 0;
			} else {
				gameCamera.position.x -= amountX * gameCamera.zoom;
				gameCamera.position.y += amountY * gameCamera.zoom;
				gameCamera.update();
			}
		}
	}

	public Viewport getGameViewport() {
		return gameViewport;
	}

	public void setGameViewport(Viewport gameViewport) {
		this.gameViewport = gameViewport;
	}

	public Viewport getUiViewport() {
		return uiViewport;
	}

	public void setUiViewport(Viewport uiViewport) {
		this.uiViewport = uiViewport;
	}

	public ShapeRenderer getShapeRenderer() {
		return shapeRenderer;
	}

	public void setShapeRenderer(ShapeRenderer shapeRenderer) {
		this.shapeRenderer = shapeRenderer;
	}

	public OrthographicCamera getGameCamera() {
		return gameCamera;
	}

	public void setGameCamera(OrthographicCamera gameCamera) {
		this.gameCamera = gameCamera;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		canfling = true;
		flingTimer = 0f;
		velocityX = 0;
		velocityY = 0;
		amountX = 0;
		amountY = 0;

		initialScale = gameCamera.zoom;
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {

		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		gameCamera.position.add(-deltaX * gameCamera.zoom, deltaY
				* gameCamera.zoom, 0);
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		if (canfling)
			flingTimer = flingTime;
		else {
			velocityX = 0;
			velocityY = 0;
			amountX = 0;
			amountY = 0;
			flingTimer = 0;
		}
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {

		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		if (canfling) {
			this.velocityX = velocityX;
			this.velocityY = velocityY;
			this.flingTimer = flingTime;
		}
		return false;
	}

	float initialScale = 1;

	@Override
	public boolean zoom(float initialDistance, float distance) {
		float ratio = initialDistance / distance;
		gameCamera.zoom = initialScale * ratio;
		canfling = false;
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

}
