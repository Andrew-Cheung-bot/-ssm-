package com.smarthousehold.pojo;

import java.io.Serializable;

public class Curtain extends Pojo implements Serializable {
    //窗帘id
    private String cid;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
