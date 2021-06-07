package com.mygdx.game.Listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.*;
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

        synchronized (ActConstants.publicInformationLock){
            if(ActConstants.contactList.get(result)!=null){
                ActConstants.contactList.get(result).react(faData,fbData);
            }
            if(ActConstants.contactList.get(faData.contactId)!=null){
                ActConstants.contactList.get(faData.contactId).react(faData,fbData);
            }
            if(ActConstants.contactList.get(fbData.contactId)!=null){
                ActConstants.contactList.get(fbData.contactId).react(fbData,faData);
            }
        }

        if(((UserData)(contact.getFixtureA().getUserData())).contactId == ActConstants.portalID || ((UserData)(contact.getFixtureB().getUserData())).contactId == ActConstants.portalID)
        {
            Portal portal =((Portal)ActConstants.publicInformation.get("Portal"));
            portal.triggerState = true;
            System.out.println("tRIGGER TRUE");

        }

        if(((UserData)(contact.getFixtureA().getUserData())).contactId == ActConstants.switchID || ((UserData)(contact.getFixtureB().getUserData())).contactId == ActConstants.switchID )
        {
            rotateSwitch rotateSwitch =((rotateSwitch)ActConstants.publicInformation.get("rotateSwitch"));
            rotateSwitch.triggerState = true;
            System.out.println("tRIGGER true");
        }

        if(((UserData)(contact.getFixtureA().getUserData())).contactId == ActConstants.SensorID || ((UserData)(contact.getFixtureB().getUserData())).contactId == ActConstants.SensorID )
        {
            Sensor sensor =((Sensor)ActConstants.publicInformation.get("Sensor"));
            sensor.triggerState = true;
            System.out.println("Sensor is tRIGGER true");
            laserTransmitter laserTransmitter = (laserTransmitter) ActConstants.publicInformation.get("laserTransmitter");
            System.out.println("IT is " + laserTransmitter.triggerState);


            if(ActConstants.SensorCount==1) {
                Timer timer = new Timer();
                Timer.Task timerTask = new Timer.Task() {
                    @Override

                    public void run() {

                        laserTransmitter laserTransmitter = (laserTransmitter) ActConstants.publicInformation.get("laserTransmitter");
                        laserTransmitter.emmit();
                        System.out.println("sssdfsdkjfjslkfjdsl");
                    }

                };
                timer.scheduleTask(timerTask, 1, 2, 5);// 0s之后执行，每次间隔1s，执行20次。
            }
            ActConstants.SensorCount = 2;
//            laserTransmitter laserTransmitter = ((laserTransmitter)ActConstants.publicInformation.get("laserTransmitter"));
//            laserTransmitter.emmit();
        }



        //之后也可以获得碰撞点坐标等
        //还可以把刚体设置为传感器，就是会检测到碰撞但是物理世界里不与其它刚体产生相互作用
    }

    @Override
    public void endContact(Contact contact) {//新接触点消失

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        UserData faData = (UserData) fa.getUserData();
        UserData fbData = (UserData) fb.getUserData();


        if(faData.contactId==ActConstants.tongueMonsterID&&fbData.contactId==ActConstants.mainCharacterID){
            ((TongueMonster)ActConstants.publicInformation.get(faData.nameInPublicInformation)).setContact(false);
        }else if(fbData.contactId==ActConstants.tongueMonsterID&&faData.contactId==ActConstants.mainCharacterID){
            ((TongueMonster)ActConstants.publicInformation.get(fbData.nameInPublicInformation)).setContact(false);
        }else if(faData.contactId==ActConstants.blowerID&&fbData.contactId==ActConstants.mainCharacterID){
            ActConstants.MainCharacterState.replace("blow",false);
        }else if(fbData.contactId==ActConstants.blowerID&&faData.contactId==ActConstants.mainCharacterID){
            ActConstants.MainCharacterState.replace("blow",false);
        }




////
//////两个指定物体碰撞后
////    if( ((faData.equals("wall")|| fbData.equals("wall"))&&(faData.equals("main character") || fbData.equals("main character")))){
////
////        PublicData.MainCharacterState.replace("onGround",true);
////    }
        if(((UserData)(contact.getFixtureA().getUserData())).contactId == ActConstants.portalID || ((UserData)(contact.getFixtureB().getUserData())).contactId == ActConstants.portalID)
        {
            Portal portal =((Portal)ActConstants.publicInformation.get("Portal"));
            portal.triggerState = false;
            System.out.println("tRIGGER fALUSE");
        }

        if(((UserData)(contact.getFixtureA().getUserData())).contactId == ActConstants.switchID || ((UserData)(contact.getFixtureB().getUserData())).contactId == ActConstants.switchID)
        {
            rotateSwitch rotateSwitch =((rotateSwitch)ActConstants.publicInformation.get("rotateSwitch"));
            rotateSwitch.triggerState = false;

            System.out.println("tRIGGER fALUSE");
        }

        if(((UserData)(contact.getFixtureA().getUserData())).contactId == ActConstants.SensorID || ((UserData)(contact.getFixtureB().getUserData())).contactId == ActConstants.SensorID )
        {
            Sensor sensor =((Sensor)ActConstants.publicInformation.get("Sensor"));
            sensor.triggerState = false;
            System.out.println("Sensor is tRIGGER false");
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {//aabb框之间出现新接触点
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        UserData faData = (UserData)fa.getUserData();
        UserData fbData = (UserData)fb.getUserData();
        long result = faData.contactId+fbData.contactId;
        if(result==(0b1+0b1000000000000000000000000000000)){
            ThinSurface srf= (ThinSurface) ActConstants.publicInformation.get("thinSurface");
            MainCharacter mainCha= (MainCharacter) ActConstants.publicInformation.get("MainCharacter");
            if(mainCha.getPositionInSimulation().y-(mainCha.getHeight()/2f)
                    >= srf.y+ (srf.getHeight()/2f)){
                if(Gdx.input.isKeyPressed(Input.Keys.S)== true){

                    contact.setEnabled(false);
                }
            }
            if(mainCha.getPositionInSimulation().y-(mainCha.getHeight()/2f)
                    < srf.y+ (srf.getHeight()/2f)){
                contact.setEnabled(false);
            }



        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {//aabb框之间的新接触点消失

    }
}
