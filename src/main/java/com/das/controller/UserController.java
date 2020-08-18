package com.das.controller;

import com.das.entity.Page;
import com.das.entity.User;
import com.das.service.UserService;
import com.das.utils.JwtUtils;
import com.das.utils.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        Map<String, Object> map = new HashMap<>(4);
        try{
            User userDB = userService.login(user);
            Map<String,String> payload = new HashMap<>(16);
            payload.put("name",userDB.getName());
            payload.put("email",userDB.getEmail());
            payload.put("telephone",userDB.getTelephone());
            //生成JWT令牌
            String token = JwtUtils.getToken(payload);
            map = State.packet(userDB,"登录成功", 200);
            //响应token
            map.put("token",token);
        }catch (Exception e){
            map.put("state",false);
            map.put("msg",e.getMessage());
        }
        return map;
    }
    @GetMapping
    public Map<String,Object> getUserList(Integer pageNum,Integer pageSize){
        Page<User> page = new Page(pageNum,pageSize);

        return null;
    }
}