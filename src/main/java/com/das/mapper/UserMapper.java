package com.das.mapper;

import com.das.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Tim
 */
@Mapper
public interface UserMapper {
    /**
     * 用户登录
     * @param user 前端传来的用户名和密码
     * @return 完整的User
     */
    User login(User user);
}
