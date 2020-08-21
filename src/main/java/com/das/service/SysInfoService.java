package com.das.service;

import com.das.entity.SysInfo;
import com.das.mapper.SysInfoMapper;
import com.das.utils.ReadExcelContents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        try{
            sysInfos = readExcelContents.readExcel(multipartFile,importer_id);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (sysInfos==null){
            return null;
        }
        for (SysInfo sysInfo : sysInfos) {
            System.out.println(sysInfo.toString());
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
}
