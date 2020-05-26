package com.how2java.pojo;

import java.io.Serializable;

public class Data_Fan extends Data implements Serializable {
    //风扇id
    private int fid;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }
}

