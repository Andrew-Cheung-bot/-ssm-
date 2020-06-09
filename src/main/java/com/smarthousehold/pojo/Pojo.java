package com.smarthousehold.pojo;

public class Pojo {
    //操作判断符(0_normal,1_add,2_modified_state,3_modified_StemandShumidity,4_delete)
    private String action;
    //设置pojo的开关状态
    private String state;
    //设置pojo的默认温度
    private String Stem;
    //设置pojo的默认湿度
    private String Shumidity;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setStem(String stem) {
        Stem = stem;
    }

    public void setShumidity(String shumidity) {
        Shumidity = shumidity;
    }

    public String getAction() {
        return action;
    }

    public String getShumidity() {
        return Shumidity;
    }

    public String getStem() {
        return Stem;
    }
}
