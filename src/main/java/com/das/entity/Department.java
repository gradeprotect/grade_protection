package com.das.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * @author Tim
 */
@Data
@AllArgsConstructor
public class Department {
    private Integer id;
    private String name;
    private String principal_name;
    private String principal_telephone;
    private String principal_email;
    private String principal_title;
    private String principal_office_telephone;
}
