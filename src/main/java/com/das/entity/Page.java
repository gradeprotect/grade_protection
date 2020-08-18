package com.das.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页信息
 * @author Tim
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {
    /**
     * page:分页起始页
     * size:每页总条数
     * total:总记录数
     * rows:返回记录集合
     */
    private Integer page;
    private Integer size;
    private Integer total;
    private List<T> rows;
}
