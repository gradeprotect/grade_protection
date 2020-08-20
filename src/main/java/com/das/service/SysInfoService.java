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

    public void add(SysInfo sysInfo){
        sysInfoMapper.add(sysInfo);
    }

    public List<SysInfo> addByExcel(MultipartFile multipartFile,Integer importer_id){
        List<SysInfo> sysInfos = new ArrayList<>();
        try{
            sysInfos = ReadExcelContents.readExcel(multipartFile,importer_id);
        }catch (Exception e){
            e.printStackTrace();
        }
        for (int i=0;i<sysInfos.size();i++){
            System.out.println(sysInfos.get(i).toString());
            sysInfoMapper.add(sysInfos.get(i));
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
}
