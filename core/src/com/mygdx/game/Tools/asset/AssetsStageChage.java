package com.mygdx.game.Tools.asset;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.AudioManager;

import java.util.Random;


/**
 * @auther SHI Zhancheng
 * @create 2021-06-13 21:41
 */
public class AssetsStageChage extends Stage implements Disposable {
    private float progressMax = 100;
    private float progressNow = 1;
    private float stateTime;
    private Image backGround;
    private float nextStage;

    private float timeToTurn=10.0f;

    private SpriteBatch batch;

    public AssetsStageChage(AtlasRegion background,float nextStage){
        this.nextStage = nextStage;
        this.setDebugAll(true);
        batch = new SpriteBatch();
        // 初始化ui资源
        AssetsUI.instance.init(new AssetManager());
        backGround = new Image(background);
        backGround.setSize(ActConstants.SCREEN_WIDTH, ActConstants.SCREEN_HEIGHT);
        backGround.setPosition(0, 0);//舞台坐标是左下角

        // 播放背景音乐
        AudioManager.instance.stopMusic();
//        AudioManager.instance.play(AssetsUI.instance.music.mainTheme);

        this.addActor(backGround);
    }

    @Override
    public void act(){
        super.act();
        stateTime += Gdx.graphics.getDeltaTime();
        Random random = new Random();
        float middle = random.nextInt(14);
        progressNow += (stateTime-middle)*(stateTime-middle)/70;
        if (progressNow>= progressMax){
            ActConstants.changeStageTo = nextStage;
        }
    }

    @Override
    public void draw(){
        super.draw();
//        batch.end();
        batch.begin();
        batch.draw(AssetsUI.instance.mainPanel.barBackground, ActConstants.SCREEN_WIDTH/2-960/2-2, ActConstants.SCREEN_HEIGHT/3-2-200,
                960+4, 30+4);
        batch.draw(AssetsUI.instance.mainPanel.barKnot, ActConstants.SCREEN_WIDTH/2-960/2, ActConstants.SCREEN_HEIGHT/3-200,
                960*(progressNow/progressMax), 30);
        AssetsUI.instance.fonts.defaultBigUnFlip.draw(batch,"Loading......",
                ActConstants.SCREEN_WIDTH/2,ActConstants.SCREEN_HEIGHT/3+30-200,
                0,Align.center, true);
        batch.end();
    }
}
