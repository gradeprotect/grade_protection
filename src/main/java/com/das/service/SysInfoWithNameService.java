package com.das.service;

import com.das.entity.SysInfoWithName;
import com.das.mapper.SysInfoWithNameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public List<Integer> countByImporter(Integer id){
        return sysInfoWithNameMapper.countByImporter(id);
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

    public List<SysInfoWithName> findByInfo(Integer pagenum, Integer pagesize, Integer grade, String name,
                                            String import_time1, String import_time2, String review_time1, String review_time2,
                                            Integer importer_id) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date it1 = import_time1==null?null:simpleDateFormat.parse(import_time1);
        Date it2 = import_time2==null?null:simpleDateFormat.parse(import_time2);
        if (it1 == null && it2 != null){
            it1 = new Date();
        }else if (it1 != null && it2 == null){
            it2 = new Date();
        }
        Date rw1 = review_time1==null?null:simpleDateFormat.parse(review_time1);
        Date rw2 = review_time2==null?null:simpleDateFormat.parse(review_time2);
        if (rw1 == null && rw2 != null){
            rw1 = new Date();
        }else if (rw1 != null && rw2 == null){
            rw2 = new Date();
        }

        name = name == null?null:"%"+name+"%";
        return sysInfoWithNameMapper.findByInfo((pagenum-1)*pagesize,pagesize,grade,name, it1,it2,rw1,rw2,importer_id);
    }

    public Integer countByInfo(Integer pagenum, Integer pagesize, Integer grade, String name,
                               String import_time1, String import_time2, String review_time1, String review_time2,
                               Integer importer_id) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date it1 = import_time1==null?null:simpleDateFormat.parse(import_time1);
        Date it2 = import_time2==null?null:simpleDateFormat.parse(import_time2);
        if (it1 == null && it2 != null){
            it1 = new Date();
        }else if (it1 != null && it2 == null){
            it2 = new Date();
        }
        Date rw1 = review_time1==null?null:simpleDateFormat.parse(review_time1);
        Date rw2 = review_time2==null?null:simpleDateFormat.parse(review_time2);
        if (rw1 == null && rw2 != null){
            rw1 = new Date();
        }else if (rw1 != null && rw2 == null){
            rw2 = new Date();
        }

        name = name == null?null:"%"+name+"%";
        return sysInfoWithNameMapper.countByInfo((pagenum-1)*pagesize,pagesize,grade,name, it1,it2,rw1,rw2,importer_id);
    }

}
