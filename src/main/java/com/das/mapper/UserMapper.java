package com.das.mapper;

import com.das.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * 用户列表
     * @param num 第几页
     * @param pageSize 每页显示条数
     * @return 用户集合
     */
    List<User> getUserList(Integer num,Integer pageSize);
    /**
     * 用户修改
     * @param user 修改的信息
     */
    public void updateUser(User user);

    /**
     * 通过用户id查找用户
     * @param id
     * @return
     */
    User getUserById(Integer id);

    /**
     * 修改密码
     * @param id 用户id
     * @param password 修改后的密码
     * @return
     */
    void updateUserPassword(String password,Integer id);

    /**
     * 添加用户
     * @param user 添加用户的信息
     */
    void insertUser(User user);
}
