package com.zodiac;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zodiac.Support.Assets;
import com.zodiac.screen.MenuScreen;

public class SpaceAssault extends Game {

	public static SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		Assets.Load();
		//Settings.Load();
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
