package com.das.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.das.entity.Department;
import com.das.entity.Page;
import com.das.entity.User;
import com.das.service.DepartmentService;
import com.das.service.UserService;
import com.das.utils.JudgAuthority;
import com.das.utils.JwtUtils;
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
@CrossOrigin(maxAge = 3600)
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public Map<String, Object> findAll(Integer pagenum, Integer pagesize){
        Page<Department> page = new Page<>(pagenum,pagesize);
        page.setRows(departmentService.findAll(pagenum,pagesize));
        page.setTotal(departmentService.count());
        return State.packet(page,"获取成功",200);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> add(@RequestBody Department department,@RequestHeader("token") String token){
        if (!JudgAuthority.isAdmin(userService,token)){
            return State.packet(null,"添加失败，您的权限不足",403);
        }else {
            departmentService.add(department);
            return State.packet(departmentService.findById(department.getId()),"添加成功",201);
        }
    }

    @RequestMapping(path = "/{id}",method = RequestMethod.PUT)
    public Map<String, Object> update(@RequestBody Department department, @PathVariable("id") int id,@RequestHeader("token") String token){
        if (!JudgAuthority.isAdmin(userService,token)){
            return State.packet(null,"修改失败，您的权限不足",403);
        }else {
            department.setId(id);
            departmentService.update(department);
            return State.packet(departmentService.findById(department.getId()),"修改成功",200);
        }
    }
}
