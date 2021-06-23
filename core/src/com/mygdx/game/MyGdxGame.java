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
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.asset.AssetsLevel1;
import com.mygdx.game.Tools.asset.AssetsLevel2;
import com.mygdx.game.Tools.asset.AssetsStageChage;
import com.mygdx.game.Tools.asset.AssetsUI;

/* ************************************************
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



	}
}
*/
//**************************t
public class MyGdxGame extends ApplicationAdapter {

	// test2
	Stage stage1;

	World world;


	InputMultiplexer inputMultiplexer;

	public static Stage currentStage;


	@Override
	public void create() {


		Assets.instance.init(new AssetManager());
		AssetsUI.instance.init(new AssetManager());
//		AssetsLevel1.instance.init(new AssetManager());


		inputMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(inputMultiplexer);//可以把监听信息分发给所有的stage
		ActConstants.inputMultiplexer = inputMultiplexer;


		//
		currentStage = new AssetsStageChage(AssetsUI.instance.mainPanel.backgroundForest3,1);





	}

	@Override
	public void render() {
		//
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//
		currentStage.act();
		currentStage.draw();

		if(ActConstants.changeStageTo!=0){
			System.out.println("qieguanle");
			currentStage.dispose();

			if(ActConstants.changeStageTo==1){
				currentStage = new Stage0(ActConstants.inputMultiplexer);
				ActConstants.changeStageTo = 0;
			}else if(ActConstants.changeStageTo==2){
				ActConstants.spriteNumber = 1;
				AudioManager.instance.play(AssetsUI.instance.music.mainTheme);
				MyGdxGame.currentStage = new Stage2(ActConstants.inputMultiplexer);
//				AssetsLevel0.instance.dispose();
				ActConstants.changeStageTo = 0;

			}else if(ActConstants.changeStageTo==3){
				ActConstants.spriteNumber = 2;
				MyGdxGame.currentStage = new Stage3(ActConstants.inputMultiplexer);
				ActConstants.changeStageTo = 0;

			}else if(ActConstants.changeStageTo==4){
				MyGdxGame.currentStage = new Stage1(ActConstants.inputMultiplexer);
				ActConstants.changeStageTo = 0;

			}else if(ActConstants.changeStageTo==1.5){
				MyGdxGame.currentStage = new AssetsStageChage(AssetsUI.instance.mainPanel.backgroundForest1,2);
				ActConstants.changeStageTo = 0;

			}else if(ActConstants.changeStageTo==2.5){
				MyGdxGame.currentStage = new AssetsStageChage(AssetsUI.instance.mainPanel.backgroundIce1,3);
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
