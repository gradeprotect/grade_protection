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

    public List<Department> getAll(){
        return departmentsMapper.getAll();
    }

    public Integer getIdByName(String name){
        return departmentsMapper.getIdByName(name);
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
        //判断添加的部门是否是曾经删除的
        //若不是则直接添加
        //若是则对其进行修改，并将isdelete属性设置为false
        if (departmentsMapper.getIdByName(department.getName()) == 0){
            departmentsMapper.add(department);
        }else {
            department.setIsdelete(false);
            departmentsMapper.update(department);
        }
    }

    public void update(Department department){
        departmentsMapper.update(department);
    }
}
