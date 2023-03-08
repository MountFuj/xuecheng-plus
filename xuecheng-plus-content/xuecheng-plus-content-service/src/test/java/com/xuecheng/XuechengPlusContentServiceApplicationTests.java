package com.xuecheng;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import com.xuecheng.content.service.CourseCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sound.midi.Soundbank;
import java.util.List;

@SpringBootTest
class XuechengPlusContentServiceApplicationTests {
    @Autowired
    CourseBaseMapper courseBaseMapper;
    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @Autowired
    CourseCategoryService courseCategoryService;
    @Test
    void BaseCourseMapper() {
        CourseBase courseBase = courseBaseMapper.selectById(22);
        System.out.println(courseBase.toString());
    }
    @Test
    void CourseBaseInfoService() {
        PageParams pageParams = new PageParams();
        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseList(pageParams, new QueryCourseParamsDto());
        System.out.println(courseBasePageResult);
    }
    @Test
    void testqueryTreeNodes(){
        List<CourseCategoryTreeDto> categoryTreeDtos = courseCategoryService.queryTreeNodes("1");
        System.out.println(categoryTreeDtos);
    }

}
