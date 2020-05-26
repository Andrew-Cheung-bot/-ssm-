package com.how2java.pojo;

public class Pojo {
    //操作判断符(-1_delete,0_normal,1_add,2_modified)
    private int action;
    //设置pojo的开关状态
    private int state;
    //设置pojo的默认温度
    private Float Stem;
    //设置pojo的默认湿度
    private Float Shumidity;

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public void setShumidity(Float shumidity) {
        Shumidity = shumidity;
    }

    public void setStem(Float stem) {
        Stem = stem;
    }

    public Float getShumidity() {
        return Shumidity;
    }

    public Float getStem() {
        return Stem;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
