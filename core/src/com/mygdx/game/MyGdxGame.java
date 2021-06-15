package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level0.Stage0;
import com.mygdx.game.Level1.Stage1;
import com.mygdx.game.Level2.Stage2;
import com.mygdx.game.Level3.Stage3;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.asset.AssetsLevel1;
import com.mygdx.game.Tools.asset.AssetsLevel2;
import com.mygdx.game.Tools.asset.AssetsStageChage;
import com.mygdx.game.Tools.asset.AssetsUI;

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


	InputMultiplexer inputMultiplexer;

	public static Stage currentStage;


	@Override
	public void create() {

		//加载全部资源
		Assets.instance.init(new AssetManager());
		AssetsLevel1.instance.init(new AssetManager());


		//准备分发监听
		inputMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(inputMultiplexer);//可以把监听信息分发给所有的stage
		//监听分发处理器放入公共域
		ActConstants.inputMultiplexer = inputMultiplexer;


		//设置当前舞台为stage1
		currentStage = new Stage0(inputMultiplexer);




	}

	@Override
	public void render() {
		//清屏
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//运行当前舞台
		currentStage.act();
		currentStage.draw();

		if(ActConstants.changeStageTo!=0){
			System.out.println("qieguanle");
			currentStage.dispose();
			if(ActConstants.changeStageTo==2){
				MyGdxGame.currentStage = new Stage2(ActConstants.inputMultiplexer);
//				AssetsLevel1.instance.dispose();
				ActConstants.changeStageTo = 0;

			}else if(ActConstants.changeStageTo==3){
				MyGdxGame.currentStage = new Stage3(ActConstants.inputMultiplexer);
//				AssetsLevel2.instance.dispose();
				ActConstants.changeStageTo = 0;

			}else if(ActConstants.changeStageTo==1.5){
				//MyGdxGame.currentStage = new AssetsStageChage(AssetsUI.instance.mainPanel.backgroundForest1,2,14);
				ActConstants.changeStageTo = 0;

			}else if(ActConstants.changeStageTo== -1){
				MyGdxGame.currentStage = new Stage0(ActConstants.inputMultiplexer);
				ActConstants.changeStageTo = 0;

			}else if(ActConstants.changeStageTo==2.5){
				//MyGdxGame.currentStage = new AssetsStageChage(AssetsUI.instance.mainPanel.backgroundIce1,3,14);
				ActConstants.changeStageTo = 0;
			}
		}


	}

	@Override
	public void dispose() {


	}

}
