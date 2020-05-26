package com.how2java.pojo;

import java.io.Serializable;

public class Fan extends Pojo implements Serializable {
    //风扇id
    private int fid;

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getFid() {
        return fid;
    }
}
