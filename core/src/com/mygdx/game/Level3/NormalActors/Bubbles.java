package com.mygdx.game.Level3.NormalActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.BodyBuilder;
import com.mygdx.game.Tools.asset.AssetsLevel2;
//import org.graalvm.compiler.loop.MathUtil;


public class Bubbles extends Actor {
    public World world;
    long actorId;
    String name;
    float range;
    float radius;
    float distance; //
   public Array<Body> bubbles;
    Integer bubbleIndex;
   public int numOfDestroyed;
   public int numOfSpawned;
    Image image;
    TextureRegion textureRegion;



    public Bubbles(World world, float x, float y, float range, float radius, long actorId, String name) {
        textureRegion = (TextureRegion) AssetsLevel2.instance.decoration.qipao;
        image = new Image(textureRegion);
        this.world = world;
        setX(x);//
        setY(y);
        this.range = range;
        this.radius = radius;
        this.actorId = actorId;
        this.name = name;
        ActConstants.publicInformation.put(name, this);
        bubbleIndex=0;
        numOfDestroyed=0;
        numOfSpawned=0;
//        System.out.println("lifetime "+time);
        bubbles = new Array<Body>();
        createBubbles();

    }

    public Body createOneBubble(float x, String bubbleIndex) {//
        float velocity = 2f;
        Body body = BodyBuilder.createKineticCircle(world, x, getY(), radius, actorId, bubbleIndex);
        body.setLinearVelocity(0, velocity);
        return body;


    }

    public void createBubbles() {

        distance = 3f;
        final int numOfLauncher = (int) (range / distance);
        for(int i=0;i<numOfLauncher;i++) {
            final float dis= i*distance;
            Timer timer = new Timer();
            Timer.Task timerTask = new Timer.Task() {
                @Override
                public void run() {
                    if(numOfSpawned<40){
                        bubbles.add(createOneBubble(getX()+dis,bubbleIndex.toString() ));//这里把数组indedx加进去
//                    System.out.println("index: "+bubbleIndex.toString());
                        bubbleIndex++;
                        numOfSpawned++;
                    }

                    //
                    for(Body b:bubbles){
                        if(b.getPosition().y>90f){
                            b.setTransform(b.getPosition().x,getY(),0);
//                            System.out.println(b);
//                           int index= bubbles.indexOf(b,true);
//                           bubbles.removeIndex(index);
//                            world.destroyBody(b);
//                            numOfDestroyed++;
                        }
                    }
                }

            };
            timer.scheduleTask(timerTask, i*0.5f, MathUtils.random(2f,10f));// 0s之后执行，每次间隔1s，执行20次。
//        System.out.println(i);

        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (Body body : bubbles) {
            batch.draw(textureRegion,(body.getPosition().x-radius)*50,(body.getPosition().y-radius)*50,2*radius*50f,2*radius*50f);
        }

//        batch.draw(region,(mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f);
    }


}
