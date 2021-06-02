package com.mygdx.game.Tools;

public class MyVector {
    public static float[] getStandardVector(float x1, float y1, float x2, float y2){
        float[] begin = new float[2];
        begin[0]=x1;
        begin[1]=y1;

        float[] d=new float[2];
        d[0]=x2-begin[0];
        d[1]=y2-begin[1];

        float length=(float) Math.sqrt((d[0]*d[0]+d[1]*d[1]));

        d[0]=d[0]/length;
        d[1]=d[1]/length;

        return d;
    }

    public static float countAngle(float x1, float y1, float x2, float y2){
        double normA = Math.sqrt(x1*x1+y1*y1);
        double normB = Math.sqrt(x2*x2+y2*y2);
        double amb = x1*x2+y1*y2;
        return (float)(amb/(normA*normB));
    }

    public static float[] add(float x1, float y1, float x2, float y2){
        float[] d = new float[2];

        d[0] = x1+x2;
        d[1] = y1+y2;

        return d;
    }

    public static float[] add(float[] d1, float[] d2){

        float[] result = new float[2];
        result[0] = d1[0]+d2[0];
        result[1] = d1[1]+d2[1];

        return result;
    }

    public static float[] multiple(float[] d1, float x){
        float[] d2 = new float[2];
        d2[0] = d1[0]*x;
        d2[1] = d1[1]*x;
        return d2;
    }
}
