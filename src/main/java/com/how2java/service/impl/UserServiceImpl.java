package com.how2java.service.impl;

import com.how2java.mapper.UserMapper;
import com.how2java.pojo.User;
import com.how2java.service.UserService;
import com.how2java.util.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Auther:胡坚涛
 * @Data:2020/05/25/21:10
 */
@Service("userService")
@Transactional
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
     * 修改用户密码
     * @param user
     * @return
     */
    @Override
    public boolean update(User user) {
        User u=null;
        u=userMapper.findByUsername(user.getUsername());
        //判断数据库中是否存在用户名
        if(u==null){
            //用户名存在，修改失败
            return false;
        }
        //使用bcrypt加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        u.setPassword(passwordEncoder.encode(user.getPassword()));

        //放入经过加密的密码
        userMapper.updateUser(u);

        String content= "您的用户名为"+user.getUsername()+"的账号密码已经变更，请确认是否为正常改动!";
        MailUtils.sendMail(u.getEmail(),content,"相关账号密码发生修改");

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
     * @param //user
     * @return
     */
    /*
    @Override
    public User login(User user) {
        return userMapper.findByUsernameAndPassword(user);
    }
    */

    @Override
    public UserDetails loadUserByUsername(String username) throws AuthenticationException {
            User user = userMapper.findByUsername(username);
            org.springframework.security.core.userdetails.User security_user =
                    new org.springframework.security.core.userdetails.User(
                            user.getUsername(),
                            user.getPassword(),
                            (user.getActivate().equals("Y")) ? true : false,
                            true,
                            true,
                            true,
                            getAuthority(user));
            return security_user;
    }

    public List<SimpleGrantedAuthority> getAuthority(User user){
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_"+user.getRolename()));
        return list;
    }
}
