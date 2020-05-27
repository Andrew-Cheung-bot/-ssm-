package com.how2java.pojo;

public class Data {
    //记录温湿度的时间(由数据库的now()函数自动填入)
    public String settime;
    //温度
    public int tem;
    //湿度
    public int humidity;

    public void setSettime(String settime) {
        this.settime = settime;
    }

    public String getSettime() {
        return settime;
    }

    public void setTem(int tem) {
        this.tem = tem;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getTem() {
        return tem;
    }
}
