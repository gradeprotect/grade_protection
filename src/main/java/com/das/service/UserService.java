package com.das.service;

import com.das.entity.User;
import com.das.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Tim
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User login(User user){
        return userMapper.login(user);
    }
    public List<User> getUserList(Integer num,Integer pageSize){
        return userMapper.getUserList(num,pageSize);
    }
    public void updateUser(User user){
        userMapper.updateUser(user);
    }
    public User getUserById(Integer id){
        return userMapper.getUserById(id);
    }
    public void updateUserPassword(String password,Integer id){
        userMapper.updateUserPassword(password,id);
    }
    public void insertUser(User user){
        userMapper.insertUser(user);
    }
    public Integer getAllUserCount(){
        return userMapper.getAllUserCount();
    }
    public List<User> getUserByName(String keyword,Integer num,Integer pageSize){
        return userMapper.getUserByName(keyword,num,pageSize);
    }
    public Integer getUserByNameCount(String keyword){
        return userMapper.getUserByNameCount(keyword);
    }
    public Integer deleteUserById(int id){ return userMapper.deleteUserById(id);}
}
