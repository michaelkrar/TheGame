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
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("mario.png");
		player = new Player();
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		player.update();
		playMusic();
		batch.draw(img, player.x, player.y);
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
