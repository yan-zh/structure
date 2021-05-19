package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
/* *这*************************************************这个是监听换舞台的版本
public class MyGdxGame extends ApplicationAdapter {

	Stage currentStage;
	Stage stage0;
	Stage stage1;
	Stage progressStage;

	InputMultiplexer inputMultiplexer;


	
	@Override
	public void create () {

		currentStage = new Stage0(this);

		inputMultiplexer= new InputMultiplexer();
		Gdx.input.setInputProcessor(inputMultiplexer);//可以把监听信息分发给所有的stage
		inputMultiplexer.addProcessor(currentStage);



	}

	@Override
	public void render () {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		currentStage.act();
		currentStage.draw();

	}
	
	@Override
	public void dispose () {


	}

	public void changeStage(char stageNumber){

		switch(stageNumber){
			case PublicData.Stage0:
				break;
			case PublicData.Stage1:
				if(stage1==null){
					currentStage = new Stage1(this);//换舞台
					inputMultiplexer.addProcessor(currentStage);//加入监听
				}else{
					currentStage = stage1;
				}
				break;

		}



	}
}
*/
//**************************t跳的低是因为有向下的速度，直接给动量就抵充了一部分速度，应该向下速度清零再给动量
public class MyGdxGame extends ApplicationAdapter {


	Stage stage0;
	Stage stage1;

	World world;


	InputMultiplexer inputMultiplexer;


	@Override
	public void create() {

		inputMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(inputMultiplexer);//可以把监听信息分发给所有的stage

		PublicData.currentStage = PublicData.Stage0;

		world = new World(new Vector2(0,-10),true);
		world.setContactListener(new MyContactListener());

		PhysicalEntityDefine.boundWorld(world);

		stage0 = new Stage0(inputMultiplexer);
		stage1 = new Stage1(inputMultiplexer,world);





	}

	@Override
	public void render() {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		switch (PublicData.currentStage) {
			case PublicData.Stage0:
				stage0.act();
				stage0.draw();
				break;
			case PublicData.Stage1:
				stage1.act();
				stage1.draw();
				break;
		}

	}

	@Override
	public void dispose() {


	}

}
