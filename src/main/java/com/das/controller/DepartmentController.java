package com.das.controller;

import com.das.entity.Department;
import com.das.entity.Page;
import com.das.service.DepartmentService;
import com.das.service.UserService;
import com.das.utils.JudgAuthority;
import com.das.utils.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 许文滨
 * @date 2020-8-18
 */
@RestController
@RequestMapping("/department")
@CrossOrigin(maxAge = 360000)
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;

    /**
     * 分页获取部门
     * @param pagenum 当前页码
     * @param pagesize 每页的数据数
     * @return Map<String, Object>
     */
    @RequestMapping(method = RequestMethod.GET)
    public Map<String, Object> findAll(Integer pagenum, Integer pagesize){
        if (pagenum<=0){
            return State.packet(null,"获取失败，pagenum必须为正整数",400);
        }
        Page<Department> page = new Page<>(pagenum,pagesize);
        page.setRows(departmentService.findAll(pagenum,pagesize));
        page.setTotal(departmentService.count());
        return State.packet(page,"获取成功",200);
    }

    /**
     * 获取所有部门信息
     * @return Map<String,Object>
     */
    @RequestMapping(method = RequestMethod.GET,path = "/getAll")
    public Map<String,Object> getAll(){
        return State.packet(departmentService.getAll(),"获取成功",200);
    }

    /**
     * 根据部门名删除部门
     * @param id 部门id
     * @return Map<String,Object>
     */
    @RequestMapping(path = "/{id}",method = RequestMethod.DELETE)
    public Map<String,Object> deleteById(@PathVariable("id") Integer id,@RequestHeader("Authorization") String token){
        if (JudgAuthority.isAdmin(userService,token)){
            departmentService.deleteById(id);
            return State.packet(null,"删除成功",204);
        }else {
            return State.packet(null,"删除失败，您的权限不足！",403);
        }
    }

    /**
     * 根据部门名模糊查询分页获取部门信息
     * @param name 部门名
     * @param pagenum 当前页码
     * @param pagesize 每页的数据数
     * @return Map<String,Object>
     */
    @RequestMapping(path = "/findByName/{name}",method = RequestMethod.GET)
    public Map<String,Object> findByName(@PathVariable String name,Integer pagenum,Integer pagesize){
        Page<Department> page = new Page<>(pagenum,pagesize);
        page.setRows(departmentService.findByName(name,pagenum,pagesize));
        page.setTotal(departmentService.countByName(name));
        return State.packet(page,"获取成功",200);
    }

    /**
     * 根据部门id获取部门信息
     * @param id 部门id
     * @return Map<String,Object>
     */
    @RequestMapping(path = "/findById/{id}",method = RequestMethod.GET)
    public Map<String,Object> findById(@PathVariable("id") Integer id){
        return State.packet(departmentService.findById(id), "获取成功", 200);
    }

    /**
     * 获取所有的部门名
     * @return Map<String,Object>
     */
    @RequestMapping(path = "/getNames",method = RequestMethod.GET)
    public Map<String,Object> getNames(){
        return State.packet(departmentService.getNames(),"获取成功",200);
    }

    /**
     * 添加系统信息
     * @param department 部门信息
     * @param token String
     * @return Map<String,Object>
     */
    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> add(@RequestBody Department department,@RequestHeader("Authorization") String token){
        if (!JudgAuthority.isAdmin(userService,token)){
            return State.packet(null,"添加失败，您的权限不足",403);
        }else {
            departmentService.add(department);
            return State.packet(departmentService.findById(department.getId()),"添加成功",201);
        }
    }

    /**
     * 根据部门id修改部门信息
     * @param department 部门信息
     * @param id 部门id
     * @param token
     * @return Map<String,Object>
     */
    @RequestMapping(path = "/{id}",method = RequestMethod.PUT)
    public Map<String, Object> update(@RequestBody Department department, @PathVariable("id") int id,@RequestHeader("Authorization") String token){
        if (!JudgAuthority.isAdmin(userService,token)){
            return State.packet(null,"修改失败，您的权限不足",403);
        }else {
            department.setId(id);
            departmentService.update(department);
            return State.packet(departmentService.findById(department.getId()),"修改成功",200);
        }
    }
}
