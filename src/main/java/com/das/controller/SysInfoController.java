package com.das.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.das.entity.SysInfo;
import com.das.entity.SysInfoWithName;
import com.das.service.SysInfoService;
import com.das.service.SysInfoWithNameService;
import com.das.service.UserService;
import com.das.utils.JudgAuthority;
import com.das.utils.JwtUtils;
import com.das.utils.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 许文滨
 * @date 2020-8-19
 */
@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping(path = "/sysInfo")
public class SysInfoController {
    @Autowired
    private SysInfoService sysInfoService;
    @Autowired
    private SysInfoWithNameService sysInfoWithNameService;
    @Autowired
    private UserService userService;

    /**
     * 导入系统信息
     * @param sysInfo SysInfo
     * @return Map<String,Object>
     */
    @RequestMapping(method = RequestMethod.POST)
    public Map<String,Object> add(@RequestBody SysInfo sysInfo){
        sysInfoService.add(sysInfo);
        SysInfoWithName sysInfoWithName = sysInfoWithNameService.findById(sysInfo.getId());
        return State.packet(sysInfoWithName,"添加成功",201);
    }

    /**
     * 通过 excel 文件导入系统信息
     * @param multipartFile MultipartFile
     * @return Map<String,Object>
     */
    @RequestMapping(path = "/excel",method = RequestMethod.POST)
    public Map<String,Object> addByExcel(@RequestParam("file") MultipartFile multipartFile,@RequestHeader("Authorization") String token){
        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
        Integer importer_id = Integer.parseInt(tokenInfo.getClaim("id").asString());
        List<SysInfo> sysInfos = sysInfoService.addByExcel(multipartFile,importer_id);
        if (sysInfos == null){
            return State.packet(null,"批量导入失败",422);
        }
        return State.packet(sysInfos,"批量导入成功",201);
    }

    /**
     * 修改系统信息
     * @param sysInfo SysInfo
     * @param id int
     * @return Map<String,Object>
     */
    @RequestMapping(path = "/{id}",method = RequestMethod.PUT)
    public Map<String,Object> update(@RequestBody SysInfo sysInfo,@PathVariable("id") int id){
        sysInfo.setId(id);
        sysInfoService.update(sysInfo);
        SysInfoWithName sysInfoWithName = sysInfoWithNameService.findById(sysInfo.getId());
        return State.packet(sysInfoWithName,"修改成功",200);
    }

    /**
     * 删除系统信息
     * @param id int
     * @return Map<String,Object>
     */
    @RequestMapping(path = "/{id}",method = RequestMethod.DELETE)
    public Map<String,Object> delete(@PathVariable("id") int id){
        sysInfoService.delete(id);
        return State.packet(null,"删除成功",204);
    }

    /**
     * 审核系统信息
     * @param id int
     * @param sysInfo SysInfo
     * @return Map<String,Object>
     */
    @RequestMapping(path = "/review/{id}",method = RequestMethod.PUT)
    public Map<String,Object> review(@PathVariable("id") int id,@RequestBody SysInfo sysInfo,@RequestHeader("Authorization") String token){
        if (!JudgAuthority.isAdmin(userService,token)){
            return State.packet(null,"审核失败，您没有权限",403);
        }else {
            sysInfo.setId(id);
            sysInfoService.review(sysInfo);
            SysInfoWithName sysInfoWithName = sysInfoWithNameService.findById(sysInfo.getId());
            return State.packet(sysInfoWithName,"审核成功",200);
        }
    }
}
