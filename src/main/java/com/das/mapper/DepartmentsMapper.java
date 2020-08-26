package com.das.mapper;

import com.das.entity.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 许文滨
 * @date 2020-8-18
 */
@Mapper
public interface DepartmentsMapper {
    /**
     * 获取全部的部门信息
     * @param pagenum 当前页码
     * @param pagesize 每页条数
     * @return List<Department>
     */
    List<Department> findAll(Integer pagenum,Integer pagesize);

    /**
     * 通过部门名获取部门id
     * @param name String
     * @return Integer
     */
    Integer getIdByName(String name);

    /**
     * 获取所有部门信息
     * @return List<Department>
     */
    List<Department> getAll();

    /**
     * 根据id删除部门
     * @param id Integer
     */
    void deleteById(Integer id);

    /**
     * 获取全部的部门名字
     * @return List<Integer>
     */
    List<String> getNames();

    /**
     * 通过名字查询部门信息
     * @param name String
     * @param pagesize Integer
     * @param pagenum Integer
     * @return Department
     */
    List<Department> findByName(String name,Integer pagenum,Integer pagesize);

    /**
     * 通过名字计数
     * @param name String
     * @return Integer
     */
    Integer countByName(String name);

    /**
     * 统计所有部门数
     * @return Integer
     */
    Integer count();

    /**
     * 通过 id 查找部门
     * @param id int
     * @return Department
     */
    Department findById(int id);

    /**
     * 添加部门信息并返回部门的全部信息
     * @param department Department
     */
    void add(Department department);

    /**
     * 修改部门信息
     * @param department Department
     */
    void update(Department department);
}
