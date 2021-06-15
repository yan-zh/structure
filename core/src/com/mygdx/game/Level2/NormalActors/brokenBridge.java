package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.PhysicalActions.DeletePhysicalEntity;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.UserData;

public class brokenBridge extends Actor {
   Body mySimulation;
   FixtureDef myFixtureDef;
   BodyDef myBodyDef;
   Fixture myFixture;

   float hx;
   float hy;

   TextureRegion wait;
   TextureRegion broken;

   TextureRegion currentFrame;

   public boolean state = true;

   float statetime;

   String name;

   World world;

   public brokenBridge(TextureRegion animationWait, TextureRegion animationAbsorb, int x, int y, float hx, float hy, long actorId, World world, String name)
   {
      //Set position
      this.setX(x);
      this.setY(y);

      this.hx = hx;
      this.hy = hy;
      //Set the animation of the wait state and absorb
      this.wait = (TextureRegion) animationWait;
      this.broken = (TextureRegion)animationAbsorb;

      //Create the physical Entity
      PhysicalEntityDefine.defineCharacter();
      myBodyDef = PhysicalEntityDefine.getBd();
      myBodyDef.type = BodyDef.BodyType.StaticBody;
      myFixtureDef = PhysicalEntityDefine.getFd();

      //这里设定盒子的大小
      PolygonShape shape = new PolygonShape();
      shape.setAsBox(hx/ ActConstants.worldSize_shapeAndPhysics,hy/ ActConstants.worldSize_shapeAndPhysics);
      myFixtureDef.shape = shape;

      myBodyDef.position.set(this.getX() , this.getY());

      this.world = world;

      mySimulation = world.createBody(myBodyDef);
      mySimulation.setGravityScale(1);
      myFixture = mySimulation.createFixture(myFixtureDef);

      myFixture.setUserData(new UserData(actorId, name));

      this.name = name;
      ActConstants.publicInformation.put(name, this);

   }

   @Override
   public void act(float delta) {
      super.act(delta);

      statetime += delta;

      if(state == true) currentFrame = wait;

      else currentFrame = broken;

   }
   @Override
   public void draw(Batch batch, float parentAlpha) {
      super.draw(batch, parentAlpha);

         if(this.name == "brokenIce")
         {
            batch.draw(currentFrame, (mySimulation.getPosition().x - (hx/2)-3f)* 50f, (mySimulation.getPosition().y - hy/2)*50f);
         }

     if(this.name == "brokenBridge") batch.draw(currentFrame, (mySimulation.getPosition().x - (hx/2))* 50f, (mySimulation.getPosition().y -(hy/2)-3f)*50f);



   }

   @Override
   public boolean remove() {
      ActConstants.publicInformation.remove(name);
      return super.remove();
   }

   //To remove the physical body
   public void removeBody()
   {
      DeletePhysicalEntity deletePhysicalEntity1 = new DeletePhysicalEntity();
      deletePhysicalEntity1.deleteBody(mySimulation,world);
      ActConstants.physicalActionList.add(deletePhysicalEntity1);
   }

}
