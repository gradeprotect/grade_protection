package com.das.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

/**
 * @author 许文滨
 * @date 2020-8-19
 */
@Data
public class SysInfo {
    private Integer id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date import_time;
    private Integer importer_id;
    private Integer reviewer_id;
    private Integer review_state;
    private String failure_reason;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date pass_time;
    private Integer grade_protect_level;
    private Integer department_id;
    private String annex;
    private String business_type;
    private String business_desc;
    private String service_area;
    private String service_obj;
    private String network;
    private String sys_interconnect;
    private Integer safety_num;
    private Integer domestic_safety_num;
    private Integer network_num;
    private Integer domestic_network_num;
    private Integer operate_sys_num;
    private Integer domestic_operate_sys_num;
    private Integer database_num;
    private Integer domestic_database_num;
    private Integer server_num;
    private Integer domestic_server_num;
    private Boolean rank_evaluation;
    private Boolean risk_assessment;
    private Boolean disaster_recover;
    private Boolean emergency_response;
    private Boolean sys_integration;
    private Boolean security_advisory;
    private Boolean security_train;
    private String evaluate_firm_name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date put_into_time;
    private Integer business_info_level;
    private Integer sys_service_level;
    private Integer info_sys_level;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date rank_time;
    private Boolean expert_review;
    private String competent_authority_name;
    private Boolean sys_rate_report;
}
