package com.das.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 部门不同等保等级系统的情况
 * @author 谭立聪
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeptGradeStatus {
    /**
     * deptName:部门名称
     * grade2num:该部门二级系统数量
     * grade3num:该部门三级系统数量
     * grade4num:该部门四级系统数量
     */
    private String deptName;
    private Integer grade2num;
    private Integer grade3num;
    private Integer grade4num;

}
