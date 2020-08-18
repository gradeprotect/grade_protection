package com.das.controller;

import com.das.entity.User;
import com.das.service.UserService;
import com.das.utils.JwtUtils;
import com.das.utils.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tim
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody User user){
        user.toString();
        Map<String, Object> map = new HashMap<>(16);
        try{
            User userDB = userService.login(user);
            Map<String,String> payload = new HashMap<>(16);
            payload.put("name",userDB.getName());
            payload.put("email",userDB.getEmail());
            payload.put("telephone",userDB.getTelephone());
            //生成JWT令牌
            String token = JwtUtils.getToken(payload);
            Map<String, Object> meta = State.reState("登录成功", 200);
            map.put("meta",meta);
            map.put("data",userDB);
            //响应token
            map.put("token",token);
        }catch (Exception e){
            map.put("state",false);
            map.put("msg",e.getMessage());
        }
        return map;
    }
}