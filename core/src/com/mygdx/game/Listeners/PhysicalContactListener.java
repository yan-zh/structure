package com.mygdx.game.Listeners;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.abstraction.UserData;

public class PhysicalContactListener implements ContactListener{
    @Override
    public void beginContact(Contact contact) {//出现新接触点
        //通过把这个my contact listener 添加到word里，进行监听，可以在函数里通过contact获得碰撞对象的fixturedefine（里面可以通过usedata设置编号来区分对象）
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

//        long faData = (long) fa.getUserData();
//        long fbData = (long) fb.getUserData();
//
//        long result = faData+fbData;

        UserData faData = (UserData)fa.getUserData();
        UserData fbData = (UserData)fb.getUserData();

        long result = faData.contactId+fbData.contactId;

        System.out.println(Integer.toBinaryString((int)result));
        if(ActConstants.contactList.get(result)!=null){
            ActConstants.contactList.get(result).react(faData,fbData);
        }
        if(ActConstants.contactList.get(faData.contactId)!=null){
            ActConstants.contactList.get(faData.contactId).react(faData,fbData);
        }
        if(ActConstants.contactList.get(fbData.contactId)!=null){
            ActConstants.contactList.get(fbData.contactId).react(faData,fbData);
        }



        //之后也可以获得碰撞点坐标等
        //还可以把刚体设置为传感器，就是会检测到碰撞但是物理世界里不与其它刚体产生相互作用
    }

    @Override
    public void endContact(Contact contact) {//新接触点消失
//        Fixture fa = contact.getFixtureA();
////        Fixture fb = contact.getFixtureB();
////
////        Object faData = fa.getUserData();
////        Object fbData = fb.getUserData();
////
//////两个指定物体碰撞后
////    if( ((faData.equals("wall")|| fbData.equals("wall"))&&(faData.equals("main character") || fbData.equals("main character")))){
////
////        PublicData.MainCharacterState.replace("onGround",true);
////    }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {//aabb框之间出现新接触点

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {//aabb框之间的新接触点消失

    }
}
