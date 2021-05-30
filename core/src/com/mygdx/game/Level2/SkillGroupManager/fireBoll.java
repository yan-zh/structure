package com.mygdx.game.Level2.SkillGroupManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class fireBoll extends Actor {
	
	private Animation animation;
	
	private float xPosition;
	private float yPosition;
	private float[] direction;
	
	private float stateTime;//ʱ�䣬���ڿ��ƶ����ٶȵ�
	
	private int lifeTime;
	private int exist;

	private Array<ParticleEmitter> emitter;
	private ParticleEffect effect;

	
	public fireBoll(float xo, float yo, float[] direction){
		Texture boll1 =  new Texture(Gdx.files.internal("core/assets/fireboll1.jpg"));
		Texture boll2 =  new Texture(Gdx.files.internal("core/assets/fireboll2.jpg"));

		TextureRegion region[] = new TextureRegion[2];
		region[0]=new TextureRegion(boll1);
		region[1]=new TextureRegion(boll2);
//12123
		animation = new Animation(0.1f,region);

		lifeTime=200;

		xPosition=xo;
		yPosition=yo;
		this.direction=direction;
		this.exist=1;

		effect = new ParticleEffect();
		effect.load(Gdx.files.internal("core/assets/particle3.p"),Gdx.files.internal("core/assets/"));
		
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		if(exist==1) {
			stateTime += Gdx.graphics.getDeltaTime();

			this.update();

			batch.draw((TextureRegion) animation.getKeyFrame(stateTime,true), xPosition, yPosition);

			effect.draw(batch,Gdx.graphics.getDeltaTime());

		}

	}



	private void update() {
		
		xPosition=xPosition+direction[0]*5;
		yPosition=yPosition+direction[1]*5;
		lifeTime--;
		
		if(lifeTime==0) {
			this.exist=0;
			//effect.dispose();好像不需要主动消除粒子，一旦演员结束后其内部所有的都清除
			this.remove();
		}

		effect.setPosition(xPosition,yPosition);

		
	}
	
}
