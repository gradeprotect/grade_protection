package com.das.service;

import com.das.entity.User;
import com.das.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
