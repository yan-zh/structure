package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.BodyBuilder;
import org.graalvm.compiler.loop.MathUtil;

//超出屏幕时销毁
public class Bubbles extends Actor {
    World world;
    long actorId;
    String name;
    float range;//生成泡泡的区域范围，米
    float radius;
    float distance; //发射台间隔


    public Bubbles(World world, float x, float y, float range, float radius, long actorId, String name) {
        this.world = world;
        setX(x);//气泡场起始位置
        setY(y);
        this.range = range;
        this.radius = radius;
        this.actorId = actorId;
        this.name = name;
        ActConstants.publicInformation.put(name, this);
//        System.out.println("lifetime "+time);
        createBubbles();

    }

    public Body createOneBubble(float x) {
        float velocity = 1f;
        Body body = BodyBuilder.createKineticCircle(world, x, getY(), radius, actorId, name);
        body.setLinearVelocity(0, velocity);
        return body;


    }

    public void createBubbles() {
        final Array<Body> bubbles = new Array<Body>();
        distance = 3f;
        final int numOfLauncher = (int) (range / distance);
        for(int i=0;i<numOfLauncher;i++) {
            final float dis= i*distance;
            Timer timer = new Timer();
            Timer.Task timerTask = new Timer.Task() {
                @Override
                public void run() {
                    bubbles.add(createOneBubble(getX()+dis));
                    for(Body b:bubbles){
                        if(b.getPosition().y>15f){
                           int index= bubbles.indexOf(b,true);
                            bubbles.removeIndex(index);
                            world.destroyBody(b);//阎哥说销毁得放别的地方？
                        }
                    }
                }

            };
            timer.scheduleTask(timerTask, 0, MathUtils.random(2f,10f));// 0s之后执行，每次间隔1s，执行20次。
        System.out.println(i);

        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

//        batch.draw(region,(mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f);
    }


}
