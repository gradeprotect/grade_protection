package com.das.service;

import com.das.entity.SysInfo;
import com.das.mapper.SysInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
