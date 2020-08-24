package com.das.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 首页轮播数据
 * @author 谭立聪
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarouselData {
    /**
     * importTime:等保系统录入时间
     * departmentName:等保系统所属部门
     * importerName:录入员姓名
     * systemName:等保系统名
     */
    private String importTime;
    private String departmentName;
    private String importerName;
    private String systemName;
}