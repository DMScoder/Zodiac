package com.zodiac.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zodiac.Zodiac;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.fullscreen = true;
		config.useGL30 = true;
		config.height = 1080;
		config.width = 1920;

		//Change icon in future release
		//config.addIcon("badlogic.jpg",Files.FileType.Internal);
		new LwjglApplication(new Zodiac(), config);
	}
}
