package com.how2java.pojo;

public class User {

    private String username;
    //用户姓名
    private String email;
    //用户邮箱
    private String pwd;
    //用户密码
    private String activate;
    //激活状态，Y代表激活，N代表未激活
    private String code;
    //激活码（要求唯一）
    private String permission;
    //权限
    public String getEmail() {
        return email;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
