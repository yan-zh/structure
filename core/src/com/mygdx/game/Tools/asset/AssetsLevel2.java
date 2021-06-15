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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * @auther SHI Zhancheng
 * @create 2021-05-06 14:22
 */
public class AssetsLevel2 implements Disposable, AssetErrorListener {
    public static final String TAG = AssetsLevel2.class.getName();
    public static final AssetsLevel2 instance = new AssetsLevel2();
    private AssetManager assetManager;

    // 测试扩展资源
    public AssetDaoju daoju;
    public AssetPingtaiguai pingtaiguai;
    public AssetZboos zboos;
    public AssetPangxie pangxie;
    public AssetDecoration decoration;

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
    private AssetsLevel2() {}

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        // 设置资源管理的错误处理对象
        assetManager.setErrorListener(this);
        // 预加载纹理集资源
        assetManager.load(AssetsConstent.TEXTURE_ATLAS_NORMAL, TextureAtlas.class);
        assetManager.load(AssetsConstent.TEXTURE_ATLAS_OBJECTS_LEVEL_2, TextureAtlas.class);
        // 预加载声音
//        assetManager.load("core/assets/sounds/Sound2/bubble_Emmit.wav", Sound.class);
        assetManager.load("core/assets/sounds/Sound2/bubbleBound.wav", Sound.class);
//        assetManager.load("core/assets/sounds/Sound2/clap.wav", Sound.class);
        assetManager.load("core/assets/sounds/Sound2/ice_Broken.wav", Sound.class);
        assetManager.load("core/assets/sounds/Sound2/Light.wav", Sound.class);
        assetManager.load("core/assets/sounds/Sound2/Zboss.wav", Sound.class);
        // 预加载音乐
        assetManager.load("core/assets/music/Music2/FinalBoss.wav", Music.class);
        assetManager.load("core/assets/music/Music2/IceTheme.wav", Music.class);
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
        TextureAtlas atlas_level02 = assetManager.get(AssetsConstent.TEXTURE_ATLAS_OBJECTS_LEVEL_2);
        // 激活平滑纹理过滤
        for (Texture t :
                atlas_level02.getTextures()) {
            t.setFilter(TextureFilter.Linear,TextureFilter.Linear);
        }

        // 创建关卡资源对象
        daoju = new AssetDaoju(atlas_level02);
        pingtaiguai = new AssetPingtaiguai(atlas_level02);
        zboos = new AssetZboos(atlas_level02);
        decoration = new AssetDecoration(atlas_level02);
        pangxie = new AssetPangxie(atlas_level02);
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
    public class AssetTestItem extends AbstractItem{
        public final AtlasRegion main;
        public final Animation animBreath;

        public AssetTestItem(TextureAtlas atlas){
            super(atlas);
            main = addAtlasRegion("ClockworkKnightD_Idle");
            animBreath = addAnimation(AssetsConstent.animSpeed24,"ClockworkKnightD_Idle");
        }
    }


    // 资源内部类：音效
    public class AssetSounds {
//        public final Sound bubble_Emmit;
        public final Sound bubbleBound;
//        public final Sound clap;
        public final Sound ice_Broken;
        public final Sound Light;
        public final Sound Zboss;
        public AssetSounds (AssetManager am) {
//            bubble_Emmit = am.get("core/assets/sounds/Sound2/bubble_Emmit.wav", Sound.class);
            bubbleBound = am.get("core/assets/sounds/Sound2/bubbleBound.wav", Sound.class);
//            clap = am.get("core/assets/sounds/Sound2/clap.wav", Sound.class);
            ice_Broken = am.get("core/assets/sounds/Sound2/ice_Broken.wav", Sound.class);
            Light = am.get("core/assets/sounds/Sound2/Light.wav", Sound.class);
            Zboss = am.get("core/assets/sounds/Sound2/Zboss.wav", Sound.class);
        }
    }

    // 资源内部类：音效
    public class AssetMusic {
        public final Music FinalBoss;
        public final Music IceTheme;
        public AssetMusic(AssetManager am) {
            FinalBoss = am.get("core/assets/music/Music2/FinalBoss.wav", Music.class);
            IceTheme = am.get("core/assets/music/Music2/IceTheme.wav", Music.class);
        }
    }

    public class AssetDaoju extends AbstractItem{
        public final AtlasRegion bingtai;
        public final AtlasRegion bingkuai;
        AssetDaoju(TextureAtlas atlas) {
            super(atlas);
            bingtai = addAtlasRegion("bingtai");
            bingkuai = addAtlasRegion("bingkuai");
        }
    }

    public class AssetPingtaiguai extends AbstractItem{
        public final AtlasRegion main;
        public final Animation animBreath;
        public final Animation animAttack;
        AssetPingtaiguai(TextureAtlas atlas) {
            super(atlas);
            main = addAtlasRegion("pingtaiguai_idle");
            animBreath = addAnimation(AssetsConstent.animSpeed24,"pingtaiguai_idle");
            animAttack = addAnimation(AssetsConstent.animSpeed24,"pingtaiguai_attack", Animation.PlayMode.NORMAL);
        }
    }

    public class AssetZboos extends AbstractItem{
        public final AtlasRegion main;
        public final Animation animBreath;
        public final Animation animAttack;
        AssetZboos(TextureAtlas atlas) {
            super(atlas);
            main = addAtlasRegion("zBoss_idle");
            animBreath = addAnimation(AssetsConstent.animSpeed30,"zBoss_idle");
            animAttack = addAnimation(AssetsConstent.animSpeed30,"zBoss_attack");
        }
    }

    public class AssetDecoration extends AbstractItem{
        public final AtlasRegion huolu;
        public final AtlasRegion qipao;
        public final AtlasRegion csg1;
        public final AtlasRegion sand;
        public final AtlasRegion jieshouqi;
        public final AtlasRegion jiguang;
        public final AtlasRegion huoqiujieshouqi;
        public final AtlasRegion pianzhuanqi;
        public final Animation animSand;
        public final AtlasRegion cf;
        public final Animation animCf;
        AssetDecoration(TextureAtlas atlas) {
            super(atlas);
            huolu = addAtlasRegion("huolu");
            qipao = addAtlasRegion("qipao");
            csg1 = addAtlasRegion("csg1");
            sand = addAtlasRegion("sand_idle");
            cf = addAtlasRegion("cf_idle");
            jieshouqi = addAtlasRegion("jieshouqi");
            jiguang = addAtlasRegion("jiguang");
            huoqiujieshouqi = addAtlasRegion("huoqiujieshouqi");
            pianzhuanqi = addAtlasRegion("pianzhuanqi");
            animSand = addAnimation(AssetsConstent.animSpeed45,"sand_idle", Animation.PlayMode.LOOP_REVERSED);
            animCf = addAnimation(AssetsConstent.animSpeed5,"cf_idle");
        }
    }

    public class AssetPangxie extends AbstractItem{
        public final AtlasRegion main;
        public final Animation animBreath;
        AssetPangxie(TextureAtlas atlas) {
            super(atlas);
            main = addAtlasRegion("px_idle");
            animBreath = addAnimation(AssetsConstent.animSpeed30,"px_idle");
        }
    }
}
