package com.mygdx.game.Tools.asset;

/**
 * @auther SHI Zhancheng
 * @create 2021-03-03 22:13
 */
public class AssetsConstent {
    // 将游戏世界的可视高度定义为5米（视口高度）
    public static final float VIEWPORT_WIDTH = 5.0f;
    // 将游戏世界的可视高度定义为5米（视口高度）
    public static final float VIEWPORT_HEIGHT = 5.0f;
    // GUI 视口宽度
    public static final float VIEWPORT_GUI_WIDTH = 800f;
    // GUI 视口高度
    public static final float VIEWPORT_GUI_HEIGHT = 480f;

    // 纹理集描述文件路径
    public static final String TEXTURE_ATLAS_NORMAL = "core/assets/images-out/canyonBunny.atlas";
    // 第一关资源
    public static final String TEXTURE_ATLAS_OBJECTS_LEVEL_1 = "core/assets/images_level/level1.atlas";
    // 第二关资源
    public static final String TEXTURE_ATLAS_OBJECTS_LEVEL_2 = "core/assets/images_level/level2.atlas";
    // 第三关资源
    public static final String TEXTURE_ATLAS_OBJECTS_LEVEL_3 = "core/assets/images_level/level3.atlas";
    public static final String TEXTURE_ATLAS_OBJECTS_UI = "core/assets/images_level/images-ui.atlas";
    // 01关卡文件路径
    public static final String LEVEL_01 = "core/assets/levels/level-01.png";
    // 初始化玩家生命数
    public static final int LIVES_START =3;
    // 羽毛道具使用时间
    public static final float ITEM_FEARHER_POWERUP_DURATION = 9.0f;
    // 游戏结束后的延迟时间（s）
    public static final float TIME_DELAY_GAME_OVER =3;
    // UI描述文件路径
    public static final String TEXTURE_ATLAS_UI = "core/assets/images-out/canyonBunny-ui.atlas";
    public static final String TEXTURE_ATLAS_LIBGDX_UI = "core/assets/images-out/uiskin.atlas";
    // skin描述文件
    public static final String SKIN_LIBGDX_UI = "core/assets/images-out/uiskin.json";
    public static final String SKIN_CANYONBUNNY_UI = "core/assets/images-out/canyonBunny-ui.json";
    public static final String PREFERENCES = "core/assets/canyonbunny.prefs";
//    public static final String PREFERENCES = "core/canyonbunny.prefs";

    // 创建carrot对象的数量
    public static final int CARROT_SPAWN_MAX = 100;
    // carrot的散布半径
    public static final float CARROTS_SPAW_RADIUS = 3.5f;
    // 游戏结束后的延迟
    public static final float TIME_DELAY_GAME_FINISHED = 6;

    // 着色器
    public static final String shaderMonochromeVertex = "core/assets/shaders/monochrome.vs";
    public static final String shaderMonochromeFragment = "core/assets/shaders/monochrome.fs";

    // 图片切换的帧率
    public static final float animSpeed = 1.0f;
    public static final float animSpeed12 = animSpeed/12.0f;
    public static final float animSpeed24 = animSpeed/24.0f;
    public static final float animSpeed30 = animSpeed/30.0f;
    public static final float animSpeed45 = animSpeed/45.0f;
    public static final float animSpeed60 = animSpeed/60.0f;

}
