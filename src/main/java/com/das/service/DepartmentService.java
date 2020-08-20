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
        return departmentsMapper.findAll((pagenum-1)*pagesize,pagesize);
    }

    public void deleteById(Integer id){
        departmentsMapper.deleteById(id);
    }

    public List<String> getNames(){
        return departmentsMapper.getNames();
    }

    public Integer countByName(String name){
        return departmentsMapper.countByName(name);
    }

    public List<Department> findByName(String name,Integer pagenum,Integer pagesize){
        return departmentsMapper.findByName(name,(pagenum-1)*pagesize,pagesize);
    }

    public Integer count(){
        return departmentsMapper.count();
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
