package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level1.Stage1;
import com.mygdx.game.Level2.NormalActors.BossLauncher;
import com.mygdx.game.Level2.Stage2;
import com.mygdx.game.Tools.Assets;

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

	// test2
	Stage stage1;

	World world;

	boolean change;

	int x;


	InputMultiplexer inputMultiplexer;

	public static Stage currentStage;

	Timer timer;
	Timer.Task timerTask;


	@Override
	public void create() {
		x=0;
		change = false;

		//加载全部资源
		Assets.instance.init(new AssetManager());


		//准备分发监听
		inputMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(inputMultiplexer);//可以把监听信息分发给所有的stage
		//监听分发处理器放入公共域
		ActConstants.inputMultiplexer = inputMultiplexer;


		//设置当前舞台为stage1
		currentStage = new Stage2(inputMultiplexer);



	}

	@Override
	public void render() {
		//清屏
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//		x++;
//		System.out.println(x);
//
//		if(x>1000){
//			currentStage.dispose();
//			currentStage = new Stage2(inputMultiplexer);
//			System.out.println("change stage");
//			x=0;
//		}


		//运行当前舞台
		currentStage.act();
		currentStage.draw();

	}

	@Override
	public void dispose() {


	}

}
