package com.mygdx.game.Tools.asset;

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
import com.badlogic.gdx.utils.Disposable;

/**
 * @auther SHI Zhancheng
 * @create 2021-06-15 00:03
 */
public class AssetsLevel0 implements Disposable, AssetErrorListener {
    public static final String TAG = AssetsLevel0.class.getName();
    public static final AssetsLevel0 instance = new AssetsLevel0();
    private AssetManager assetManager;

    // 测试扩展资源
    public AssetXianjing xianjing;

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
    private AssetsLevel0() {}

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        // 设置资源管理的错误处理对象
        assetManager.setErrorListener(this);
        // 预加载纹理集资源
        assetManager.load(AssetsConstent.TEXTURE_ATLAS_NORMAL, TextureAtlas.class);
        assetManager.load(AssetsConstent.TEXTURE_ATLAS_OBJECTS_LEVEL_0, TextureAtlas.class);
        // 预加载声音
        assetManager.load("core/assets/sounds/Sound0/tortoise_Angry.wav", Sound.class);
        assetManager.load("core/assets/sounds/Sound0/wood_Broken.wav", Sound.class);
        // 预加载音乐
        assetManager.load("core/assets/music/Music0/StartBoss.wav", Music.class);
        assetManager.load("core/assets/music/Music0/StartTheme.wav", Music.class);
        // 开始加载资源，阻塞进程，等待加载完成【此处后期更新进度条】
        assetManager.finishLoading();

        Gdx.app.debug(TAG,"# of assets loaded:"+assetManager.getAssetNames().size);

        for (String a :
                assetManager.getAssetNames()) {
            Gdx.app.debug(TAG, "AssetLevel2: "+a);
        }

        TextureAtlas atlas = assetManager.get(AssetsConstent.TEXTURE_ATLAS_NORMAL);

        // 激活平滑纹理过滤
        for (Texture t :
                atlas.getTextures()) {
            t.setFilter(TextureFilter.Linear,TextureFilter.Linear);
        }

        // 创建通用资源对象
        fonts = new AssetFonts();
        sounds = new AssetSounds(assetManager);
        music = new AssetMusic(assetManager);

        // 加载测试纹理集 第一关
        TextureAtlas atlas_level0 = assetManager.get(AssetsConstent.TEXTURE_ATLAS_OBJECTS_LEVEL_0);
        // 激活平滑纹理过滤
        for (Texture t :
                atlas_level0.getTextures()) {
            t.setFilter(TextureFilter.Linear,TextureFilter.Linear);
        }

        // 创建关卡资源对象
        xianjing = new AssetXianjing(atlas_level0);
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



    // 资源内部类；加载图像和动画资源：
    // 主角资源
    public class AssetXianjing extends AbstractItem{
        public final AtlasRegion main;

        public AssetXianjing(TextureAtlas atlas){
            super(atlas);
            main = addAtlasRegion("xianjing");
        }
    }


    // 资源内部类：音效
    public class AssetSounds {
        public final Sound wood_Broken;
        public final Sound tortoise_Angry;
        public AssetSounds (AssetManager am) {
            wood_Broken = am.get("core/assets/sounds/Sound0/wood_Broken.wav", Sound.class);
            tortoise_Angry = am.get("core/assets/sounds/Sound0/tortoise_Angry.wav", Sound.class);
        }
    }

    // 资源内部类：音效
    public class AssetMusic {
        public final Music startBoss;
        public final Music startTheme;
        public AssetMusic(AssetManager am) {
            startBoss = am.get("core/assets/music/Music0/StartBoss.wav", Music.class);
            startTheme = am.get("core/assets/music/Music0/StartTheme.wav", Music.class);
        }
    }

}
