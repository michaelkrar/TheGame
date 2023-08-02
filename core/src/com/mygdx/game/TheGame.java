package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class TheGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Player player;
	KeyHandler keyH;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("mario.png");
		player = new Player(keyH);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		player.update();
		batch.draw(img, player.x, player.y);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
