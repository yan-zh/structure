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


    public AssetMainCharacter mainCharacter;



    public AssetGoldCoin goldCoin;
    public AssetBunny bunny;
    public AssetRock rock;
    public AssetFeather feather;
    public AssetLevelDecoration levelDecoration;


    public AssetFonts fonts;


    public AssetSounds sounds;
    public AssetMusic music;

    public class AssetFonts {
        public final BitmapFont defaultSmall;
        public final BitmapFont defaultNormal;
        public final BitmapFont defaultBig;


        public AssetFonts () {

            defaultSmall = new BitmapFont(
                    Gdx.files.internal("core/assets/images/arial-15.fnt"),true);
            defaultNormal = new BitmapFont(
                    Gdx.files.internal("core/assets/images/arial-15.fnt"),true);
            defaultBig = new BitmapFont(
                    Gdx.files.internal("core/assets/images/arial-15.fnt"),true);

            defaultSmall.getData().setScale(0.75f);
            defaultNormal.getData().setScale(1.0f);
            defaultBig.getData().setScale(2.0f);

            defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear,TextureFilter.Linear);
            defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear,TextureFilter.Linear);
            defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear,TextureFilter.Linear);
        }
    }


    private Assets() {

    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;

        assetManager.setErrorListener(this);

        assetManager.load(DisplayConstants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
        assetManager.load(DisplayConstants.TEXTURE_ATLAS_OBJECTS_LEVEL_1, TextureAtlas.class);

        assetManager.load("core/assets/sounds/jump.wav", Sound.class);
        assetManager.load("core/assets/sounds/jump_with_feather.wav", Sound.class);
        assetManager.load("core/assets/sounds/pickup_coin.wav", Sound.class);
        assetManager.load("core/assets/sounds/pickup_feather.wav", Sound.class);
        assetManager.load("core/assets/sounds/live_lost.wav", Sound.class);

        assetManager.load("core/assets/music/keith303_-_brand_new_highscore.mp3", Music.class);

        assetManager.finishLoading();
        Gdx.app.debug(TAG,"# of assets loaded:"+assetManager.getAssetNames().size);

        for (String a :
                assetManager.getAssetNames()) {
            Gdx.app.debug(TAG, "Asset: "+a);
        }

        TextureAtlas atlas = assetManager.get(DisplayConstants.TEXTURE_ATLAS_OBJECTS);


        for (Texture t :
                atlas.getTextures()) {
            t.setFilter(TextureFilter.Linear,TextureFilter.Linear);
        }


        fonts = new AssetFonts();
        bunny = new AssetBunny(atlas);
        rock = new AssetRock(atlas);
//        goldCoin = new AssetGoldCoin(atlas);
        feather = new AssetFeather(atlas);
        levelDecoration = new AssetLevelDecoration(atlas);
        sounds = new AssetSounds(assetManager);
        music = new AssetMusic(assetManager);

        //
        TextureAtlas atlas_test = assetManager.get(DisplayConstants.TEXTURE_ATLAS_OBJECTS_LEVEL_1);
        //
        mainCharacter = new AssetMainCharacter(atlas_test);

        goldCoin = new AssetGoldCoin(atlas_test);
        //


    }

    @Override
    public void dispose() {
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




    public class AssetMainCharacter {
        public final AtlasRegion main;
        public final Animation animBreath;
        public final Animation animRun;

        public AssetMainCharacter(TextureAtlas atlas){
            main = atlas.findRegion("ygg_Attack A");

            Array<AtlasRegion> regions = null;
            AtlasRegion region = null;

            regions = atlas.findRegions("ygg_Attack A");
            animBreath = new Animation(1.0f/30.0f,regions,Animation.PlayMode.LOOP_PINGPONG);

            regions = atlas.findRegions("ygg_Attack A");
            animRun = new Animation(1.0f/30.0f,regions,Animation.PlayMode.LOOP_REVERSED);
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

        public AssetFeather (TextureAtlas atlas) {
            feather = atlas.findRegion("item_feather");
        }
    }

    // 资源内部类：装饰资源
    public class AssetLevelDecoration {
        public final AtlasRegion cloud01;
        public final AtlasRegion cloud02;
        public final AtlasRegion cloud03;
        public final AtlasRegion mountainLeft;
        public final AtlasRegion mountainRight;
        public final AtlasRegion waterOverlay;
        public final AtlasRegion carrot;
        public final AtlasRegion goal;

        public AssetLevelDecoration (TextureAtlas atlas) {
            cloud01 = atlas.findRegion("cloud01");
            cloud02 = atlas.findRegion("cloud02");
            cloud03 = atlas.findRegion("cloud03");
            mountainLeft = atlas.findRegion("mountain_left");
            mountainRight = atlas.findRegion("mountain_right");
            waterOverlay = atlas.findRegion("water_overlay");
            carrot = atlas.findRegion("carrot");
            goal = atlas.findRegion("goal");
        }
    }

    // 资源内部类：音乐
    public class AssetSounds {
        public final Sound jump;
        public final Sound jumpWithFeather;
        public final Sound pickupCoin;
        public final Sound pickupFeather;
        public final Sound liveLost;

        public AssetSounds (AssetManager am) {
            jump = am.get("core/assets/sounds/jump.wav", Sound.class);
            jumpWithFeather = am.get("core/assets/sounds/jump_with_feather.wav", Sound.class);
            pickupCoin = am.get("core/assets/sounds/pickup_coin.wav", Sound.class);
            pickupFeather = am.get("core/assets/sounds/pickup_feather.wav", Sound.class);
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
