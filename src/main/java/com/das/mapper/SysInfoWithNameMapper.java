package com.das.mapper;

import com.das.entity.SysInfoWithName;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @author 许文滨
 * @date 2020-8-19
 */
@Mapper
public interface SysInfoWithNameMapper {
    /**
     * 分页查询所有系统信息
     * @param pagenum Integer
     * @param pagesize Integer
     * @return List<SysInfoWithName>
     */
    List<SysInfoWithName> findAll(Integer pagenum, Integer pagesize);

    /**
     * 根据审核状态分页查询
     * @param pagenum Integer
     * @param pagesize Integer
     * @param state Integer
     * @return List<SysInfoWithName>
     */
    List<SysInfoWithName> findByState(Integer state,Integer pagenum, Integer pagesize);

    /**
     * 根据 id 查找系统信息
     * @param id Integer
     * @return SysInfoWithName
     */
    SysInfoWithName findById(Integer id);

    /**
     * 统计系统总数
     * @return Integer
     */
    Integer count();

    /**
     * 根据用户权限和用户id统计系统数
     * @param importer_id Integer
     * @return Integer
     */
    List<Integer> countByImporter(Integer importer_id);

    /**
     * 根据状态统计系统总数
     * @param review_state Integer
     * @return Integer
     */
    Integer countByState(Integer review_state);

    /**
     * 根据等保级别、录入时间区间、审核时间区间和系统名分页模糊查询系统信息
     * @param pagenum 当前页码
     * @param pagesize 每页数据数
     * @param grade 等保级别
     * @param name 系统名
     * @param import_time1 导入时间1
     * @param import_time2 导入时间2
     * @param review_time1 审核时间1
     * @param review_time2 审核时间2
     * @param importer_id
     * @return
     */
    List<SysInfoWithName> findByInfo(Integer pagenum, Integer pagesize, Integer grade, String name,
                                     Date import_time1, Date import_time2, Date review_time1, Date review_time2,
                                     Integer importer_id);

    /**
     *
     * @param pagenum
     * @param pagesize
     * @param grade
     * @param name
     * @param import_time1
     * @param import_time2
     * @param review_time1
     * @param review_time2
     * @param importer_id
     * @return
     */
    Integer countByInfo(Integer pagenum, Integer pagesize, Integer grade, String name,
                        Date import_time1, Date import_time2, Date review_time1, Date review_time2,
                        Integer importer_id);
}
