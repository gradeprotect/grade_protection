package com.das.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.das.entity.User;
import com.das.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 许文滨
 * @date 2020-8-19
 */
public class JudgAuthority {

    /**
     * 判断用户是否为超级管理员
     * @param userService UserService
     * @param token token
     * @return boolean
     */
    public static boolean isSuperAdmin(UserService userService,String token){
        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
        int id = Integer.parseInt(tokenInfo.getClaim("id").asString());
        User user = userService.getUserById(id);
        if (user.getAuthority() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断用户是否有管理员权限
     * @param userService UserService
     * @param token token
     * @return boolean
     */
    public static boolean isAdmin(UserService userService,String token){
        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
        int id = Integer.parseInt(tokenInfo.getClaim("id").asString());
        User user = userService.getUserById(id);
        if (user.getAuthority() == 0 || user.getAuthority() == 1) {
            return true;
        } else {
            return false;
        }
    }
}
