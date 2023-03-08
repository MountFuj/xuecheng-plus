package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
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

    /***
     * @description 新增课程
     * @param companyId 培训机构id
     * @param addCourseDto 新增课程信息
     * @return课程信息包括营销信息
     * @author
     * @date
    */
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);
    /***
     * @description 根据id查询课程信息
     * @param courseId 课程id
     * @return
     * @author 
     * @date  
    */
    public CourseBaseInfoDto getCourseBaseInfo(long courseId);

    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto);
}
