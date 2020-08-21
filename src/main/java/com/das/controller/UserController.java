package com.das.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.das.entity.Page;
import com.das.entity.User;
import com.das.service.UserService;
import com.das.utils.JwtUtils;
import com.das.utils.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tim
 */
@RestController
@CrossOrigin(maxAge = 360000)
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
            payload.put("id",Integer.toString(userDB.getId()));
            payload.put("name",userDB.getName());
            payload.put("email",userDB.getEmail());
            payload.put("telephone",userDB.getTelephone());
            //生成JWT令牌
            String token = JwtUtils.getToken(payload);
            map = State.packet(userDB,"登录成功", 200);
            //响应token
            map.put("token",token);
        }catch (Exception e){
            map = State.packet(null, "用户名或密码错误", 422);
        }
        return map;
    }
    @GetMapping
    public Map<String,Object> getUserList(Integer pagenum,Integer pagesize){
        Page<User> page = new Page(pagenum,pagesize);
        List<User> userList = userService.getUserList(pagesize * (pagenum - 1), pagesize);
        Integer allUserCount = userService.getAllUserCount();
        page.setTotal(allUserCount);
        page.setPageNum(pagenum);
        page.setPageSize(pagesize);
        page.setRows(userList);
        Map<String, Object> packet = State.packet(page, "获取成功", 200);
        return packet;
    }
    @PutMapping("/update")
    public Map<String, Object> updateUser(@RequestBody User user,@RequestHeader("Authorization") String token) {
        Map<String, Object> map = new HashMap<>(16);
        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
        String id = tokenInfo.getClaim("id").asString();
        User userLogin = userService.getUserById(Integer.parseInt(id));
        User userOld = userService.getUserById(user.getId());
        System.out.println("传过来的user:"+user);
        System.out.println("数据修改前的user:"+userOld);
        System.out.println("当前登录的user:"+userLogin);
        System.out.println(user.getAuthority().equals(userOld.getAuthority()));
        System.out.println(userOld.getAuthority()+":::"+userLogin.getAuthority());
        if (!user.getAuthority().equals(userOld.getAuthority())) {
            System.out.println("===========");
            if (userService.getUserById(Integer.parseInt(id)).getAuthority() != 0) {
                map = State.packet(null, "您不是超级管理员，不能修改用户权限", 403);
            }
            if (userOld.getAuthority() == 0) {
                map = State.packet(null, "您不能修改超级管理员的权限", 403);
            }
            return map;
        }
        if(userOld.getAuthority()<Integer.parseInt(id)){
            map = State.packet(null, "您不能修改权限比你高的用户信息", 403);
            return map;
        }
        userService.updateUser(user);
        User userDB = userService.getUserById(user.getId());
        map = State.packet(userDB, "修改成功", 200);
        return map;
    }
    @PutMapping("/update/password")
    public Map<String,Object> updateUserPassword(@RequestBody User user,@RequestHeader("Authorization") String token){
        Map<String, Object> map = new HashMap<>(16);
        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
        int id = Integer.parseInt(tokenInfo.getClaim("id").asString());
        if(user.getAuthority()<id){
            map = State.packet(null, "您不能修改权限比你高的用户信息", 403);
            return map;
        }
        userService.updateUserPassword(user.getPassword(),user.getId());
        User userDB = userService.getUserById(user.getId());
        map = State.packet(userDB, "修改成功", 200);
        return map;
    }
    @PostMapping
    public Map<String,Object> insertUser(@RequestBody User user,@RequestHeader("Authorization") String token){
        Map<String, Object> map = new HashMap<>(16);
        userService.insertUser(user);
        User userDB = userService.getUserById(user.getId());
        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
        int id = Integer.parseInt(tokenInfo.getClaim("id").asString());
        User userLogin = userService.getUserById(id);
        if(userLogin.getAuthority()!=0&&(userLogin.getAuthority()>=user.getAuthority())){
            map = State.packet(userDB, "您不能添加权限和你相同以及权限比你高的用户", 403);
            return map;
        }
        map = State.packet(userDB, "添加成功", 201);
        return map;
    }
    @GetMapping("/{id}")
    public Map<String,Object> getUserById(@PathVariable("id") Integer id){
        User userDB = userService.getUserById(id);
        Map<String, Object> map = State.packet(userDB, "获取成功", 200);
        return map;
    }
    @GetMapping("/name")
    public Map<String,Object> getUserByName(String keyword,Integer pagenum,Integer pagesize){
        Page<User> page = new Page<>();
        List<User> userByName = userService.getUserByName(keyword,pagesize*(pagenum-1),pagesize);
        page.setPageNum(pagenum);
        page.setPageSize(pagesize);
        page.setTotal(userService.getUserByNameCount(keyword));
        page.setRows(userByName);
        Map<String, Object> map = State.packet(page, "获取成功", 200);
        return map;
    }
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteUserById(@PathVariable("id") Integer id,@RequestHeader("Authorization") String token){
        Map<String, Object> map = new HashMap<>(16);
        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
        int loginUserId = Integer.parseInt(tokenInfo.getClaim("id").asString());
        //根据token里的ID获取对应用户
        User userDB = userService.getUserById(loginUserId);
        Integer result = userService.deleteUserById(id,userDB.getAuthority());
        System.out.println(userDB.getAuthority()+"-"+id);
        if(result==1){
            map = State.packet(null,"删除成功", 204);
        }
        else{
            map = State.packet(null,"删除失败", 403);
        }
        return map;
    }
}