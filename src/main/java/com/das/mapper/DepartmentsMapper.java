package com.das.mapper;

import com.das.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
     * @param id int
     */
    void update(Department department);
}
