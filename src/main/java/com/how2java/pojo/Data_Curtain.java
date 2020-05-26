package com.how2java.pojo;

import java.io.Serializable;

public class Data_Curtain extends Data implements Serializable {
    //窗帘id
    private int cid;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
