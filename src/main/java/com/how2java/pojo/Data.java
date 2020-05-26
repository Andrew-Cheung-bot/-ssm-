package com.how2java.pojo;

public class Data {
    //记录温湿度的时间(由数据库的now()函数自动填入)
    public String settime;
    //温度
    public Float tem;
    //湿度
    public Float humidity;

    public void setSettime(String settime) {
        this.settime = settime;
    }

    public String getSettime() {
        return settime;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    public void setTem(Float tem) {
        this.tem = tem;
    }

    public Float getHumidity() {
        return humidity;
    }

    public Float getTem() {
        return tem;
    }

}
