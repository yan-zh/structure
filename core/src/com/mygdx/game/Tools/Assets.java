package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Constants.DisplayConstants;

/**
 * @auther SHI Zhancheng
 * @create 2021-03-06 14:22
 */
public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    private AssetManager assetManager;

    // 图片切换的帧率 animSpeed/animSpeedA|B|C
    private float animSpeed = 1.0f;
    private float animSpeedA = 15.0f;
    private float animSpeedB = 20.0f;
    private float animSpeedC = 30.0f;



    // add for test
    public AssetGoldCoin goldCoin;
    public AssetBunny bunny;
    public AssetRock rock;
    public AssetFeather feather;
    public AssetLevelDecoration levelDecoration;
    // add for test


    // 从这里开始进行资源扩展；后期考虑将他们分开初始化；
    public AssetFijling fijling;
    public AssetCdxgw cdxgw;
    public AssetCddpxgw cddpxgw;
    public AssetCdboss cdboss;





    // 资源扩展结束

    // 创建内部类变量
    public AssetMainCharacter mainCharacter;
    public AssetCharacter01 characrer01;
    public AssetCharacter02 characrer02;

    // 加载字体
    public AssetFonts fonts;

    // 加载音乐
    public AssetSounds sounds;
    public AssetMusic music;

    public class AssetFonts {
        public final BitmapFont defaultSmall;
        public final BitmapFont defaultNormal;
        public final BitmapFont defaultBig;


        public AssetFonts () {
            // 创建三个15磅的位图字体
            defaultSmall = new BitmapFont(
                    Gdx.files.internal("core/assets/images/arial-15.fnt"),true);
            defaultNormal = new BitmapFont(
                    Gdx.files.internal("core/assets/images/arial-15.fnt"),true);
            defaultBig = new BitmapFont(
                    Gdx.files.internal("core/assets/images/arial-15.fnt"),true);

            // 设置字体尺寸[新版本字体设置有改动]
            defaultSmall.getData().setScale(0.75f);
            defaultNormal.getData().setScale(1.0f);
            defaultBig.getData().setScale(2.0f);
            // 为字体激活线性纹理过滤模式
            defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear,TextureFilter.Linear);
            defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear,TextureFilter.Linear);
            defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear,TextureFilter.Linear);
        }
    }

    // 单例类
    private Assets() {}

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        // 设置资源管理的错误处理对象
        assetManager.setErrorListener(this);
        // 预加载纹理集资源
        assetManager.load(DisplayConstants.TEXTURE_ATLAS_NORMAL, TextureAtlas.class);
        assetManager.load(DisplayConstants.TEXTURE_ATLAS_OBJECTS_LEVEL_1, TextureAtlas.class);
        // 预加载声音
        assetManager.load("core/assets/sounds/jump.wav", Sound.class);
        assetManager.load("core/assets/sounds/jump_with_feather.wav", Sound.class);
        assetManager.load("core/assets/sounds/pickup_coin.wav", Sound.class);
        assetManager.load("core/assets/sounds/pickup_feather.wav", Sound.class);
        assetManager.load("core/assets/sounds/live_lost.wav", Sound.class);
        // 预加载音乐
        assetManager.load("core/assets/music/keith303_-_brand_new_highscore.mp3", Music.class);
        // 开始加载资源，阻塞进程，等待加载完成
        assetManager.finishLoading();
        Gdx.app.debug(TAG,"# of assets loaded:"+assetManager.getAssetNames().size);

        for (String a :
                assetManager.getAssetNames()) {
            Gdx.app.debug(TAG, "Asset: "+a);
        }

        TextureAtlas atlas = assetManager.get(DisplayConstants.TEXTURE_ATLAS_NORMAL);

        // 激活平滑纹理过滤
        for (Texture t :
                atlas.getTextures()) {
            t.setFilter(TextureFilter.Linear,TextureFilter.Linear);
        }

        // 创建游戏资源对象
        fonts = new AssetFonts();
        sounds = new AssetSounds(assetManager);
        music = new AssetMusic(assetManager);

        // 加载测试纹理集 第一关
        TextureAtlas atlas_level01 = assetManager.get(DisplayConstants.TEXTURE_ATLAS_OBJECTS_LEVEL_1);
        // 激活平滑纹理过滤
        for (Texture t :
                atlas_level01.getTextures()) {
            t.setFilter(TextureFilter.Linear,TextureFilter.Linear);
        }

        // 测试类
        fonts = new AssetFonts();
        bunny = new AssetBunny(atlas_level01);
        rock = new AssetRock(atlas_level01);
        goldCoin = new AssetGoldCoin(atlas_level01);
        feather = new AssetFeather(atlas_level01);
        levelDecoration = new AssetLevelDecoration(atlas);
        sounds = new AssetSounds(assetManager);
        music = new AssetMusic(assetManager);

        // 主角类
        mainCharacter = new AssetMainCharacter(atlas_level01);
        characrer01 = new AssetCharacter01(atlas_level01);
        characrer02 = new AssetCharacter02(atlas_level01);
        // 测试对象
    }



    @Override
    public void dispose() {
        // 释放资源管理器中的文件
        assetManager.dispose();
        fonts.defaultSmall.dispose();
        fonts.defaultNormal.dispose();
        fonts.defaultBig.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.debug(TAG,"couldn't load asset '" + asset.fileName +
                "'",(Exception)throwable);
    }



    // 资源内部类：玩家角色
    // 主角资源
    public class AssetMainCharacter {
        public final AtlasRegion main;
        public final Animation animBreath;

        public AssetMainCharacter(TextureAtlas atlas){
            main = atlas.findRegion("ClockworkKnightD_Idle");
            Array<AtlasRegion> regions = null;
            AtlasRegion region = null;

            regions = atlas.findRegions("ClockworkKnightD_Idle");
            animBreath = new Animation(1.0f/30.0f,regions,Animation.PlayMode.LOOP_PINGPONG);
        }
    }

    // 资源内部类：角色1
    public class AssetCharacter01 {
        public final AtlasRegion main;
        public final Animation animBreath;


        public AssetCharacter01 (TextureAtlas atlas) {
            main = atlas.findRegion("Jubokko_Idle");

            // Animation:
            Array<AtlasRegion> regions = atlas.findRegions("Jubokko_Idle");
            AtlasRegion region = regions.first();
            for (int i = 0; i<10;i++)
                regions.insert(0,region);
            animBreath = new Animation(1.0f/20f,regions,
                    Animation.PlayMode.LOOP_REVERSED);
        }

    }
    // 角色二
    public class AssetCharacter02 {
        public final AtlasRegion main;
        public final Animation animBreath;
        public final Animation animRun;

        public AssetCharacter02(TextureAtlas atlas){
            main = atlas.findRegion("ygg_Attack A");

            Array<AtlasRegion> regions = null;
            AtlasRegion region = null;

            regions = atlas.findRegions("ygg_Attack A");
            animBreath = new Animation(1.0f/30.0f,regions,Animation.PlayMode.LOOP_PINGPONG);

            regions = atlas.findRegions("ygg_Attack A");
            animRun = new Animation(1.0f/30.0f,regions,Animation.PlayMode.LOOP_REVERSED);
        }
    }

    // 资源内部类：装饰资源，预留存储其他无动画要求的素材
    public class AssetLevelDecoration {
        public final AtlasRegion cloud01;
        public AssetLevelDecoration (TextureAtlas atlas) {
            cloud01 = atlas.findRegion("cloud01");
        }
    }

    // 资源内部类：音乐
    public class AssetSounds {
        public final Sound jump;
        public final Sound jump2;
        public final Sound pickupObject1;
        public final Sound pickupObject2;
        public final Sound liveLost;

        public AssetSounds (AssetManager am) {
            jump = am.get("core/assets/sounds/jump.wav", Sound.class);
            jump2 = am.get("core/assets/sounds/jump_with_feather.wav", Sound.class);
            pickupObject1 = am.get("core/assets/sounds/pickup_coin.wav", Sound.class);
            pickupObject2 = am.get("core/assets/sounds/pickup_feather.wav", Sound.class);
            liveLost = am.get("core/assets/sounds/live_lost.wav", Sound.class);
        }
    }

    public class AssetMusic {
        public final Music song01;

        public AssetMusic(AssetManager am) {
            song01 = am.get("core/assets/music/keith303_-_brand_new_highscore.mp3", Music.class);
        }
    }

    // 资源内部类：金币道具
    public class AssetGoldCoin {
        public final AtlasRegion goldCoin;
        public final Animation animGoldCoin;


        public AssetGoldCoin (TextureAtlas atlas) {
            goldCoin = atlas.findRegion("Jubokko_Idle");

            // Animation: Gold Coin
            Array<AtlasRegion> regions = atlas.findRegions("Jubokko_Idle");
            AtlasRegion region = regions.first();
            for (int i = 0; i<10;i++)
                regions.insert(0,region);
            animGoldCoin = new Animation(1.0f/20f,regions,
                    Animation.PlayMode.LOOP_REVERSED);

        }

    }

    public class AssetBunny {
        public final AtlasRegion head;
        public final Animation animNormal;
        public final Animation animCopterTransform;
        public final Animation animCopterTransformBack;
        public final Animation getAnimCopterRotate;
        public AssetBunny (TextureAtlas atlas) {
            head = atlas.findRegion("bunny_head");

            Array<AtlasRegion> regions = null;
            AtlasRegion region = null;

            // Animation : Bunny Normal
            regions = atlas.findRegions("anim_bunny_normal");
            animNormal = new Animation(1.0f/ 10.0f, regions,Animation.PlayMode.LOOP_PINGPONG);

            // Animation: Bunny Copter - knot earsnn
            regions = atlas.findRegions("anim_bunny_normal");
            animCopterTransform = new Animation(1.0f/ 10.0f, regions);

            // Animation: Bunny Copter - unknot ears
            regions = atlas.findRegions("anim_bunny_normal");
            animCopterTransformBack = new Animation(1.0f/ 10.0f, regions,Animation.PlayMode.LOOP_REVERSED);

            // Animation: Bunny Copter - rotate ears
            regions = new Array<AtlasRegion>();
            regions.add(atlas.findRegion("anim_bunny_copter",4));
            regions.add(atlas.findRegion("anim_bunny_copter",5));
            getAnimCopterRotate = new Animation(1.0f/ 15.0f, regions);
        }
    }

    // 资源内部类：石块
    public class AssetRock {
        public final AtlasRegion edge;
        public final AtlasRegion middle;

        public AssetRock (TextureAtlas atlas) {
            edge = atlas.findRegion("rock_edge");
            middle = atlas.findRegion("rock_middle");
        }
    }


    // 资源内部类：羽毛道具
    public class AssetFeather {
        public final AtlasRegion feather;

        public AssetFeather(TextureAtlas atlas) {
            feather = atlas.findRegion("item_feather");
        }

    }

    // 正式添加的资源
    private class AssetFijling {
        public final AtlasRegion main;
        public final Animation animBreath;
        public final Animation animAttack;
        public AssetFijling(TextureAtlas atlas){
            main = atlas.findRegion("fxJling_idle");

            Array<AtlasRegion> regions = null;
            AtlasRegion region = null;

            regions = atlas.findRegions("fxJling_idle");
            animBreath = new Animation(1.0f/30.0f,regions,Animation.PlayMode.LOOP_PINGPONG);

            regions = atlas.findRegions("fxJling_idle");
            animAttack = new Animation(1.0f/30.0f,regions,Animation.PlayMode.LOOP_PINGPONG);
        }
    }


    private class AssetCdxgw {
    }

    private class AssetCddpxgw {
    }

    private class AssetCdboss {
    }
}
