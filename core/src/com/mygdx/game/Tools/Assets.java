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


}
