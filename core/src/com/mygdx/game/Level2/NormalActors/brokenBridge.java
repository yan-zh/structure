package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.UserData;

public class brokenBridge extends Actor {
   Body mySimulation;
   FixtureDef myFixtureDef;
   BodyDef myBodyDef;
   Fixture myFixture;

   Animation wait;
   Animation broken;

   TextureRegion currentFrame;

   public boolean state = true;

   float statetime;

   String name;

   World world;

   public brokenBridge(Animation animationWait, Animation animationAbsorb, int x, int y, long actorId, World world, String name)
   {
      //Set position
      this.setX(x);
      this.setY(y);

      //Set the animation of the wait state and absorb
      this.wait = animationWait;
      this.broken = animationAbsorb;

      //Create the physical Entity
      PhysicalEntityDefine.defineCharacter();
      myBodyDef = PhysicalEntityDefine.getBd();
      myBodyDef.type = BodyDef.BodyType.StaticBody;
      myFixtureDef = PhysicalEntityDefine.getFd();

      //这里设定盒子的大小
      PolygonShape shape = new PolygonShape();
      shape.setAsBox(1f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
      myFixtureDef.shape = shape;

      myBodyDef.position.set(this.getX() / ActConstants.worldSize_pAndPhysic, this.getY() / ActConstants.worldSize_pAndPhysic);

      this.world = world;

      mySimulation = world.createBody(myBodyDef);
      myFixture = mySimulation.createFixture(myFixtureDef);

      myFixture.setUserData(new UserData(actorId, "brokenBridge"));

      this.name = name;
      ActConstants.publicInformation.put(name, this);

   }

   @Override
   public void act(float delta) {
      super.act(delta);

      statetime += delta;

      if(state == true) currentFrame = (TextureRegion)wait.getKeyFrame(statetime, true);

      else currentFrame = (TextureRegion) broken.getKeyFrame(statetime, true);

   }
   @Override
   public void draw(Batch batch, float parentAlpha) {
      super.draw(batch, parentAlpha);

      batch.draw(currentFrame, (mySimulation.getPosition().x - 0.7f)* 50f, (mySimulation.getPosition().y - 0.45f)*50f);

   }

   @Override
   public boolean remove() {
      ActConstants.publicInformation.remove(name);
      return super.remove();
   }

   //To remove the physical body
   public void removeBody()
   {
      ActConstants.BodyDeleteList.add(mySimulation);
   }

}
