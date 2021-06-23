package com.mygdx.game.Level0;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level1.Stage1;
import com.mygdx.game.Level2.Stage2;
import com.mygdx.game.Level3.Stage3;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.asset.*;


public class Stage0 extends Stage {//有一些按钮和背景，是类似开始界面

    private ImageButton startButton;
    private ImageButton settingButton;
    private ImageButton exitButton;
    private Vector2 position01;
    private Vector2 position02;
    private Vector2 position03;

    private Image backGround;


    public Stage0(InputMultiplexer inputMultiplexer){
//        this.setDebugAll(true);

        AssetsLevel0.instance.init(new AssetManager());
//        AssetsLevel0.instance.init(new AssetManager());
        inputMultiplexer.addProcessor(this);

        TextureRegion temp2 = AssetsUI.instance.frontPage.background;//textureRegion就是切割texture用的,好像图片坐标系起点是左上角
        backGround = new Image(temp2);

        backGround.setSize(ActConstants.SCREEN_WIDTH, ActConstants.SCREEN_HEIGHT);

        backGround.setPosition(0, 0);



        startButton = addButton_Starting(AssetsUI.instance.frontPage.buttonStart,AssetsUI.instance.frontPage.buttonStart_position);

        settingButton = addButton_Setting(AssetsUI.instance.frontPage.buttonSetting,AssetsUI.instance.frontPage.buttonSetting_position);

        exitButton = addButton_Exit(AssetsUI.instance.frontPage.buttonExit,AssetsUI.instance.frontPage.buttonExit_position);



        AudioManager.instance.play(AssetsUI.instance.music.mainTheme);





        this.addActor(backGround);
        this.addActor(startButton);
        this.addActor(settingButton);
        this.addActor(exitButton);



        ActConstants.publicInformation.put("stage0", this);
    }

    @Override
    public void act(){
        super.act();
    }


    public ImageButton addButton_Starting(final TextureRegion region, final Vector2 position){
        final ImageButton button = addButton(region,position);
        button.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                AudioManager.instance.play(AssetsUI.instance.sounds.confirm);
                AudioManager.instance.stopMusic();
                Stage0 stage0 = (Stage0) ActConstants.publicInformation.get("stage0");
                MyGdxGame.currentStage = new AssetsStageChage(AssetsUI.instance.mainPanel.backgroundForest1,4);
                stage0.dispose();

                return false;
            }
            @Override
            public void enter(InputEvent event, float x, float y,int pointer, @Null Actor fromActor){
                AudioManager.instance.play(AssetsUI.instance.sounds.select);
                button.setSize(region.getRegionWidth()*1.1f,region.getRegionHeight()*1.1f);
                button.getImage().setScale(1.1f);
                button.setPosition(position.x-region.getRegionWidth()*0.1f,position.y-region.getRegionHeight()*0.1f);
            }
            @Override
            public void exit (InputEvent event, float x, float y, int pointer, @Null Actor fromActor) {
                button.setSize(region.getRegionWidth(),region.getRegionHeight());
                button.getImage().setScale(1.0f);
                button.setPosition(position.x,position.y);
            }
        });
        return button;
    }

    public ImageButton addButton_Setting(final TextureRegion region, final Vector2 position){
        final ImageButton button = addButton(region,position);
        button.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.instance.play(AssetsUI.instance.sounds.confirm);


                MyGdxGame.currentStage = new AssetsStageChage(AssetsUI.instance.mainPanel.backgroundForest2,
                        2f);

                return false;
            }
            @Override
            public void enter(InputEvent event, float x, float y,int pointer, @Null Actor fromActor){
                AudioManager.instance.play(AssetsUI.instance.sounds.select);
                button.setSize(region.getRegionWidth()*1.1f,region.getRegionHeight()*1.1f);
                button.getImage().setScale(1.1f);
                button.setPosition(position.x-region.getRegionWidth()*0.1f,position.y-region.getRegionHeight()*0.1f);
            }
            @Override
            public void exit (InputEvent event, float x, float y, int pointer, @Null Actor fromActor) {
                button.setSize(region.getRegionWidth(),region.getRegionHeight());
                button.getImage().setScale(1.0f);
                button.setPosition(position.x,position.y);
            }
        });
        return button;
    }

    public ImageButton addButton_Exit(final TextureRegion region, final Vector2 position){
        final ImageButton button = addButton(region,position);
        button.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AudioManager.instance.play(AssetsUI.instance.sounds.confirm);

                Gdx.app.exit();
                return false;
            }
            @Override
            public void enter(InputEvent event, float x, float y,int pointer, @Null Actor fromActor){
                AudioManager.instance.play(AssetsUI.instance.sounds.select);
                button.setSize(region.getRegionWidth()*1.1f,region.getRegionHeight()*1.1f);
                button.getImage().setScale(1.1f);
                button.setPosition(position.x-region.getRegionWidth()*0.1f,position.y-region.getRegionHeight()*0.1f);
            }
            @Override
            public void exit (InputEvent event, float x, float y, int pointer, @Null Actor fromActor) {
                button.setSize(region.getRegionWidth(),region.getRegionHeight());
                button.getImage().setScale(1.0f);
                button.setPosition(position.x,position.y);
            }
        });
        return button;
    }

    private ImageButton addButton(TextureRegion region, Vector2 position){

        ImageButton imageButton = new ImageButton(new TextureRegionDrawable(region),new TextureRegionDrawable(region));
        imageButton.setPosition(position.x,position.y);
        return imageButton;
    }
}
