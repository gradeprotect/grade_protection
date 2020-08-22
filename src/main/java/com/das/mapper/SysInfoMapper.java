package com.das.mapper;

import com.das.entity.SysInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 许文滨
 * @date 2020-8-19
 */
@Mapper
public interface SysInfoMapper {
    /**
     * 添加系统信息
     * @param sysInfo SysInfo
     */
    void add(SysInfo sysInfo);

    /**
     * 修改系统信息
     * @param sysInfo SysInfo
     */
    void update(SysInfo sysInfo);

    /**
     * 删除系统信息
     * @param id SysInfo
     */
    void delete(Integer id);

    /**
     * 审核系统信息
     * @param sysInfo SysInfo
     */
    void review(SysInfo sysInfo);

    /**
     * 获取相应等级的系统总数
     * @param grade Integer
     * @return Integer
     */
    Integer getGroupGradeNum(Integer grade);

    /**
     * 获取相应类别的安全产品数
     * @param productType String
     * @return Integer
     */
    Integer getDomesticProductNum(String productType);
}
