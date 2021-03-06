package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	private static boolean rebuildAtlas = false;
	private static boolean drawDebugOutline = false;

	public static void main (String[] arg) {

		if (rebuildAtlas) {
			Settings settings = new Settings();
			settings.maxWidth = 1024*4;
			settings.maxHeight = 1024*4;
			settings.debug = drawDebugOutline;
			// load level_1 characters
			TexturePacker.process(settings,"core/assets/level1","core/assets/images_level",
					"level1");

//			TexturePacker.process(settings,"core/assets/images","core/assets/images-out","canyonBunny");
//			TexturePacker.process(settings,"core/assets/images-ui","core/assets/images-out","canyonBunny-ui");
		}

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MyGdxGame(), config);
		config.width = ActConstants.SCREEN_WIDTH;
		config.height = ActConstants.SCREEN_HEIGHT;
	}
}
