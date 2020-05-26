package com.how2java.pojo;

import java.io.Serializable;

public class Curtain extends Pojo implements Serializable {
    //窗帘id
    private int cid;

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getCid() {
        return cid;
    }
}
