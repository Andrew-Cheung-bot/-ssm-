package com.smarthousehold.pojo;

import java.io.Serializable;

public class Fan extends Pojo implements Serializable {
    //风扇id
    private String fid;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
}
