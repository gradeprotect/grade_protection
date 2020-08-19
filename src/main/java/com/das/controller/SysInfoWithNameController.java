package com.das.controller;

import com.das.entity.Page;
import com.das.entity.SysInfoWithName;
import com.das.service.SysInfoWithNameService;
import com.das.utils.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 许文滨
 * @date 2020-8-19
 */
@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping(path = "/sysInfo")
public class SysInfoWithNameController {
    @Autowired
    private SysInfoWithNameService sysInfoWithNameService;

    @RequestMapping(method = RequestMethod.GET)
    public Map<String, Object> findAll(Integer pagenum, Integer pagesize){
        Page<SysInfoWithName> page = new Page<>(pagenum,pagesize);
        page.setRows(sysInfoWithNameService.findAll(pagenum,pagesize));
        page.setTotal(sysInfoWithNameService.count());
        return State.packet(page,"获取成功",200);
    }

    @RequestMapping(path = "/findByState/{state}")
    public Map<String,Object> findByState(@PathVariable("state") int review_state,Integer pagenum,Integer pagesize){
        List<SysInfoWithName> sysInfoWithNameList = sysInfoWithNameService.findByState(review_state,pagenum,pagesize);
        Page<SysInfoWithName> page = new Page<>(pagenum,pagesize);
        page.setRows(sysInfoWithNameList);
        page.setTotal(sysInfoWithNameService.countByState(review_state));
        return State.packet(page,"获取成功",200);
    }
}
