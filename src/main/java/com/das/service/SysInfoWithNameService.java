package com.das.service;

import com.das.entity.SysInfoWithName;
import com.das.mapper.SysInfoWithNameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 许文滨
 * @date 2020-8-19
 */
@Service
public class SysInfoWithNameService {
    @Autowired
    private SysInfoWithNameMapper sysInfoWithNameMapper;

    public List<SysInfoWithName> findAll(Integer pagenum,Integer pagesize){
        return sysInfoWithNameMapper.findAll((pagenum-1)*pagesize,pagesize);
    }

    public Integer count(){
        return sysInfoWithNameMapper.count();
    }

    public SysInfoWithName findById(Integer id){
        return sysInfoWithNameMapper.findById(id);
    }

    public List<SysInfoWithName> findByState(Integer review_state, Integer pagenum,Integer pagesize){
        return sysInfoWithNameMapper.findByState(review_state,(pagenum-1)*pagesize,pagesize);
    }

    public Integer countByState(Integer review_state){
        return sysInfoWithNameMapper.countByState(review_state);
    }
}
