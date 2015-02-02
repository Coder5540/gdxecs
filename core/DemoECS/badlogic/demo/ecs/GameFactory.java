package badlogic.demo.ecs;

import badlogic.demo.ecs.components.Anim;
import badlogic.demo.ecs.components.Anim.Layer;
import badlogic.demo.ecs.components.Bounds;
import badlogic.demo.ecs.components.Gravity;
import badlogic.demo.ecs.components.Physics;
import badlogic.demo.ecs.components.Pos;
import badlogic.demo.ecs.components.Renderable;
import badlogic.demo.ecs.components.WallSensor;
import badlogic.demo.ecs.systems.AfterPhysicsSystem;
import badlogic.demo.ecs.systems.AnimRenderSystem;
import badlogic.demo.ecs.systems.AssetSystem;
import badlogic.demo.ecs.systems.AssetSystem.LoadEvent;
import badlogic.demo.ecs.systems.AssetSystem.OnLoadAtlas;
import badlogic.demo.ecs.systems.GravitySystem;
import badlogic.demo.ecs.systems.MapCollisionSystem;
import badlogic.demo.ecs.systems.MapRenderSystem;
import badlogic.demo.ecs.systems.MapSystem;
import badlogic.demo.ecs.systems.PhysicsSystem;
import badlogic.demo.ecs.systems.PlayerControllerSystem;
import badlogic.demo.ecs.systems.TestRenderPlayerSystem;
import badlogic.demo.ecs.systems.UIRenderSystem;
import badlogic.demo.ecs.systems.ViewportSystem;
import badlogic.demo.ecs.systems.WallSensorSystem;
import badlogic.demo.ecs.systems.debug.DebugMapSystem;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class GameFactory {

	private World world;
	private float accumulate = 0;
	private float STEP = 0.015f;
	private final Array<EntitySystem> passiveSystem = new Array<EntitySystem>();

	public World getWorld() {
		if (world == null) {
			createWorld();
			beginLoadResource();
		}
		return world;
	}

	private void createWorld() {
		world = new World();
		passiveSystem.clear();

		{
			// None passive system
			setSystem(world, new ViewportSystem(), false);
			setSystem(world, new AssetSystem(), false);
			setSystem(world, new MapSystem(), false);
			setSystem(world, new GravitySystem(), false);
			setSystem(world, new PhysicsSystem(), false);
			setSystem(world, new MapCollisionSystem(), false);
			setSystem(world, new AfterPhysicsSystem(), false);
			setSystem(world, new WallSensorSystem(), false);
			setSystem(world, new PlayerControllerSystem(), false);
			setSystem(world, new MapRenderSystem(), true);
			setSystem(world, new AnimRenderSystem(), true);
			setSystem(world, new TestRenderPlayerSystem(), true);
		}

		{
			// passive system
			// setSystem(world, new TestSpriteBatchSystem(), true);
			setSystem(world, new UIRenderSystem(), true);
		}

		{
			// temp system to test
			setSystem(world, new DebugMapSystem(), true);
		}

		{
			// manager
			world.setManager(new TagManager());
			world.setManager(new GroupManager());
		}

		world.initialize();
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(new GestureDetector(world
				.getSystem(DebugMapSystem.class)));
		inputMultiplexer.addProcessor(world
				.getSystem(PlayerControllerSystem.class));
		inputMultiplexer.addProcessor(new GestureDetector(world
				.getSystem(ViewportSystem.class)));
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	void setSystem(World world, EntitySystem system, boolean passive) {
		world.setSystem(system, passive);
		if (passive) {
			passiveSystem.add(system);
		}
	}

	public void processWorld(float delta) {
		if (world == null)
			return;
		accumulate += delta;
		while (accumulate > STEP) {
			world.setDelta(STEP);
			world.process();
			accumulate -= STEP;
		}
		for (EntitySystem entitySystem : passiveSystem) {
			world.setDelta(delta);
			entitySystem.process();
		}
	}

	public void beginLoadResource() {
		world.getSystem(AssetSystem.class).load(
				"animation/naruhina/naruhina.pack", onLoadAtlas, true);
	}

	public void finishLoadResource() {
		world.getSystem(AssetSystem.class).createResource();
		Entity entity = world
				.createEntity()
				.edit()
				.add(new Pos(MathUtils.randomTriangular(0, R.WIDTH_SCREEN),
						MathUtils.random(0, R.HEIGHT_SCREEN)))
				.add(new Bounds(60, 60)).add(new Gravity())
				.add(new Anim("player_left", Layer.PLAYER, 0, 0)).getEntity();
		world.getManager(GroupManager.class).add(entity, "player");

		Entity ball = world
				.createEntity()
				.edit()
				.add(new Pos(MathUtils.random(0, R.WIDTH_SCREEN), MathUtils
						.random(0, R.HEIGHT_SCREEN))).add(new Bounds(60, 60)).add(new Gravity())
				.add(new Physics()).add(new Renderable()).add(new WallSensor())
				.getEntity();
		ball.getComponent(Physics.class).bounce =.5f;
		ball.getComponent(Physics.class).friction =6;
		world.getManager(TagManager.class).register("ball", ball);
		world.getSystem(PlayerControllerSystem.class).setEntity(ball);

	}

	OnLoadAtlas onLoadAtlas = new OnLoadAtlas() {
		@Override
		public void broadcastEvent(LoadEvent event) {
			if (event == LoadEvent.LOADED) {
				finishLoadResource();
			}
		}
	};

}
