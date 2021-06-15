package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.Level2.NormalActors.MonsterA;
import com.mygdx.game.Tools.asset.AssetsUI;
import com.mygdx.game.abstraction.BulletSkill;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class BulletAndMonsterA implements ContactReaction {

    public static long contactID1 = ActConstants.MonsterID+ActConstants.iceBulletID;
    public static long contactID2 = ActConstants.MonsterID+ActConstants.woodBulletID;
    public static long contactID3 = ActConstants.MonsterID+ActConstants.windBulletID;
    public static long contactID4 = ActConstants.MonsterID+ActConstants.sandBulletID;

    public BulletAndMonsterA() {
        ActConstants.contactList.put(contactID1,this);
        ActConstants.contactList.put(contactID2,this);
        ActConstants.contactList.put(contactID3,this);
        ActConstants.contactList.put(contactID4,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {
        synchronized (ActConstants.monsterLock) {
            if (userData1.contactId == ActConstants.MonsterID) {

                MonsterA monsterA = (MonsterA) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
                BulletSkill bulletSkill = (BulletSkill) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
                if(monsterA!=null){

                    if(bulletSkill.actorId==ActConstants.woodBulletID){
                        AssetsUI.instance.addLives(1);//
                    }else if(bulletSkill.actorId==ActConstants.windBulletID){
                        monsterA.repulse(bulletSkill.mySimulation.getPosition().x,bulletSkill.mySimulation.getPosition().y);
                    }

                    boolean isAlive = monsterA.damage(userData2.contactId, bulletSkill.getDamage());
                    if (!isAlive) {
                        monsterA.remove = true;
                    }
                }

            } else {
                MonsterA monsterA = (MonsterA) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
                BulletSkill bulletSkill = (BulletSkill) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
                if(monsterA!=null){

                    if(bulletSkill.actorId==ActConstants.woodBulletID&&ActConstants.health<=20){
                        ActConstants.health++;
                    }else if(bulletSkill.actorId==ActConstants.windBulletID){
                        monsterA.repulse(bulletSkill.mySimulation.getPosition().x,bulletSkill.mySimulation.getPosition().y);
                    }

                    boolean isAlive = monsterA.damage(userData1.contactId, bulletSkill.getDamage());
                    if (!isAlive) {
                        monsterA.remove = true;
                    }
                }

            }
        }

    }
}
