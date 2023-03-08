package com.xuecheng.content.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.exception.CommonError;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.CastExpression;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @description TODO
 * @date 2023/3/6 10:06
 */
@Service
@Slf4j
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {
    @Autowired
    CourseBaseMapper courseBaseMapper;
    @Autowired
    CourseMarketMapper courseMarketMapper;
    @Autowired
    CourseCategoryMapper courseCategoryMapper;
    @Autowired
    CourseMarketServiceImpl courseMarketService;
    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams params, QueryCourseParamsDto dto) {
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        //拼接查询条件
        //根据课程名称模糊查询
        queryWrapper.like(StringUtils.isNotEmpty(dto.getCourseName()),CourseBase::getName,dto.getCourseName());
        //根据课程审核状态
        queryWrapper.eq(StringUtils.isNotEmpty(dto.getAuditStatus()),CourseBase::getAuditStatus,dto.getAuditStatus());
        //根据课程发布状态
        queryWrapper.eq(StringUtils.isNotEmpty(dto.getPublishStatus()),CourseBase::getStatus,dto.getPublishStatus());
        //分页参数
        Page<CourseBase> page = new Page<>(params.getPageNo(), params.getPageSize());
        //分页查询
        Page<CourseBase> courseBasePage = courseBaseMapper.selectPage(page, queryWrapper);
        //数据
        List<CourseBase> records = courseBasePage.getRecords();
        //总记录数
        long total = courseBasePage.getTotal();
        //准备返回数据
        PageResult<CourseBase> courseBasePageResult = new PageResult<>(records, total, params.getPageNo(), params.getPageSize());
        return courseBasePageResult;
    }

    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {
        //对参数合法性的校验
        //合法性校验
        //合法性校验
//        if (StringUtils.isBlank(dto.getName())) {
//            throw new RuntimeException("课程名称为空");
////            XueChengPlusException.cast(CommonError.PARAMS_ERROR);
////            XueChengPlusException.cast("课程名称为空");
//        }
//        if (StringUtils.isBlank(dto.getMt())) {
//            throw new RuntimeException("课程分类为空");
//        }
//        if (StringUtils.isBlank(dto.getSt())) {
//            throw new RuntimeException("课程分类为空");
//        }
//        if (StringUtils.isBlank(dto.getGrade())) {
//            throw new RuntimeException("课程等级为空");
//        }
//        if (StringUtils.isBlank(dto.getTeachmode())) {
//            throw new RuntimeException("教育模式为空");
//        }
//        if (StringUtils.isBlank(dto.getUsers())) {
//            throw new RuntimeException("适应人群为空");
//        }
//        if (StringUtils.isBlank(dto.getCharge())) {
//            throw new RuntimeException("收费规则为空");
//        }
        //对数据进行封装
        //对数据进行封装，调用mapper进行数据持久化
        CourseBase courseBase = new CourseBase();
        //将传入dto的数据设置到courseBase对象
//        courseBase.setName(dto.getName());
//        courseBase.setMt(dto.getMt());
        //将dto中和courseBase属性名一样的属性值拷贝到courseBase
        BeanUtils.copyProperties(dto,courseBase);
        //设置机构id
        courseBase.setCompanyId(companyId);
        //创建时间
        courseBase.setCreateDate(LocalDateTime.now());
        //审核状态默认为未提交
        courseBase.setAuditStatus("202002");
        //发布状态默认为未发布
        courseBase.setStatus("203001");
        //课程基本表插入一条数据
        int insert = courseBaseMapper.insert(courseBase);
        //获取课程id
        Long courseId = courseBase.getId();
        //将dto中和courseMarket属性名一样的属性值拷贝到courseMarket
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(dto,courseMarket);
        courseMarket.setId(courseId);
//        //校验如果课程为收费，价格必须输入
//        String charge = courseMarket.getCharge();
//        if (charge.equals("201001")){ //收费
//            if (courseMarket.getPrice()==null||courseMarket.getPrice().floatValue()<=0){
//                XueChengPlusException.cast("课程为收费但是价格为空");
//            }
//        }
        int insert1 = this.saveCourseMarket(courseMarket);
        //课程营销表插入一条数据
//        int insert1 = courseMarketMapper.insert(courseMarket);
        if (insert<1&&insert1<1){
            XueChengPlusException.cast("添加课程失败");
        }
        //组装要返回的结果
        return getCourseBaseInfo(courseId);
    }
    //根据课程id查询课程基本信息，包括基本信息和营销信息
    public CourseBaseInfoDto getCourseBaseInfo(long courseId){
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        if(courseBase == null){
            return null;
        }
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase,courseBaseInfoDto);
        if(courseMarket != null){
            BeanUtils.copyProperties(courseMarket,courseBaseInfoDto);
        }
        //查询分类名称
        CourseCategory courseCategoryBySt =
                courseCategoryMapper.selectById(courseBase.getSt());
        courseBaseInfoDto.setStName(courseCategoryBySt.getName());
        CourseCategory courseCategoryByMt =
                courseCategoryMapper.selectById(courseBase.getMt());
        courseBaseInfoDto.setMtName(courseCategoryByMt.getName());
        return courseBaseInfoDto;
    }

    @Override
    @Transactional
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto) {
        //校验
        //课程id
        Long courseId = dto.getId();
        if (courseId==null){
            System.out.println("课程id为空");
        }
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase==null){
            XueChengPlusException.cast("课程不存在");
        }
        //校验本机构只能修改本机构的课程
        if (!courseBase.getCompanyId().equals(companyId)){
            XueChengPlusException.cast("本机构只能修改本机构的课程");
        }
        //封装基本信息的数据
        BeanUtils.copyProperties(dto,courseBase);
        courseBase.setChangeDate(LocalDateTime.now());
        courseBaseMapper.updateById(courseBase);
        //封装营销信息的数据
        CourseMarket courseMarket=new CourseMarket();
        if (courseMarket!=null){
            BeanUtils.copyProperties(dto,courseMarket);
        }
        saveCourseMarket(courseMarket);
        //查询课程信息
        CourseBaseInfoDto courseBaseInfo = this.getCourseBaseInfo(courseId);
        return courseBaseInfo;
    }

    private int saveCourseMarket(CourseMarket courseMarket) {
        //校验如果课程为收费，价格必须输入
        String charge = courseMarket.getCharge();
        if (StringUtils.isBlank(charge)){
            XueChengPlusException.cast("收费规则没有选择");
        }
        if (charge.equals("201001")){ //收费
            if (courseMarket.getPrice()==null|| courseMarket.getPrice().floatValue()<=0){
               XueChengPlusException.cast("课程为收费但是价格为空");
            }
        }
        //请求数据库
        //对营销表有则更新，没有则添加
        boolean b = courseMarketService.saveOrUpdate(courseMarket);
        return b?1:0;
    }
}
