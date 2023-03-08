package com.xuecheng.content.api;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import com.xuecheng.content.service.Impl.CourseMarketServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Administrator
 * @version 1.0
 * @description TODO
 * @date 2023/3/5 14:07
 */
@Api(value = "课程管理相关的接口",tags = "课程管理相关的接口")
@RestController
public class CourseBaseInfoController {
    @Autowired
    CourseBaseInfoService courseBaseInfoService;
    @ApiOperation("课程查询接口")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams params, @RequestBody QueryCourseParamsDto dto){
        //调用srevice获取数据
        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseList(params, dto);
        return courseBasePageResult;
    }
    @ApiOperation("新增课程")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated AddCourseDto addCourseDto){
        //获取当前用户所属培训机构id
        Long companyId=22L;
        //调用service
        CourseBaseInfoDto courseBase = courseBaseInfoService.createCourseBase(companyId, addCourseDto);
        return courseBase;
    }
    @ApiOperation("查询数据回显接口")
    @GetMapping("/course/{courseId}")
    public  CourseBaseInfoDto getCourseBaseById(@PathVariable Long courseId){
        return courseBaseInfoService.getCourseBaseInfo(courseId);
    }
    @PutMapping("/course")
    public CourseBaseInfoDto modifyCourseBase(@RequestBody EditCourseDto editCourseDto){
        Long companyid=22L;
        return  courseBaseInfoService.updateCourseBase(companyid,editCourseDto);
    }
}
