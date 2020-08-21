package com.das.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 许文滨
 * @date 2020-8-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private Integer id;
    private String name;
    private String principal_name;
    private String principal_telephone;
    private String principal_email;
    private String principal_title;
    private String principal_office_telephone;
    private Boolean isdelete;
}
