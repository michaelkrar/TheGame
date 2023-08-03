package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class TheGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Player player;
	Audio audio;
	Texture grass;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("walmartio.png");
		grass = new Texture("grassblock.png");
		player = new Player();
		Gdx.graphics.setWindowedMode(256,256);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 1, 1);
		batch.begin();
		player.update();
		playMusic();
		for (int i = 0; i<40; i++) {
			batch.draw(grass,16*i,16);
		}
		batch.draw(img, (float)player.linK.position().x(), (float)player.linK.position().y());
		batch.end();
	}

	public void playMusic () {
		Music menuMusic = Gdx.audio.newMusic(Gdx.files.internal("song.ogg"));
menuMusic.setLooping(true);
menuMusic.play();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
