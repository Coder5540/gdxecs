package badlogic.demo.ecs;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public class Utils {
	public static TextureRegion[] getArrayTextureRegion(
			TextureRegion textureRegion, int FRAME_COLS, int FRAME_ROWS) {
		float width = textureRegion.getRegionWidth() / FRAME_COLS;
		float height = textureRegion.getRegionHeight() / FRAME_ROWS;

		TextureRegion[] textureRegions = new TextureRegion[FRAME_COLS
				* FRAME_ROWS];
		TextureRegion[][] temp = textureRegion.split((int) width, (int) height);
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				textureRegions[index++] = temp[i][j];
			}
		}

		return textureRegions;
	}

	public static TextureRegion[] getArrayTextureRegion(
			TextureRegion textureRegion, int FRAME_COLS, int FRAME_RAWS,
			int startFrame, int endFrame) {

		TextureRegion[][] arrayAnimations = (textureRegion.split(
				textureRegion.getRegionWidth() / FRAME_COLS,
				textureRegion.getRegionHeight() / FRAME_RAWS));
		TextureRegion[] arrayAnimation_temp = new TextureRegion[FRAME_RAWS
				* FRAME_COLS];
		int index = 0;
		for (int i = 0; i < FRAME_RAWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				arrayAnimation_temp[index++] = arrayAnimations[i][j];
			}
		}
		if ((startFrame > endFrame) || (startFrame < 0)
				|| (endFrame > arrayAnimation_temp.length)
				|| (startFrame > arrayAnimation_temp.length)) {
			System.out.println("Loi Khoi Tao Sai Sprite. AssetGame.java");
		}

		TextureRegion[] arrayAnimation = new TextureRegion[endFrame
				- startFrame + 1];
		index = 0;
		for (int i = startFrame; i < endFrame + 1; i++) {
			arrayAnimation[index++] = arrayAnimation_temp[i];
		}
		return arrayAnimation;
	}

	public static Animation creatAnimation(TextureRegion textureRegion,
			int FRAME_COLS, int FRAME_RAWS) {
		return new Animation(1.0f / 10.0f, getArrayTextureRegion(textureRegion,
				FRAME_COLS, FRAME_RAWS));
	}

	public static Animation creatAnimation(TextureRegion textureRegion,
			int FRAME_COLS, int FRAME_RAWS, int startFrame, int endFrame) {
		return new Animation(1.0f / 10.0f, getArrayTextureRegion(textureRegion,
				FRAME_COLS, FRAME_RAWS, startFrame, endFrame));
	}

	public static Animation creatAnimation(TextureRegion textureRegion,
			float frameDuration, int FRAME_COLS, int FRAME_RAWS) {

		return new Animation(frameDuration, getArrayTextureRegion(
				textureRegion, FRAME_COLS, FRAME_RAWS));

	}

	public static Animation creatAnimation(TextureRegion textureRegion,
			float frameDuration, int FRAME_COLS, int FRAME_RAWS,
			int startFrame, int endFrame) {
		return new Animation(frameDuration, getArrayTextureRegion(
				textureRegion, FRAME_COLS, FRAME_RAWS, startFrame, endFrame));
	}

	public static Animation creatAnimation(TextureRegion textureRegion,
			float frameDuration,PlayMode playMode, int FRAME_COLS, int FRAME_RAWS) {
		Animation anim =new Animation(frameDuration, getArrayTextureRegion(
				textureRegion, textureRegion.getRegionWidth() / FRAME_COLS,
				textureRegion.getRegionHeight() / FRAME_RAWS));
		anim.setPlayMode(playMode);
		return anim;

	}

}
