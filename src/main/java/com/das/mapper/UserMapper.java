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

    /**
     * 查询用户总数
     * @return 用户总数
     */
    Integer getAllUserCount();

    /**
     *
     * 通过用户名模糊查询
     * @param num 第几页
     * @param pageSize 每页数量
     * @param keyword 关键字
     * @return 复合条件的用户
     */
    List<User> getUserByName(String keyword,Integer num,Integer pageSize);

    /**
     * 通过用户名模糊查询得到的总条数
     * @param keyword 关键字
     * @return 总数
     */
    Integer getUserByNameCount(String keyword);
    /**
     * 通过Id对用户的isdelete字段修改，进行伪删除。
     * @param id 被删除的id
     * @param authority 删除者的权限
     * @return
     */
    Integer deleteUserById(Integer id,Integer authority);

    /**
     * 通过id获取用户的email
     * @param id Integer
     * @return email
     */
    String getEmailById(Integer id);

    /**
     * 获取所有管理员email
     * @return List<String>
     */
    List<String> getEmails();
}
