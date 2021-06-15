package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.PhysicalActions.CreatePortal;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.BodyBuilder;
import com.mygdx.game.Tools.asset.AssetsLevel1;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.Tools.asset.AssetsUI;

public class HangedBalls extends Actor {
    String password;
    String pswEntered="";
    int pswLength;
    int enteredLength;

    float x;
    float y;
    long actorId;

    World world;
    String name;
    TextureRegion tr0;
    TextureRegion tr1;
    TextureRegion tr2;
    Image img0;
    Image img1;
    Image img2;

    Array<Body> bodies;
    Array<Vector2> positions;



    public HangedBalls(World world, float x, float y,String password,long actorId,String name){
        AssetsUI.instance.instance.init(new AssetManager());
       tr0 =(TextureRegion) AssetsLevel1.instance.jiemi.miZhong;
        tr1 =(TextureRegion) AssetsLevel1.instance.jiemi.miDuan;
        tr2 =(TextureRegion) AssetsLevel1.instance.jiemi.miChang;
        img0 =new Image(tr0);
        img1 =new Image(tr1);
        img2 =new Image(tr2);
//





        setPassword(password);
        this.world=world;
        this.name=name;
       this.x=x;
       this.y=y;
       this.actorId=actorId;
       createBalls();
        ActConstants.publicInformation.put(name,this);
    }

    public void setPassword(String password) {
        this.password = password;
        this.pswLength=password.length();
    }

    public void createBalls(){
        bodies= new Array<Body>();
        Array<Body> vertices=new Array<Body>();
        positions=new Array<>();

        Array<Vector2> vertexPos=new Array<>();
        vertexPos.add(new Vector2(216f, 69f));
        vertexPos.add(new Vector2(219f, 69f));
        vertexPos.add(new Vector2(222f, 69f));

        float[] ropeLength= {4f,3f,5f};




        for (Integer i = 0; i < 3; i++) {//注意这里改坐标的话三行代码都得改
            vertices.add(BodyBuilder.createBox(world, vertexPos.get(i).x , vertexPos.get(i).y, 0.05f, 0.05f, false, 0b10, "vertex"));
            positions.add(new Vector2(vertexPos.get(i).x  , vertexPos.get(i).y));
            bodies.add(BodyBuilder.createCircle(world, vertexPos.get(i).x , vertexPos.get(i).y-ropeLength[i], 0.5f, true,
                    actorId, name+i.toString()));
            RevoluteJointDef rDef= new RevoluteJointDef();
            rDef.initialize(vertices.get(i),bodies.get(i),positions.get(i));
//            rDef.enableLimit=true;
//            rDef.lowerAngle=-(float)Math.PI/12;
//            rDef.upperAngle=(float)Math.PI/12;
            world.createJoint(rDef);
        }


    }

    public void hit(String i){
        this.pswEntered+=i;
        this.enteredLength= this.pswEntered.length();
        if(enteredLength>=pswLength){
            if (password.equals(pswEntered.substring(enteredLength - pswLength))) {
                this.pswCorrect();
            }
        }
    }

    private void pswCorrect(){
        System.out.println("psw correct!");
//        HangedBalls balls= (HangedBalls) ActConstants.publicInformation.get("hangedBalls");
        PswDoor door = (PswDoor) ActConstants.publicInformation.get("pswDoor");
//这里加音效！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
        door.move();
//        MainCharacter mainCharacter = ((MainCharacter) ActConstants.publicInformation.get("MainCharacter"));
//        mainCharacter.mySimulation.setTransform(new Vector2(64f-1.2f,85f-23.4f), 0);
        CreatePortal createPortal = new CreatePortal();

        ActConstants.physicalActionList.add(createPortal);
        AudioManager.instance.play(AssetsLevel1.instance.sounds.success_Triggered);
        //从actcontants里获得横门的body让它消失
        //调用横门对象的draw还是act让它向左移
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(tr1,(bodies.get(0).getPosition().x-1f)*50,(bodies.get(0).getPosition().y-1.5f)*50,
                2.5f*50f,5*50f);
        batch.draw(tr2,(bodies.get(1).getPosition().x-1f)*50,(bodies.get(1).getPosition().y-1.5f)*50,
                2*50f,5*50f);
        batch.draw(tr0,(bodies.get(2).getPosition().x-1f)*50,(bodies.get(2).getPosition().y-1.5f)*50,
                2*50f,5*50f);
//        System.out.println(180*bodies.get(0).getAngle()/3.14);


    }

}
