package com.zodiac.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zodiac.SpaceAssault;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.fullscreen = false;
		config.useGL30 = true;
		config.height = 1080;
		config.width = 1980;

		//Change icon in future release
		//config.addIcon("badlogic.jpg",Files.FileType.Internal);
		new LwjglApplication(new SpaceAssault(), config);
	}
}
