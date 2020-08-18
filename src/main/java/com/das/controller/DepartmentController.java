package com.das.controller;

import com.das.entity.Department;
import com.das.service.DepartmentService;
import com.das.utils.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 许文滨
 * @date 2020-8-18
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Department> findAll(Integer pagenum,Integer pagesize){
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> add(@RequestBody Department department){
        departmentService.add(department);
        return State.packet(departmentService.findById(department.getId()),"添加成功",201);
    }

    @RequestMapping(path = "/{id}",method = RequestMethod.PUT)
    public Map<String, Object> update(@RequestBody Department department, @PathVariable("id") int id){
        department.setId(id);
        departmentService.update(department);
        return State.packet(departmentService.findById(department.getId()),"修改成功",200);
    }
}
