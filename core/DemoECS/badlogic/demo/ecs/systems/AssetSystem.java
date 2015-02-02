package badlogic.demo.ecs.systems;

import java.util.HashMap;
import java.util.Set;

import badlogic.demo.ecs.Utils;
import badlogic.demo.ecs.components.Pos;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

@Wire
public class AssetSystem extends VoidEntitySystem implements Disposable {
	// auto initial
	TagManager tagManager;
	ComponentMapper<Pos> pm;
	AssetManager assetManager;
	// manual initial
	public HashMap<String, Animation> sprites = new HashMap<String, Animation>();
	public HashMap<String, Texture> textures = new HashMap<String, Texture>();
	public HashMap<String, OnLoadAtlas> onLoadAtlasts = new HashMap<String, AssetSystem.OnLoadAtlas>();

	public enum LoadEvent {
		LOADING, LOADED, DISPOSE
	}

	boolean processing = false;

	public interface OnLoadAtlas {
		public void broadcastEvent(LoadEvent event);
	}

	public AssetSystem() {
		assetManager = new AssetManager();
		Texture.setAssetManager(assetManager);
	}

	@Override
	protected void processSystem() {
		Set<String> keySet = onLoadAtlasts.keySet();
		for (String atlastName : keySet) {
			if (assetManager.isLoaded(atlastName, TextureAtlas.class)) {
				OnLoadAtlas onLoadAtlas = onLoadAtlasts.get(atlastName);
				onLoadAtlas.broadcastEvent(LoadEvent.LOADED);
				onLoadAtlasts.remove(atlastName);
				return;
			}
		}
		if (assetManager.update() && onLoadAtlasts.size() > 0) {
			for (String atlastName : keySet) {
				OnLoadAtlas onLoadAtlas = onLoadAtlasts.get(atlastName);
				onLoadAtlas.broadcastEvent(LoadEvent.LOADED);
			}
			onLoadAtlasts.clear();
		}

	}

	@Override
	protected void end() {
		if (onLoadAtlasts.size() == 0)
			processing = false;
	}

	@Override
	protected boolean checkProcessing() {
		return processing;
	}

	public void load(String atlasName, OnLoadAtlas onLoadAtlas,
			boolean isFinishLoading) {
		if (onLoadAtlasts.containsKey(atlasName)) {
			return;
		}
		processing = true;
		assetManager.load(atlasName, TextureAtlas.class);
		if (isFinishLoading)
			assetManager.finishLoading();
		onLoadAtlasts.put(atlasName, onLoadAtlas);
	}

	public void createResource() {
		// fuck that code, you're the ugly one
		TextureAtlas naruhinaAtlas = assetManager.get(
				"animation/naruhina/naruhina.pack", TextureAtlas.class);
		sprites.put("player_up", Utils.creatAnimation(
				naruhinaAtlas.findRegion("up"), .15f,
				Animation.PlayMode.NORMAL, 6, 1));
		sprites.put("player_down",
				Utils.creatAnimation(naruhinaAtlas.findRegion("down"), 6, 1));
		sprites.put("player_right",
				Utils.creatAnimation(naruhinaAtlas.findRegion("right"), 7, 1));
		sprites.put("player_left",
				Utils.creatAnimation(naruhinaAtlas.findRegion("left"), 7, 1));

	}

	public Animation get(final String identifier) {
		return sprites.get(identifier);
	}

	@Override
	public void dispose() {
		sprites.clear();
		textures.clear();
	}

	Texture texture;

	public TextureRegion getRegion(String textureId) {
		if (texture == null) {
			texture = new Texture(Gdx.files.internal("ball.png"));
		}
		return new TextureRegion(texture);
	}

}
