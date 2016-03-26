package com.zodiac;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zodiac.Support.Assets;
import com.zodiac.Support.IO;
import com.zodiac.Support.Settings;
import com.zodiac.screen.MenuScreen;

public class Zodiac extends Game {

	public static SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		Assets.Load();
		IO.Open();
		System.out.println(IO.getStat("Federation_Scout","Size"));
		//Settings.Load();
		//setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
