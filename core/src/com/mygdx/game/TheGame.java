package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.physics.Translation2d;

public class TheGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Player mPlayer;
	Audio audio;
	Texture grass;
	Boss bowser;
	ShapeRenderer barDrawer;
	// BitmapFont font = new BitmapFont();
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("walmartio.png");
		grass = new Texture("grassblock.png");
		Gdx.graphics.setWindowedMode(256,256);
		mPlayer = Player.getInstance();
		bowser = new Boss(new Translation2d(200,30));
		barDrawer = new ShapeRenderer();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 1, 1);
		barDrawer.begin(ShapeType.Filled); //I'm using the Filled ShapeType, but remember you have three of them 
		barDrawer.setColor(Color.RED);
	            barDrawer.rect((float)bowser.mLK.position().x(),(float)bowser.mLK.position().y()+32,(float).3*bowser.hp,(float)3); //assuming you have created those x, y, width and height variables 
			    barDrawer.rect((float)mPlayer.linK.position().x(),(float)mPlayer.linK.position().y()+32,(float).3*mPlayer.hp,(float)3); //assuming you have created those x, y, width and height variables 
	            barDrawer.end(); 
		batch.begin();
		mPlayer.update();
		// playMusic();
		for (int i = 0; i<40; i++) {
			batch.draw(grass,16*i,16);
		}
		// batch.draw(img, (float)player.linK.position().x(), (float)player.linK.position().y());
		mPlayer.render(batch);
		bowser.render(batch);
		damageChecker(mPlayer,bowser);
		// System.out.println(bowser.hp);
		// font.draw(batch,bowser.hp + "/" + bowser.hpMax, 10,10);
		batch.end();
	}

	public void playMusic () {
		Music menuMusic = Gdx.audio.newMusic(Gdx.files.internal("song.ogg"));
		menuMusic.setLooping(true);
		menuMusic.play();
	}
	
	public void damageChecker (Player player, Boss boss) {
		for (Bullet b : player.bullets) {
			if (boss.mLK.position().subtract(b.linK.position()).hypot()<20){
				boss.damage(10);
			}
		}
		for (Bullet b : boss.bullets) {
			if (player.linK.position().subtract(b.linK.position()).hypot()<20){
				player.damage(10);
			}
		}
		if(boss.mLK.position().subtract(player.linK.position()).hypot()<20){
			player.damage(10);
		}
	}
	@Override
	public void dispose () {
		batch.dispose();
		mPlayer.dispose();
		img.dispose();
		barDrawer.dispose();
	}
}
