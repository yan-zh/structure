package com.mygdx.game;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class MyInputListener1 extends InputListener {
    MainCharacter mainCharacter;
    MyInputListener1(){
    }

    public boolean keyDown(InputEvent event, int keycode) {
        System.out.println("a");


        return super.keyUp(event, keycode);
        //return false;//不知道干嘛的
    }

    @Override
    public boolean keyUp(InputEvent event, int keycode) {



        return super.keyUp(event, keycode);
    }

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {//这里鼠标位置应该是像素个数，在desktopluncher里设定1900,1000的情况下基本和最右边是1900，最上边是100
        //当对话框调整的时候，在最左边和最上边的最大值会相应改变
        //System.out.println(x+"   "+y);

        return super.mouseMoved(event, x, y);
    }
}
