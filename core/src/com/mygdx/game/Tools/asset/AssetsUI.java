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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.Assets;

/**
 * @auther SHI Zhancheng
 * @create 2021-06-10 14:22
 */
public class AssetsUI implements Disposable, AssetErrorListener {
    public static final String TAG = AssetsUI.class.getName();
    public static final AssetsUI instance = new AssetsUI();

    enum SPRITE_TYPE {
        WOOD,
        SAND,
        WIND
    }

    // 主角当前信息
    private static int mainUserLive;
    private static int mainUserLiveMax;
    private static int spriteNumber;

    private static String userName;
    private static SPRITE_TYPE currentSprite;
    private static AtlasRegion currentFrame;
    private static AtlasRegion sprite1;
    private static AtlasRegion sprite2;
    private static AtlasRegion sprite3;
    private AssetManager assetManager;

    // 测试扩展资源
    public AssetFrontPage frontPage;
    // 加载字体
    public AssetFonts fonts;
    // 加载主角资源
    public AssetMainPanel mainPanel;
    public AssetMainUser mainUser;
    public AssetZj zj;
    public AssetJiguan jiguan;
    public AssetFxjling fxjling;
    public AssetTxjling txjling;
    public AssetMxjling mxjling;
    public AssetCdxgw cdxgw;
    public AssetCddpxgw cddpxgw;
    public AssetSpriteAttack spriteAttack;
    public AssetDecoration decoration;


    // 加载音乐
    public AssetSounds sounds;
    public AssetMusic music;


    public class AssetFonts {
        public final BitmapFont defaultSmall;
        public final BitmapFont defaultNormal;
        public final BitmapFont defaultBig;
        public final BitmapFont defaultBigUnFlip;


        public AssetFonts() {
            // 创建三个15磅的位图字体
            defaultSmall = new BitmapFont(
                    Gdx.files.internal("core/assets/images/arial-15.fnt"), true);
            defaultNormal = new BitmapFont(
                    Gdx.files.internal("core/assets/images/arial-15.fnt"), true);
            defaultBig = new BitmapFont(
                    Gdx.files.internal("core/assets/images/arial-15.fnt"), true);
            defaultBigUnFlip = new BitmapFont(
                    Gdx.files.internal("core/assets/images/arial-15.fnt"), false);

            // 设置字体尺寸[新版本字体设置有改动]
            defaultSmall.getData().setScale(0.75f);
            defaultNormal.getData().setScale(1.0f);
            defaultBig.getData().setScale(2.0f);
            defaultBigUnFlip.getData().setScale(2.0f);
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
        assetManager.load("core/assets/sounds/UI/beacon.wav", Sound.class);
        assetManager.load("core/assets/sounds/UI/bullet_Monster_Emmit.wav", Sound.class);
        assetManager.load("core/assets/sounds/UI/bullet_Monster_Hit.wav", Sound.class);
        assetManager.load("core/assets/sounds/UI/bullet_Sand_Emmit.wav", Sound.class);
        assetManager.load("core/assets/sounds/UI/bullet_Sand_Hit.wav", Sound.class);
        assetManager.load("core/assets/sounds/UI/bullet_Wind_Emmit.wav", Sound.class);
        assetManager.load("core/assets/sounds/UI/bullet_Wind_Hit.wav", Sound.class);
        assetManager.load("core/assets/sounds/UI/bullet_Wood_Emmit.wav", Sound.class);
        assetManager.load("core/assets/sounds/UI/bullet_Wood_Hit.wav", Sound.class);
        assetManager.load("core/assets/sounds/UI/fiary_Absorb.wav", Sound.class);
        assetManager.load("core/assets/sounds/UI/jump_MainCharacter.wav", Sound.class);
        assetManager.load("core/assets/sounds/UI/newTest.wav", Sound.class);
        assetManager.load("core/assets/sounds/UI/run_Forest.wav", Sound.class);
        assetManager.load("core/assets/sounds/UI/run_Grass.wav", Sound.class);
        assetManager.load("core/assets/sounds/UI/run_Ground.wav", Sound.class);
        assetManager.load("core/assets/sounds/UI/seinDeathLaserA.wav", Sound.class);
        assetManager.load("core/assets/sounds/UI/seinHurtRegularA.wav", Sound.class);
        assetManager.load("core/assets/sounds/UI/transmit.wav", Sound.class);
        assetManager.load("core/assets/sounds/UI/confirm.wav", Sound.class);
        assetManager.load("core/assets/sounds/UI/select.wav", Sound.class);
        // 预加载音乐
        assetManager.load("core/assets/music/UI/Main Theme.wav", Music.class);
        assetManager.load("core/assets/music/Music2/IceTheme.wav", Music.class);
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
        mainPanel = new AssetMainPanel(atlas_ui);
        mainUser = new AssetMainUser(atlas_ui);
        zj = new AssetZj(atlas_ui);
        jiguan = new AssetJiguan(atlas_ui);
        fxjling = new AssetFxjling(atlas_ui);
        txjling = new AssetTxjling(atlas_ui);
        mxjling = new AssetMxjling(atlas_ui);
        cdxgw = new AssetCdxgw(atlas_ui);
        cddpxgw = new AssetCddpxgw(atlas_ui);
        spriteAttack = new AssetSpriteAttack(atlas_ui);
        decoration = new AssetDecoration(atlas_ui);

        // 初始化主角信息
        mainUserLive = ActConstants.mainUserLive;
        mainUserLiveMax = ActConstants.mainUserLiveMax;
        spriteNumber = 1;
        userName = "Alibaba";
        currentSprite = null;
        currentFrame = mainPanel.paneFrame;
        sprite1 = mainPanel.woodNull;
        sprite2 = mainPanel.sandNull;
        sprite3 = mainPanel.windNull;

    }



    /**
     * 增加一个精灵，目前最大为3只。初始化后是0只
     */
    public void addSprit() {
        if (spriteNumber < 3) {
            spriteNumber++;
            if (spriteNumber == 1) {
                sprite1 = mainPanel.woodHas;
                if (currentSprite == null) {
                    currentSprite = SPRITE_TYPE.WOOD;
                    sprite1 = mainPanel.woodActive;
                }
            } else if (spriteNumber == 2)
                sprite2 = mainPanel.sandHas;
            else sprite3 = mainPanel.windHas;
        }
    }

    public void updateSprite(){
        if (spriteNumber == 1) {
            sprite1 = mainPanel.woodHas;
            if (currentSprite == null) {
                currentSprite = SPRITE_TYPE.WOOD;
                sprite1 = mainPanel.woodActive;
            }
        } else if (spriteNumber == 2)
            sprite2 = mainPanel.sandHas;
        else sprite3 = mainPanel.windHas;
    }

    public void setSprit(int number){
        if (spriteNumber <3&&spriteNumber>=0)
            spriteNumber = number;
    }


    public int sgetSprit(){
        return spriteNumber;
    }

    /**
     * 生命值增加,最大不超过生命上限
     *
     * @param num 增加的数量
     */
    public void addLives(int num) {
        mainUserLive = mainUserLive + num > mainUserLiveMax ? mainUserLiveMax : mainUserLive + num;
        ActConstants.mainUserLive = mainUserLive;

    }

    /**
     * 生命值减少，最少不能小于0，若生命值为0，返回true表示死亡
     *
     * @param num
     * @return isDead 返回是否死亡，true = 主角死亡
     */
    public boolean reduceLives(int num) {
        mainUserLive = mainUserLive - num < 0 ? 0 : mainUserLive - num;
        ActConstants.mainUserLive = mainUserLive;
        return mainUserLive == 0;
    }

    /**
     * 添加 生命上限
     *
     * @param num 添加的数值
     */
    public void addLivesLimit(int num) {
        mainUserLiveMax += num;
        addLives(num);
        ActConstants.mainUserLive = mainUserLiveMax;
    }

    /**
     * 减少 生命上限 并更新生命值
     *
     * @param num 减少的数值
     */
    public void reduceLivesLimit(int num) {
        mainUserLiveMax = mainUserLiveMax - num < 1 ? 1 : mainUserLiveMax - num;
        if (mainUserLiveMax < mainUserLive)
            mainUserLive = mainUserLiveMax;
        ActConstants.mainUserLive = mainUserLiveMax;
    }

    /**
     * 切换到下一个精灵：使用的技能图标会改变
     */
    public void changeToSprit(int num) {
        // 激活新的精灵
        switch (num) {
            case 1:
                if (currentSprite != SPRITE_TYPE.WOOD && spriteNumber >= 1) {
                    unActiveSprite(currentSprite);
                    activeSprite(SPRITE_TYPE.WOOD);
                    currentFrame = mainPanel.paneFrameWood;
                    currentSprite = SPRITE_TYPE.WOOD;
                }
                break;
            case 2:
                if (currentSprite != SPRITE_TYPE.SAND && spriteNumber >= 2) {
                    unActiveSprite(currentSprite);
                    activeSprite(SPRITE_TYPE.SAND);
                    currentFrame = mainPanel.paneFrameSand;
                    currentSprite = SPRITE_TYPE.SAND;
                }
                break;
            case 3:
                if (currentSprite != SPRITE_TYPE.WIND && spriteNumber == 3) {
                    unActiveSprite(currentSprite);
                    activeSprite(SPRITE_TYPE.WIND);
                    currentFrame = mainPanel.paneFrameWind;
                    currentSprite = SPRITE_TYPE.WIND;
                }
                break;
        }
    }

    /**
     * 设置用户姓名
     *
     * @param name
     */
    public void setUserName(String name) {
        userName = name;
    }

    /**
     * 激活当前状态的精灵图标
     *
     * @param type
     */
    private void activeSprite(SPRITE_TYPE type) {
        switch (type) {
            case WOOD:
                sprite1 = mainPanel.woodActive;
                break;
            case SAND:
                sprite2 = mainPanel.sandActive;
                break;
            case WIND:
                sprite3 = mainPanel.windActive;
                break;
        }

    }

    /**
     * 恢复精灵图标至拥有状态
     *
     * @param type
     */
    private void unActiveSprite(SPRITE_TYPE type) {
        switch (type) {
            case WOOD:
                sprite1 = mainPanel.woodHas;
                break;
            case SAND:
                sprite2 = mainPanel.sandHas;
                break;
            case WIND:
                sprite3 = mainPanel.windHas;
                break;
        }
    }

    /**
     * 获得当前对应的精灵技能面板
     *
     * @return
     */
    private AtlasRegion getCurrentFrame() {
        if (currentSprite != null)
            switch (currentSprite) {
                case SAND:
                    currentFrame = mainPanel.paneFrameSand;
                    break;
                case WIND:
                    currentFrame = mainPanel.paneFrameWind;
                    break;
                case WOOD:
                    currentFrame = mainPanel.paneFrameWood;
                    break;
            }
        return currentFrame;
    }

    /**
     * 在舞台中调用：用于绘制左上角的状态栏
     *
     * @param batch
     */
    public void drawUpdate(SpriteBatch batch) {
        batch.setColor(1, 1, 1, 0.8f);
        // 绘制面板、技能图标面板
        batch.draw(getCurrentFrame(), 0, 0, 150, 75, 300, 150,
                1.0f, -1.0f, 0);

        // 绘制精灵图标
        batch.draw(sprite1, 247, 5, 22, 22, 44, 44, 1.0f, -1.0f, 0);
        batch.draw(sprite2, 245, 40, 25, 25, 50, 40, 1.0f, -1.0f, 0);
        batch.draw(sprite3, 247, 90, 25, 25, 50, 50, 1.0f, -1.0f, 0);


        // 绘制血条
        drawBar(batch, 10, 42, 230, 28, AssetsUI.instance.mainPanel.bloodBackground, AssetsUI.instance.mainPanel.bloodKnot);
        // 绘制人物文字信息
        AssetsUI.instance.fonts.defaultBig.draw(batch, userName, 120, 10, 0, Align.center, false);
        AssetsUI.instance.fonts.defaultBig.draw(batch, mainUserLive + "/" + mainUserLiveMax, 120, 45, 0, Align.center, false);
    }

    private void drawBar(SpriteBatch batch, float x, float y, float width, float height, AtlasRegion backgroud, AtlasRegion knot) {
        // 先画背景
        batch.draw(backgroud, x, y, width, height);
        batch.draw(knot, x, y, width * (mainUserLive / (float) mainUserLiveMax), height);
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

        public final Sound beacon;
        public final Sound confirm;
        public final Sound select;
        public final Sound bullet_Monster_Emmit;
        public final Sound bullet_Monster_Hit;
        public final Sound bullet_Sand_Emmit;
        public final Sound bullet_Sand_Hit;
        public final Sound bullet_Wind_Emmit;
        public final Sound bullet_Wind_Hit;
        public final Sound bullet_Wood_Emmit;
        public final Sound bullet_Wood_Hit;
        public final Sound fiary_Absorb;
        public final Sound jump_MainCharacter;
        public final Sound rotateSwitch_Trigger;
        public final Sound run_Forest;
        public final Sound run_Grass;
        public final Sound run_Ground;
        public final Sound seinDeathLaserA;
        public final Sound seinHurtRegularA;
        public final Sound transmit;
        public AssetSounds (AssetManager am) {
            beacon = am.get("core/assets/sounds/UI/beacon.wav", Sound.class);
            bullet_Monster_Emmit = am.get("core/assets/sounds/UI/bullet_Monster_Emmit.wav", Sound.class);
            bullet_Monster_Hit = am.get("core/assets/sounds/UI/bullet_Monster_Hit.wav", Sound.class);
            bullet_Sand_Emmit = am.get("core/assets/sounds/UI/bullet_Sand_Emmit.wav", Sound.class);
            bullet_Sand_Hit = am.get("core/assets/sounds/UI/bullet_Sand_Hit.wav", Sound.class);
            bullet_Wind_Emmit = am.get("core/assets/sounds/UI/bullet_Wind_Emmit.wav", Sound.class);
            bullet_Wind_Hit = am.get("core/assets/sounds/UI/bullet_Wind_Hit.wav", Sound.class);
            bullet_Wood_Emmit = am.get("core/assets/sounds/UI/bullet_Wood_Emmit.wav", Sound.class);
            bullet_Wood_Hit = am.get("core/assets/sounds/UI/bullet_Wood_Hit.wav", Sound.class);
            fiary_Absorb = am.get("core/assets/sounds/UI/fiary_Absorb.wav", Sound.class);
            jump_MainCharacter = am.get("core/assets/sounds/UI/jump_MainCharacter.wav", Sound.class);
            rotateSwitch_Trigger = am.get("core/assets/sounds/UI/newTest.wav", Sound.class);
            run_Forest = am.get("core/assets/sounds/UI/run_Forest.wav", Sound.class);
            run_Grass = am.get("core/assets/sounds/UI/run_Grass.wav", Sound.class);
            run_Ground = am.get("core/assets/sounds/UI/run_Ground.wav", Sound.class);
            seinDeathLaserA = am.get("core/assets/sounds/UI/seinDeathLaserA.wav", Sound.class);
            seinHurtRegularA = am.get("core/assets/sounds/UI/seinHurtRegularA.wav", Sound.class);
            transmit = am.get("core/assets/sounds/UI/transmit.wav", Sound.class);
            confirm = am.get("core/assets/sounds/UI/confirm.wav", Sound.class);
            select = am.get("core/assets/sounds/UI/select.wav", Sound.class);
        }
    }

    // 资源内部类：音效
    public class AssetMusic {
        public final Music mainTheme;
        public final Music iceTheme;
        public AssetMusic(AssetManager am) {
            mainTheme = am.get("core/assets/music/UI/Main Theme.wav", Music.class);
            iceTheme = am.get("core/assets/music/Music2/IceTheme.wav", Music.class);
        }
    }



    public class AssetMainPanel extends AbstractItem {
        // 无精灵的面板
        public final AtlasRegion paneFrame;
        // 土精灵的面板
        public final AtlasRegion paneFrameSand;
        public final AtlasRegion sandNull;
        public final AtlasRegion sandHas;
        public final AtlasRegion sandActive;
        // 风精灵的面板
        public final AtlasRegion paneFrameWind;
        public final AtlasRegion windNull;
        public final AtlasRegion windHas;
        public final AtlasRegion windActive;
        // 木精灵的面板
        public final AtlasRegion paneFrameWood;
        public final AtlasRegion woodNull;
        public final AtlasRegion woodHas;
        public final AtlasRegion woodActive;
        // 血条的背景和前景
        public final AtlasRegion bloodBackground;
        public final AtlasRegion bloodKnot;

        // 进度条的背景和前景
        public final AtlasRegion barBackground;
        public final AtlasRegion barKnot;
        // 换场背景图
        public final AtlasRegion backgroundForest1;
        public final AtlasRegion backgroundForest2;
        public final AtlasRegion backgroundForest3;
        public final AtlasRegion backgroundIce1;
        public final AtlasRegion backgroundDesert1;



//        private Table table;

        AssetMainPanel(TextureAtlas atlas) {
            super(atlas);
            paneFrame = addAtlasRegion("paneFrame");
            paneFrameSand = addAtlasRegion("paneFrameSand");
            sandNull = addAtlasRegion("sandNull");
            sandHas = addAtlasRegion("sandHas");
            sandActive = addAtlasRegion("sandActive");
            paneFrameWind = addAtlasRegion("paneFrameWind");
            windNull = addAtlasRegion("windNull");
            windHas = addAtlasRegion("windHas");
            windActive = addAtlasRegion("windActive");
            paneFrameWood = addAtlasRegion("paneFrameWood");
            woodNull = addAtlasRegion("woodNull");
            woodHas = addAtlasRegion("woodHas");
            woodActive = addAtlasRegion("woodActive");
            barBackground = addAtlasRegion("bar_background");
            bloodBackground = addAtlasRegion("blood1");
            barKnot = addAtlasRegion("bar_knot");
            bloodKnot = addAtlasRegion("blood2");
            backgroundForest1 = addAtlasRegion("forest1");
            backgroundForest2 = addAtlasRegion("forest2");
            backgroundForest3 = addAtlasRegion("forest3");
            backgroundDesert1 = addAtlasRegion("desert1");
            backgroundIce1 = addAtlasRegion("ice1");
        }

        public void update(int lives, SPRITE_TYPE type) {
            mainUserLive = lives;
            currentSprite = type;
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
            animIdle = addAnimation(AssetsConstent.animSpeed12, "acter_idle");
            animAttack = addAnimation(AssetsConstent.animSpeed12, "acter_attack2", Animation.PlayMode.NORMAL);
            animAttack2 = addAnimation(AssetsConstent.animSpeed12, "acter_change_sprite", Animation.PlayMode.NORMAL);
            animChangeSprite = addAnimation(AssetsConstent.animSpeed12, "acter_attack1", Animation.PlayMode.NORMAL);
            animJump = addAnimation(AssetsConstent.animSpeed12, "acter_jump", Animation.PlayMode.NORMAL);
            animDown = addAnimation(AssetsConstent.animSpeed12, "acter_down", Animation.PlayMode.NORMAL);
            animRun = addAnimation(AssetsConstent.animSpeed12, "acter_run");
        }
    }

    public class AssetZj extends AbstractItem{
        public final AtlasRegion main;
        public final Animation animBreath;
        public final Animation animAttack;
        public final Animation animDead;
        AssetZj(TextureAtlas atlas) {
            super(atlas);
            main = addAtlasRegion("zj_idle");
            animBreath = addAnimation(AssetsConstent.animSpeed60,"zj_idle");
            animAttack = addAnimation(AssetsConstent.animSpeed60,"zj_attack");
            animDead = addAnimation(AssetsConstent.animSpeed24,"zj_dead");
        }
    }

    public class AssetJiguan extends AbstractItem{
        public final AtlasRegion jg0;
        public final AtlasRegion jg1;
        AssetJiguan(TextureAtlas atlas) {
            super(atlas);
            jg0 = addAtlasRegion("jg0");
            jg1 = addAtlasRegion("jg1");
        }
    }

    public class AssetCdxgw extends AbstractItem{
        public final AtlasRegion main;
        public final Animation animBreath;
        public final Animation animAttack;
        public final Animation animDead;
        public AssetCdxgw(TextureAtlas atlas){
            super(atlas);
            main = atlas.findRegion("cdXgw_idle");

            Array<AtlasRegion> regions = null;
            AtlasRegion region = null;

            regions = atlas.findRegions("cdXgw_idle");
            animBreath = new Animation(AssetsConstent.animSpeed30,regions,Animation.PlayMode.LOOP_PINGPONG);

            regions = atlas.findRegions("cdXgw_attack");
            animAttack = new Animation(AssetsConstent.animSpeed30,regions,Animation.PlayMode.LOOP_PINGPONG);

            regions = atlas.findRegions("cdXgw_dead");
            animDead = new Animation(AssetsConstent.animSpeed24,regions,Animation.PlayMode.LOOP_PINGPONG);
        }
    }

    public class AssetCddpxgw extends AbstractItem{
        public final AtlasRegion main;
        public final Animation animBreath;
        public final Animation animAttack;
        public final Animation animDead;
        public AssetCddpxgw(TextureAtlas atlas){
            super(atlas);
            main = addAtlasRegion("cdDpxgw_idle");
            animBreath = addAnimation(AssetsConstent.animSpeed30,"cdDpxgw_idle");
            animAttack = addAnimation(AssetsConstent.animSpeed30,"cdDpxgw_attack");
            animDead = addAnimation(AssetsConstent.animSpeed30,"cdDpxgw_dead");
        }
    }

    public class AssetFxjling extends AbstractItem{
        public final AtlasRegion main;
        public final Animation animBreath;
        public final Animation animAttack;
        AssetFxjling(TextureAtlas atlas) {
            super(atlas);
            main = addAtlasRegion("fxJling_idle");
            animBreath = addAnimation(AssetsConstent.animSpeed30,"fxJling_idle");
            animAttack = addAnimation(AssetsConstent.animSpeed30,"fxJling_attack");
        }
    }

    public class AssetTxjling extends AbstractItem{
        public final AtlasRegion main;
        public final Animation animBreath;
        public final Animation animAttack;
        AssetTxjling(TextureAtlas atlas) {
            super(atlas);
            main = addAtlasRegion("txJling_idle");
            animBreath = addAnimation(AssetsConstent.animSpeed30,"txJling_idle");
            animAttack = addAnimation(AssetsConstent.animSpeed30,"txJling_attack");
        }
    }

    public class AssetMxjling extends AbstractItem{
        public final AtlasRegion main;
        public final Animation animBreath;
        public final Animation animAttack;
        AssetMxjling(TextureAtlas atlas) {
            super(atlas);
            main = addAtlasRegion("mxJling_idle");
            animBreath = addAnimation(AssetsConstent.animSpeed30,"mxJling_idle");
            animAttack = addAnimation(AssetsConstent.animSpeed30,"mxJling_attack");
        }
    }

    public class AssetSpriteAttack extends AbstractItem{
        public final AtlasRegion huoqui;
        public final AtlasRegion tzd;
        public final AtlasRegion mzd;
        public final AtlasRegion fzd;
        public final AtlasRegion mhit;
        public final Animation animMhit;
        public final AtlasRegion thit;
        public final Animation animThit;
        public final AtlasRegion fhit;
        public final Animation animFhit;
        AssetSpriteAttack(TextureAtlas atlas) {
            super(atlas);
            huoqui = addAtlasRegion("huoqui");
            tzd = addAtlasRegion("tzd");
            mzd = addAtlasRegion("mzd");
            fzd = addAtlasRegion("fzd");
            mhit = addAtlasRegion("mhit_idle");
            thit = addAtlasRegion("thit_idle");
            fhit = addAtlasRegion("fhit_idle");
            animMhit = addAnimation(AssetsConstent.animSpeed12,"mhit_idle");
            animThit = addAnimation(AssetsConstent.animSpeed12,"thit_idle");
            animFhit = addAnimation(AssetsConstent.animSpeed12,"fhit_idle");
        }
    }

    public class AssetDecoration extends AbstractItem{
        public final AtlasRegion chuansong;
        public final Animation animChuansong;
        public final AtlasRegion chongsheng;
        public final Animation animChongsheng;

        public final AtlasRegion xb;
        public final Animation animXb;
        AssetDecoration(TextureAtlas atlas) {
            super(atlas);
            chuansong = addAtlasRegion("chuansong_idle");
            animChuansong = addAnimation(AssetsConstent.animSpeed24,"chuansong_idle", Animation.PlayMode.NORMAL);
            chongsheng = addAtlasRegion("chongsheng_idle");
            animChongsheng = addAnimation(AssetsConstent.animSpeed5,"chongsheng_idle",Animation.PlayMode.NORMAL);
            xb = addAtlasRegion("xb_idle");
            animXb = addAnimation(AssetsConstent.animSpeed12,"xb_idle",Animation.PlayMode.NORMAL);
        }
    }
}
