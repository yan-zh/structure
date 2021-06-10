package com.mygdx.game.Level1;

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
import com.mygdx.game.Level2.Stage2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.asset.AssetsUI;


public class Stage1 extends Stage {//有一些按钮和背景，是类似开始界面

    //按钮和背景
    private ImageButton startButton;
    private ImageButton settingButton;
    private ImageButton exitButton;
    private Vector2 position01;
    private Vector2 position02;
    private Vector2 position03;

    private Image backGround;


    public Stage1(InputMultiplexer inputMultiplexer){
        this.setDebugAll(true);
        // 初始化ui资源
        AssetsUI.instance.init(new AssetManager());
        inputMultiplexer.addProcessor(this);//通过这个接受来自用户的操作信号
        //做了个背景和按钮（以后所有的素材，包括音乐，图片，动画都通过Asset类获取，后面会有Asset类的例子）
        // 添加首页背景图
        TextureRegion temp2 = AssetsUI.instance.frontPage.background;//textureRegion就是切割texture用的,好像图片坐标系起点是左上角
        backGround = new Image(temp2);//image和texture区别？？

        backGround.setSize(ActConstants.SCREEN_WIDTH, ActConstants.SCREEN_HEIGHT);

        backGround.setPosition(0, 0);//舞台坐标是左下角


        // 添加开始按钮
        startButton = addButton_Starting(AssetsUI.instance.frontPage.buttonStart,AssetsUI.instance.frontPage.buttonStart_position);
        // 添加设置按钮
        settingButton = addButton_Setting(AssetsUI.instance.frontPage.buttonSetting,AssetsUI.instance.frontPage.buttonSetting_position);
        // 添加退出按钮
        exitButton = addButton_Exit(AssetsUI.instance.frontPage.buttonExit,AssetsUI.instance.frontPage.buttonExit_position);


        // 播放背景音乐
        AudioManager.instance.play(AssetsUI.instance.music.bmg01);
        this.addActor(backGround);
        this.addActor(startButton);
        this.addActor(settingButton);
        this.addActor(exitButton);
      //  this.addListener(new MyInputListener1());//这个监听是对从最外层传入的输入信息的响应,可以多个舞台各自new一个同一个类的对象，都有效果
        //****************注意，这个监听就算这个舞台没运行act也有效果
    }


    public ImageButton addButton_Starting(final TextureRegion region, final Vector2 position){
        final ImageButton button = addButton(region,position);
        button.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // 在适当的条件（这里是按钮被按下），切换舞台，用下一个舞台换掉MyGdxGame的currentStage
                AudioManager.instance.play(AssetsUI.instance.sounds.comfirm);
                AudioManager.instance.stopMusic();
                MyGdxGame.currentStage = new Stage2(ActConstants.inputMultiplexer);
                //注意每次切换舞台时把旧舞台dispose了，清空它占用的资源，主要是这个舞台用到的Asset
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
                AudioManager.instance.play(AssetsUI.instance.sounds.comfirm);
                // 点击打开社渚窗口：还未写

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
                AudioManager.instance.play(AssetsUI.instance.sounds.comfirm);
                // 点击退出游戏
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
        // 添加项目
        ImageButton imageButton = new ImageButton(new TextureRegionDrawable(region),new TextureRegionDrawable(region));
        imageButton.setPosition(position.x,position.y);
        return imageButton;
    }
}
