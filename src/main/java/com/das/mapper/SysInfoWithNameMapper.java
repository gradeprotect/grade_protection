package com.das.mapper;

import com.das.entity.SysInfoWithName;
import org.apache.ibatis.annotations.Mapper;

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
     * 根据状态统计系统总数
     * @param review_state Integer
     * @return Integer
     */
    Integer countByState(Integer review_state);
}
