package com.myown.sso.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myown.common.service.RedisService;
import com.myown.sso.mapper.UserMapper;
import com.myown.sso.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/30 0030 15:24
 * @Description:
 */
@Service
public class UserService  {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Integer REDIS_TIME = 60*30;

    /**
     * 注册校验
     * @param param
     * @param type
     * @return
     */
    public Boolean check(String param, Integer type) {
        User record = new User();
        switch (type){
            case 1:
                record.setUsername(param);
                break;
            case 2:
                record.setPhone(param);
                break;
            case 3:
                record.setEmail(param);
                break;
            default:
                //参数有误
                return null;
        }
        return this.userMapper.selectOne(record)==null;
    }

    /**
     * 注册
     * @param user
     * @return
     */
    public Boolean doRegister(User user) {

        user.setId(null);
        user.setCreated(new Date());
        user.setUpdated(new Date());
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        return this.userMapper.insert(user)==1;
    }

    /**
     * 登陆验证
     * @param username
     * @param password
     * @return
     */
    public String doLogin(String username, String password) throws Exception {
        User record = new User();
        record.setUsername(username);
        User user = this.userMapper.selectOne(record);
        if (null == user) {
            //用户不存在
            return null;
        }
        if(!StringUtils.equals(DigestUtils.md5Hex(password),user.getPassword())){
            //密码错误
            return null;
        }
        //登陆成功，保存到redis
        String token = DigestUtils.md5Hex(username + System.currentTimeMillis());
        System.out.println("User:"+user);
        this.redisService.set("TOKEN_"+token,MAPPER.writeValueAsString(user),REDIS_TIME);
        return token;
    }


    public User queryUserByToken(String token) {
        String key = "TOKEN_" + token;
        String jsonData = this.redisService.get(key);
        if (StringUtils.isEmpty(jsonData)) {
            //登陆超时
            return null;
        }
        //重置redis中数据的生存时间
        this.redisService.expire(key,REDIS_TIME);
        try {
            return MAPPER.readValue(jsonData,User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
