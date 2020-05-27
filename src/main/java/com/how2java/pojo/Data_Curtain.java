package com.how2java.pojo;

import java.io.Serializable;

public class Data_Curtain extends Data implements Serializable {
    //窗帘id
    private String cid;

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCid() {
        return cid;
    }
}
