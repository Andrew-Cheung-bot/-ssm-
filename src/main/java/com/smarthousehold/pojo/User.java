package com.smarthousehold.pojo;

public class User {

    private String username;
    //用户姓名
    private String email;
    //用户邮箱
    private String password;
    //用户密码
    private String activate;
    //激活状态，Y代表激活，N代表未激活
    private String code;
    //激活码（要求唯一）
    private String rolename;

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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
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

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
