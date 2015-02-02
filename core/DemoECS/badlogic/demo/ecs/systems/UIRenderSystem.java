package badlogic.demo.ecs.systems;

import badlogic.demo.ecs.R;

import com.artemis.annotations.Wire;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
@Wire
public class UIRenderSystem extends VoidEntitySystem{
	private ViewportSystem viewportSystem;
	private SpriteBatch batch;
	private BitmapFont font;
	public UIRenderSystem() {
		super();
	}
	@Override
	protected void initialize() {
		super.initialize();
		batch = new SpriteBatch();
		font  = new BitmapFont();
	}
	@Override
	protected void begin() {
		super.begin();
		batch.setProjectionMatrix(viewportSystem.getUiViewport().getCamera().combined);
		batch.begin();
	}

	@Override
	protected void processSystem() {
		font.draw(batch,"FPS : "+ Gdx.graphics.getFramesPerSecond(), R.WIDTH_SCREEN - 80, R.HEIGHT_SCREEN - 10 - font.getCapHeight());
	}
	
	@Override
	protected void end() {
		super.end();
		batch.end();
	} 
}
