package com.das.service;

import com.das.entity.Department;
import com.das.mapper.DepartmentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 许文滨
 * @date 2020-8-18
 */
@Service
public class DepartmentService {
    @Autowired
    private DepartmentsMapper departmentsMapper;

    public List<Department> findAll(Integer pagenum,Integer pagesize){
        return departmentsMapper.findAll(pagenum,pagesize);
    }

    public Department findById(int id){
        return departmentsMapper.findById(id);
    }

    public void add(Department department){
        departmentsMapper.add(department);
    }

    public void update(Department department){
        departmentsMapper.update(department);
    }
}