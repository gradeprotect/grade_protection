package com.das.controller;

import com.das.entity.Page;
import com.das.entity.SysInfoWithName;
import com.das.service.SysInfoWithNameService;
import com.das.service.UserService;
import com.das.utils.JudgAuthority;
import com.das.utils.JwtUtils;
import com.das.utils.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

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
    @Autowired
    private UserService userService;

    /**
     * 分页查询系统信息
     * @param pagenum 当前页码
     * @param pagesize 每页数据数
     * @return Map<String,Object>
     */
    @RequestMapping(method = RequestMethod.GET)
    public Map<String, Object> findAll(Integer pagenum, Integer pagesize){
        Page<SysInfoWithName> page = new Page<>(pagenum,pagesize);
        page.setRows(sysInfoWithNameService.findAll(pagenum,pagesize));
        page.setTotal(sysInfoWithNameService.count());
        return State.packet(page,"获取成功",200);
    }

    /**
     * 根据系统id获取系统信息
     * @param id 系统id
     * @return Map<String,Object>
     */
    @RequestMapping(method = RequestMethod.GET,path = "/findById/{id}")
    public Map<String,Object> findById(@PathVariable("id") Integer id){
        return State.packet(sysInfoWithNameService.findById(id),"获取成功",200);
    }

    /**
     * 根据系统审核状态分页获取系统信息
     * @param review_state 审核状态
     * @param pagenum 当前页码
     * @param pagesize 每页数据数
     * @return Map<String,Object>
     */
    @RequestMapping(path = "/findByState/{state}")
    public Map<String,Object> findByState(@PathVariable("state") int review_state,Integer pagenum,Integer pagesize){
        List<SysInfoWithName> sysInfoWithNameList = sysInfoWithNameService.findByState(review_state,pagenum,pagesize);
        Page<SysInfoWithName> page = new Page<>(pagenum,pagesize);
        page.setRows(sysInfoWithNameList);
        page.setTotal(sysInfoWithNameService.countByState(review_state));
        return State.packet(page,"获取成功",200);
    }

    /**
     * 根据等保级别、录入时间区间、审核时间区间和系统名分页模糊查询系统信息
     * @param pagenum 当前页码
     * @param pagesize 每页数据数
     * @param grade 等保级别
     * @param name 系统名
     * @param import_time 导入时间
     * @param review_time 审核时间
     * @param token String
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,path = "/findByInfo")
    public Map<String,Object> findByInfo(Integer pagenum,Integer pagesize,Integer grade, String name,
                                          String[] import_time, String[] review_time,
                                         @RequestHeader("Authorization") String token)
            throws ParseException {
        name = name==null || "".equals(name)?null:name;
        if (import_time == null || import_time.length == 0){
            import_time = new String[2];
            import_time[0] = null;
            import_time[1] = null;
        }
        if (review_time == null || review_time.length == 0){
            review_time = new String[2];
            review_time[0] = null;
            review_time[1] = null;
        }

        Page<SysInfoWithName> page = new Page<>(pagenum,pagesize);
        if (JudgAuthority.isAdmin(userService,token)){
            page.setRows(sysInfoWithNameService.findByInfo(pagenum,pagesize,grade,name,
                    import_time[0],import_time[1],review_time[0],review_time[1],null));
            page.setTotal(sysInfoWithNameService.countByInfo(pagenum,pagesize,grade,name,
                    import_time[0],import_time[1],review_time[0],review_time[1],null));
        }else {
            Integer importer_id = Integer.parseInt(JwtUtils.getTokenInfo(token).getClaim("id").asString());
            page.setRows(sysInfoWithNameService.findByInfo(pagenum,pagesize,grade,name,
                    import_time[0],import_time[1],review_time[0],review_time[1],importer_id));
            page.setTotal(sysInfoWithNameService.countByInfo(pagenum,pagesize,grade,name,
                    import_time[0],import_time[1],review_time[0],review_time[1],importer_id));
        }
        return State.packet(page,"获取成功",200);
    }

    @RequestMapping(method = RequestMethod.GET,path = "/countByAuthority")
    public Map<String,Object> countByAuthority(@RequestHeader("Authorization") String token){
        List<Integer> ans = new ArrayList<>();
        if (JudgAuthority.isAdmin(userService,token)){
            ans.add(sysInfoWithNameService.countByState(1));
        }else {
            Integer importer_id = Integer.parseInt(JwtUtils.getTokenInfo(token).getClaim("id").asString());
            ans = sysInfoWithNameService.countByImporter(importer_id);
        }
        return State.packet(ans,"获取成功",200);
    }
}
