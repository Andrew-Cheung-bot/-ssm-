package com.smarthousehold.pojo;

import java.io.Serializable;

public class Data_Fan extends Data implements Serializable {
    //风扇id
    private String fid;

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFid() {
        return fid;
    }
}

