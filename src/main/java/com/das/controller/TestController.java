package com.das.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.das.utils.JwtUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tim
 */
@RestController
public class TestController {
    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody String str){
        Map<String, Object> map = new HashMap<>(16);
        try{
            Map<String,String> payload = new HashMap<>(16);
            payload.put("name",str);
            //生成JWT令牌
            String token = JwtUtils.getToken(payload);
            map.put("state",true);
            map.put("msg","登录成功");
            //响应token
            map.put("token",token);
        }catch (Exception e){
            map.put("state",false);
            map.put("msg",e.getMessage());
        }
        return map;
    }
    @PostMapping("/user/test")
    public Map<String,Object> test(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        //处理自己的逻辑
        String token = request.getHeader("token");
        DecodedJWT inf = JwtUtils.getTokenInfo(token);
        System.out.println("用户名："+inf.getClaim("name").asString());
        map.put("msg","成功");
        return map;
    }
}
