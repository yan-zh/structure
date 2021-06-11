package com.mygdx.game.Tools.asset;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

/**
 * @auther SHI Zhancheng
 * @create 2021-06-08 21:12
 */
public abstract class AbstractItem {
    TextureAtlas atlas = null;
    Array<AtlasRegion> regions = null;
    AtlasRegion region = null;

    AbstractItem(TextureAtlas atlas){
        this.atlas = atlas;
    }

    /**
     * 封装：辅助快速添加动画
     * @param fps
     * @param name
     * @return
     */
    public Animation addAnimation(float fps, String name){
        return addAnimation(fps,name, Animation.PlayMode.LOOP);
    }

    /**
     *
     * @param fps  帧率
     * @param name Texture的名称
     * @param mood 播放模式，具体如下：
     *             NORMAL：正向播放一次
     *             REVERSED：反向播放一次
     *             LOOP：正向循环播放
     *             LOOP_REVERSED：反向循环播放（最后到第一）
     *             LOOP_PINGPONG：正反向循环播放（第一到最后，然后第一到最后）
     *             LOOP_RANDOM：随机播放
     * @return
     */
    public Animation addAnimation(float fps, String name, Animation.PlayMode mood){
        regions = atlas.findRegions(name);
        return new Animation(fps,regions, mood);
    }

    public AtlasRegion addAtlasRegion(String name){
        return atlas.findRegion(name);
    }
}
