package com.smarthousehold.mapper;

import com.smarthousehold.pojo.User;
import com.smarthousehold.pojo.UserInfo;

import java.util.List;

/**
 * @Auther:胡坚涛
 * @Data:2020/05/25/21:11
 */
public interface UserMapper {
    User findByUsername(String username);

    void addUser(User user);

    User findByCode(String code);

    void updateStatus(User user);

    void updateUser(User user);

    void updateforgetcode(User user);

    List<User> findAll();

    void delUserByUsername(String username);

    void editUserByUsername(User user);

    List<String> searchAutoPrompt(String key);

    List<User> searchByUsername(String string);

    void delUser_Curtain(String username);

    void delUser_fan(String username);

    void addUserCurtain(String username, String cid);

    void addUserFan(String username, String fid);

    UserInfo findDetail(String username);
}
