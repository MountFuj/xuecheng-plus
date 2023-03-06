package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import org.springframework.web.bind.annotation.RequestBody;

public interface CourseBaseInfoService {
    /***
     * @description 课程查询
     * @param params 分页参数
     * @param dto 查询条件
     * @return
     * @author
     * @date
    */
    public PageResult<CourseBase>  queryCourseBaseList(PageParams params,QueryCourseParamsDto dto);
}
