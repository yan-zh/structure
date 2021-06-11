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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

/**
 * @auther SHI Zhancheng
 * @create 2021-06-10 14:22
 */
public class AssetsUI implements Disposable, AssetErrorListener {
    public static final String TAG = AssetsUI.class.getName();
    public static final AssetsUI instance = new AssetsUI();
    private AssetManager assetManager;

    // 测试扩展资源
    public AssetFrontPage frontPage;
    // 加载字体
    public AssetFonts fonts;
    // 加载主角资源
    public AssetMainUser mainUser;

    // 加载音乐
    public AssetSounds sounds;
    public AssetMusic music;

    public class AssetFonts {
        public final BitmapFont defaultSmall;
        public final BitmapFont defaultNormal;
        public final BitmapFont defaultBig;


        public AssetFonts() {
            // 创建三个15磅的位图字体
            defaultSmall = new BitmapFont(
                    Gdx.files.internal("core/assets/images/arial-15.fnt"), true);
            defaultNormal = new BitmapFont(
                    Gdx.files.internal("core/assets/images/arial-15.fnt"), true);
            defaultBig = new BitmapFont(
                    Gdx.files.internal("core/assets/images/arial-15.fnt"), true);

            // 设置字体尺寸[新版本字体设置有改动]
            defaultSmall.getData().setScale(0.75f);
            defaultNormal.getData().setScale(1.0f);
            defaultBig.getData().setScale(2.0f);
            // 为字体激活线性纹理过滤模式
            defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
            defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
            defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }
    }

    // 单例类
    private AssetsUI() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        // 设置资源管理的错误处理对象
        assetManager.setErrorListener(this);
        // 预加载纹理集资源
        assetManager.load(AssetsConstent.TEXTURE_ATLAS_NORMAL, TextureAtlas.class);
        assetManager.load(AssetsConstent.TEXTURE_ATLAS_OBJECTS_UI, TextureAtlas.class);
        // 预加载声音
        assetManager.load("core/assets/sounds/select.wav", Sound.class);
        assetManager.load("core/assets/sounds/confirm.wav", Sound.class);
        // 预加载音乐
        assetManager.load("core/assets/music/bgm01.wav", Music.class);
        // 开始加载资源，阻塞进程，等待加载完成【此处后期更新进度条】
        assetManager.finishLoading();

        Gdx.app.debug(TAG, "# of assets loaded:" + assetManager.getAssetNames().size);

        for (String a :
                assetManager.getAssetNames()) {
            Gdx.app.debug(TAG, "AssetLevel2: " + a);
        }

        TextureAtlas atlas = assetManager.get(AssetsConstent.TEXTURE_ATLAS_NORMAL);

        // 激活平滑纹理过滤
        for (Texture t :
                atlas.getTextures()) {
            t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }

        // 创建通用资源对象
        fonts = new AssetFonts();
        sounds = new AssetSounds(assetManager);
        music = new AssetMusic(assetManager);

        // 加载测试纹理集 第一关
        TextureAtlas atlas_ui = assetManager.get(AssetsConstent.TEXTURE_ATLAS_OBJECTS_UI);
        // 激活平滑纹理过滤
        for (Texture t :
                atlas_ui.getTextures()) {
            t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }
        // 创建关卡资源对象
        frontPage = new AssetFrontPage(atlas_ui);
        mainUser = new AssetMainUser(atlas_ui);

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
        Gdx.app.debug(TAG, "couldn't load asset '" + asset.fileName +
                "'", (Exception) throwable);
    }



    // 资源内部类；加载图像和动画资源：
    // 主角资源
    public class AssetFrontPage extends AbstractItem{

        public final AtlasRegion background;
        public final AtlasRegion buttonStart;

        public final Vector2 buttonStart_position;
        public final AtlasRegion buttonSetting;
        public final Vector2 buttonSetting_position;
        public final AtlasRegion buttonExit;
        public final Vector2 buttonExit_position;

        public AssetFrontPage(TextureAtlas atlas) {
            super(atlas);
            background = addAtlasRegion("frontPage");
            buttonStart = addAtlasRegion("start_button");
            buttonStart_position = new Vector2(800,1080-640-60);
            buttonSetting = addAtlasRegion("setting_button");
            buttonSetting_position = new Vector2(800,1080-640-60-100);
            buttonExit = addAtlasRegion("exit_button");
            buttonExit_position = new Vector2(800,1080-640-60-200);
//        }
        }
    }


    // 资源内部类：音效
    public class AssetSounds {
        public final Sound select;
        public final Sound comfirm;
        public AssetSounds (AssetManager am) {
            select = am.get("core/assets/sounds/select.wav", Sound.class);
            comfirm = am.get("core/assets/sounds/confirm.wav", Sound.class);
        }
    }

    // 资源内部类：音效
    public class AssetMusic {
        public final Music bmg01;
        public AssetMusic(AssetManager am) {
            bmg01 = am.get("core/assets/music/bgm01.wav", Music.class);
        }
    }

    public class AssetMainUser extends AbstractItem{
        public final AtlasRegion main;
        public final AtlasRegion isUp;
        public final Animation animIdle;
        public final Animation animAttack;
        public final Animation animAttack2;
        public final Animation animChangeSprite;
        public final Animation animJump;
        public final Animation animDown;
        public final Animation animRun;

        AssetMainUser(TextureAtlas atlas) {
            super(atlas);
            main = addAtlasRegion("acter_main");
            isUp = addAtlasRegion("acter_fly");
            animIdle = addAnimation(AssetsConstent.animSpeed12,"acter_idle");
            animAttack = addAnimation(AssetsConstent.animSpeed12,"acter_attack2", Animation.PlayMode.NORMAL);
            animAttack2 = addAnimation(AssetsConstent.animSpeed12,"acter_change_sprite", Animation.PlayMode.NORMAL);
            animChangeSprite = addAnimation(AssetsConstent.animSpeed12,"acter_attack1", Animation.PlayMode.NORMAL);
            animJump = addAnimation(AssetsConstent.animSpeed12,"acter_jump",Animation.PlayMode.NORMAL);
            animDown = addAnimation(AssetsConstent.animSpeed12,"acter_down",Animation.PlayMode.NORMAL);
            animRun = addAnimation(AssetsConstent.animSpeed12,"acter_run");
        }
    }
}
