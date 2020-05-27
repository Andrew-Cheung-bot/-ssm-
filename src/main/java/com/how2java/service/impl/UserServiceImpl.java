package com.how2java.service.impl;

import com.how2java.mapper.UserMapper;
import com.how2java.pojo.User;
import com.how2java.service.UserService;
import com.how2java.util.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @Auther:胡坚涛
 * @Data:2020/05/25/21:10
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        User u=null;
        u=userMapper.findByUsername(user.getUsername());
        //判断数据库中是否存在用户名
        if(u!=null){
            //用户名存在，注册失败
            return false;
        }
        //2.保存用户信息
        //2.1设置激活码，唯一字符串
        user.setCode(UUID.randomUUID().toString().replace("-",""));
        //2.2设置激活状态
        user.setActivate("N");
        userMapper.addUser(user);

        //3.激活邮件发送，邮件正文？

        String content="<a href='http://129.204.232.202:8080/ssm/User/activeUser?code="+user.getCode()+"'>点击激活【智能家居系统】</a><br/>";
         content+="或复制链接到浏览器打开:http://129.204.232.202:8080/ssm/User/activeUser?code="+user.getCode();
        /*String content="<a href='http://localhost:8080/ssm/User/activeUser?code="+user.getCode()+"'>点击激活【智能家居系统】</a><br/>";
        content+="或复制链接到浏览器打开:http://localhost:8080/ssm/User/activeUser?code="+user.getCode();*/

        MailUtils.sendMail(user.getEmail(),content,"激活邮件");

        return true;
    }

    /**
     * 激活用户
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        User user = userMapper.findByCode(code);
        if(user!=null){
            //修改激活状态
            userMapper.updateStatus(user);
            return true;
        }else {
            return false;
        }

    }

    /**
     * 登录账户
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return userMapper.findByUsernameAndPassword(user);
    }
}
