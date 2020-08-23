package com.das.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.das.entity.*;
import com.das.service.SysInfoService;
import com.das.service.SysInfoWithNameService;
import com.das.service.UserService;
import com.das.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

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
     * @param sysInfoWithAnnex SysInfoWithAnnex
     * @param token String
     * @return Map<String,Object>
     */
    @RequestMapping(method = RequestMethod.POST)
    public Map<String,Object> add(@ModelAttribute SysInfoWithAnnex sysInfoWithAnnex,@RequestHeader("Authorization") String token) throws IOException, GeneralSecurityException, MessagingException {
        JSONObject jo = JSONObject.parseObject(sysInfoWithAnnex.getSysInfo());
        SysInfo sysInfo = (SysInfo) JSONObject.toJavaObject(jo,SysInfo.class);
        System.out.println(sysInfo);
        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
        Integer importer_id = Integer.parseInt(tokenInfo.getClaim("id").asString());
        SysInfoWithName sysInfoWithName = sysInfoWithNameService.findById(sysInfo.getId());
        String annexPath = FileUpload.upload(sysInfoWithAnnex.getMultipartFile());
        sysInfo.setImporter_id(importer_id);
        sysInfo.setImport_time(new Date());
        sysInfo.setAnnex(annexPath);
        sysInfoService.add(sysInfo);
        String message = "[等级保护信息管理系统]<br>" + new Date().toString() + "<br>" + sysInfoWithName.getImporter() +
                "提交了<b>" + sysInfo.getName() + "</b>,请及时审核!";
        List<String> emails = userService.getEmails();
        for (String email: emails){
            SendEmail.sendEmail("审核结果",message,email);
        }
        return State.packet(sysInfoWithName,"添加成功",201);
    }

//    /**
//     * 导入系统信息
//     * @param sysInfo SysInfo
//     * @return Map<String,Object>
//     */
//    @RequestMapping(method = RequestMethod.POST)
//    public Map<String,Object> add(@RequestBody SysInfo sysInfo,@RequestHeader("Authorization") String token){
//        System.out.println(sysInfo);
//        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
//        Integer importer_id = Integer.parseInt(tokenInfo.getClaim("id").asString());
//        sysInfo.setImporter_id(importer_id);
//        sysInfo.setImport_time(new Date());
//        sysInfoService.add(sysInfo);
//        SysInfoWithName sysInfoWithName = sysInfoWithNameService.findById(sysInfo.getId());
//        return State.packet(sysInfoWithName,"添加成功",201);
//    }

    /**
     * 通过 excel 文件导入系统信息
     * @param multipartFile MultipartFile
     * @return Map<String,Object>
     */
    @RequestMapping(path = "/excel",method = RequestMethod.POST)
    public Map<String,Object> addByExcel(@RequestParam("file") MultipartFile multipartFile,@RequestHeader("Authorization") String token) throws GeneralSecurityException, MessagingException {
        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
        Integer importer_id = Integer.parseInt(tokenInfo.getClaim("id").asString());
        List<SysInfo> sysInfos = sysInfoService.addByExcel(multipartFile,importer_id);
        if (sysInfos == null || sysInfos.size() == 0){
            return State.packet(null,"批量导入失败",422);
        }
        Map<String,Object> map = new HashMap<>(2);
        map.put("success",sysInfos);
        map.put("fail", ReadExcelContents.fail);
        User user = userService.getUserById(importer_id);
        String message = "[等级保护信息管理系统]<br>" + new Date().toString() + "<br>" +
                user.getName() + "通过excel文件提交了<b>" + sysInfos.size() + "</b>个等级保护信息系统,请及时审核!";
        List<String> emails = userService.getEmails();
        for (String email: emails){
            SendEmail.sendEmail("审核结果",message,email);
        }
        return State.packet(map,"批量导入成功",201);
    }

    /**
     * 修改系统信息
     * @param sysInfo SysInfo
     * @param id int
     * @return Map<String,Object>
     */
    @RequestMapping(path = "/{id}",method = RequestMethod.PUT)
    public Map<String,Object> update(@RequestBody SysInfo sysInfo,@PathVariable("id") int id,@RequestHeader("Authorization") String token) throws GeneralSecurityException, MessagingException {
        DecodedJWT tokenInfo = JwtUtils.getTokenInfo(token);
        Integer importer_id = Integer.parseInt(tokenInfo.getClaim("id").asString());
        sysInfo.setId(id);
        sysInfo.setImporter_id(importer_id);
        sysInfoService.update(sysInfo);
        SysInfoWithName sysInfoWithName = sysInfoWithNameService.findById(sysInfo.getId());
        String message = "[等级保护信息管理系统]<br>" + new Date().toString() + "<br>" + sysInfoWithName.getImporter() +
                "修改了<b>" + sysInfo.getName() + "</b>,请及时审核!";
        List<String> emails = userService.getEmails();
        for (String email: emails){
            SendEmail.sendEmail("审核结果",message,email);
        }
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
     * 审核系统信息,并在审核成功后向导入员发送审核结果邮件
     * @param id int
     * @param sysInfo SysInfo
     * @return Map<String,Object>
     */
    @RequestMapping(path = "/review/{id}",method = RequestMethod.PUT)
    public Map<String,Object> review(@PathVariable("id") int id,@RequestBody SysInfo sysInfo,@RequestHeader("Authorization") String token) throws GeneralSecurityException, MessagingException {
        if (!JudgAuthority.isAdmin(userService,token)){
            return State.packet(null,"审核失败，您没有权限",403);
        }else {
            sysInfo.setId(id);
            sysInfo.setPass_time(new Date());
            sysInfoService.review(sysInfo);
            SysInfoWithName sysInfoWithName = sysInfoWithNameService.findById(sysInfo.getId());
            String message = null;
            if (sysInfo.getReview_state()==2) message = "[等级保护信息管理系统]<br>" + sysInfo.getPass_time().toString() +
                    "<br>您提交的 <b>" + sysInfoWithName.getName() + "</b> 已经通过了审核!";
            else message = "[等级保护信息管理系统]<br>" + new Date().toString() +
                    "<br>您提交的 <b>" + sysInfoWithName.getName() + "</b> 未能通过审核，请修改后重新提交<br>" +
                    "未通过原因:<br>" + sysInfo.getFailure_reason();
            String email = userService.getEmailById(sysInfoWithName.getImporter_id());
            SendEmail.sendEmail("审核结果",message,email);

            return State.packet(sysInfoWithName,"审核成功",200);
        }
    }

    /**
     * 获取集团的各级系统数量
     * @return map Map
     */
    @RequestMapping(path = "/groupGradeNum", method = RequestMethod.GET)
    public Map<String, Object> gteGroupGradeNum (){
        Integer grade2num = sysInfoService.getGroupGradeNum(2);
        Integer grade3num = sysInfoService.getGroupGradeNum(3);
        Integer grade4num = sysInfoService.getGroupGradeNum(4);
        Map<String,Object> map = new HashMap<>(3);
        map.put("grade2num", grade2num);
        map.put("grade3num", grade3num);
        map.put("grade4num", grade4num);
        return State.packet(map,"获取成功",200);
    }

    /**
     * 获取集团不同类别的安全产品数以及对应的国产安全产品数
     * 传入参数type的应该为[safety, network, database, server, operate_sys]
     * 对应的类别为[安全产品、网络产品、数据库、服务器、操作系统]
     * @param type String
     * @return map Map
     */
    @RequestMapping(path = "/domesticNum", method = RequestMethod.GET)
    public Map<String, Object> getDomesticProductNum (String type){
        String productType = type + "_num";
        String domesticProductType = "domestic_" + type + "_num";
        Integer totalNum = sysInfoService.getDomesticProductNum(productType);
        Integer domesticNum = sysInfoService.getDomesticProductNum(domesticProductType);
        Map<String,Integer> map = new HashMap<>(2);
        map.put("import_num", totalNum);
        map.put("domestic_num", domesticNum);
        return State.packet(map,"获取成功",200);
    }

    /**
     * 获取集团所有部门数以及已提交录入信息的部门数
     * @return map Map
     */
    @RequestMapping(path = "/committedNum", method = RequestMethod.GET)
    public Map<String, Object> getCommittedNum (){
        Map<String,Integer> map = sysInfoService.getCommittedAndDepartmentNums();
        return State.packet(map,"获取成功",200);
    }

    /**
     * 获取集团各个部门包含的不同等保等级系统数
     * @return List<DeptGradeStatus>
     */
    @RequestMapping(path = "/allDeptGradeStatus", method = RequestMethod.GET)
    public Map<String, Object> getAllDeptGradeStatus (){
        List<DeptGradeStatus> list = sysInfoService.getAllDeptGradeStatus();
        return State.packet(list,"获取成功",200);
    }

    /**
     * 获取首页动态轮播所需数据
     * @return List<DeptGradeStatus>
     */
    @RequestMapping(path = "/carouselData", method = RequestMethod.GET)
    public Map<String, Object> getCarouselData (){
        List<CarouselData> list = sysInfoService.getCarouselData();
        return State.packet(list,"获取成功",200);
    }
}
