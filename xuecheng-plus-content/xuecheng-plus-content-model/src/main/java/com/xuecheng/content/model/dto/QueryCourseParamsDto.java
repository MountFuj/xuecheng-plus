package com.xuecheng.content.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author Administrator
 * @version 1.0
 * @description TODO
 * @date 2023/3/5 11:05
 */
@Data
@ToString
public class QueryCourseParamsDto {
    //审核状态
    private String auditStatus;
    //课程名称
    private String courseName;
    //发布状态
    private String publishStatus;
}
