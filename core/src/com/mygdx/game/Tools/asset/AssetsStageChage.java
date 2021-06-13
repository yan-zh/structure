package com.mygdx.game.Tools.asset;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level1.Stage1;
import com.mygdx.game.Level2.Stage2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.abstraction.Fairy;

import java.util.Random;


/**
 * @auther SHI Zhancheng
 * @create 2021-06-13 21:41
 */
public class AssetsStageChage extends Stage implements Disposable {
    //按钮和背景
//    private ProgressBar bar;
//    private ProgressBarStyle style;
    private float progressMax = 100;
    private float progressNow = 1;
    private float stateTime;
    private Image backGround;
    private Stage nextStage;

    private float timeToTurn=10.0f;

    private SpriteBatch batch;

    public AssetsStageChage(AtlasRegion background,Stage nextStage){
        this.nextStage = nextStage;
        this.setDebugAll(true);
        batch = new SpriteBatch();
        // 初始化ui资源
        AssetsUI.instance.init(new AssetManager());
        backGround = new Image(background);
        backGround.setSize(ActConstants.SCREEN_WIDTH, ActConstants.SCREEN_HEIGHT);
        backGround.setPosition(0, 0);//舞台坐标是左下角

        // 播放背景音乐
        AudioManager.instance.play(AssetsUI.instance.music.bmg01);

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
            MyGdxGame.currentStage = nextStage;
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
