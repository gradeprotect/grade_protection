package com.das.service;

import com.das.entity.CarouselData;
import com.das.entity.DeptGradeStatus;
import com.das.entity.SysInfo;
import com.das.mapper.DepartmentsMapper;
import com.das.mapper.SysInfoMapper;
import com.das.utils.ReadExcelContents;
import com.das.utils.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @author 许文滨
 * @date 2020-8-19
 */
@Service
public class SysInfoService {
    @Autowired
    private SysInfoMapper sysInfoMapper;
    @Autowired
    private ReadExcelContents readExcelContents;
    @Autowired
    private DepartmentsMapper departmentsMapper;
    @Autowired
    private SysInfoService sysInfoService;

    public List<SysInfo> getAll(){
        return sysInfoMapper.getAll();
    }

    public void addAnnex(String annex,Integer id){
        sysInfoMapper.addAnnex(annex,id);
    }

    public void add(SysInfo sysInfo){
        //数据校验
        if (sysInfo.getDatabase_num()==null) sysInfo.setDatabase_num(0);
        if (sysInfo.getNetwork_num()==null) sysInfo.setNetwork_num(0);
        if (sysInfo.getSafety_num()==null) sysInfo.setSafety_num(0);
        if (sysInfo.getServer_num()==null) sysInfo.setServer_num(0);
        if (sysInfo.getDomestic_database_num()==null) sysInfo.setDomestic_database_num(0);
        if (sysInfo.getDomestic_network_num()==null) sysInfo.setDomestic_network_num(0);
        if (sysInfo.getDomestic_safety_num()==null) sysInfo.setDomestic_safety_num(0);
        if (sysInfo.getDomestic_server_num()==null) sysInfo.setDomestic_server_num(0);
        if (sysInfo.getRank_evaluation()==null) sysInfo.setRank_evaluation(false);
        if (sysInfo.getRisk_assessment()==null) sysInfo.setRisk_assessment(false);
        if (sysInfo.getDisaster_recover()==null) sysInfo.setDisaster_recover(false);
        if (sysInfo.getEmergency_response()==null) sysInfo.setEmergency_response(false);
        if (sysInfo.getSys_integration()==null) sysInfo.setSys_integration(false);
        if (sysInfo.getSecurity_advisory()==null) sysInfo.setSecurity_advisory(false);
        if (sysInfo.getSecurity_train()==null) sysInfo.setSecurity_train(false);

        sysInfoMapper.add(sysInfo);
    }

    public List<SysInfo> addByExcel(MultipartFile multipartFile,Integer importer_id){
        List<SysInfo> sysInfos = new ArrayList<>();
        //读取excel
        try{
            sysInfos = readExcelContents.readExcel(multipartFile,importer_id);
        }catch (Exception e){
            e.printStackTrace();
        }
        //如果读取excel失败，则返回null
        if (sysInfos==null){
            return null;
        }

        //读取成功，向数据库中插入
        for (SysInfo sysInfo : sysInfos) {
            sysInfoMapper.add(sysInfo);
        }
        return sysInfos;
    }

    public void update(SysInfo sysInfo){
        sysInfoMapper.update(sysInfo);
    }

    public void delete(Integer id){
        sysInfoMapper.delete(id);
    }

    public void review(SysInfo sysInfo){
        sysInfoMapper.review(sysInfo);
    }

    public Integer getGroupGradeNum(Integer grade){
        return sysInfoMapper.getGroupGradeNum(grade);
    }

    public Integer getDomesticProductNum(String productType){
        return sysInfoMapper.getDomesticProductNum(productType);
    }

    public Map<String,Integer> getCommittedAndDepartmentNums(){
        Map<String,Integer> map = new HashMap<>(2);
        map.put("committed_num", sysInfoMapper.getCommittedNum());
        map.put("total_dept_num", departmentsMapper.count());
        return map;
    }

    public List<DeptGradeStatus> getAllDeptGradeStatus(){
        List<String> deptNames = departmentsMapper.getNames();
        List<DeptGradeStatus> allDeptGradeStatus = sysInfoMapper.getAllDeptGradeStatus();
        // 清除已录入部门，获得未录入部门
        for (DeptGradeStatus deptGradeStatus: allDeptGradeStatus) {
            deptNames.remove(deptGradeStatus.getDeptName());
        }
        // 对未录入等保信息的部门进行补0
        for(String deptName: deptNames){
            DeptGradeStatus deptGradeStatus = new DeptGradeStatus(deptName, 0, 0, 0);
            allDeptGradeStatus.add(deptGradeStatus);
        }
        return allDeptGradeStatus;
    }

    public List<CarouselData> getCarouselData(){
        List<CarouselData> list = sysInfoMapper.getCarouselData();
        return list;
    }
}
