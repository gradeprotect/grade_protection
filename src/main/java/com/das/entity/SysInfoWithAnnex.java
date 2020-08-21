package com.das.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 许文滨
 * @date 2020-8-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysInfoWithAnnex {
    private String sysInfo;
    private MultipartFile multipartFile;
}
