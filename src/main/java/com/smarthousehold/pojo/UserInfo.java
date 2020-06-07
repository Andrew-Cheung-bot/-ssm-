package com.smarthousehold.pojo;

import java.util.List;

/**
 * @Auther:胡坚涛
 * @Data:2020/06/07/21:11
 */
public class UserInfo extends User {
    public List<Fan> fans;
    public List<Curtain> curtains;

    public List<Fan> getFans() {
        return fans;
    }

    public void setFans(List<Fan> fans) {
        this.fans = fans;
    }

    public List<Curtain> getCurtains() {
        return curtains;
    }

    public void setCurtains(List<Curtain> curtains) {
        this.curtains = curtains;
    }
}
