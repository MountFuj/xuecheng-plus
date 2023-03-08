package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

public interface CourseCategoryService {
    /***
     * @description 课程分类查询
     * @param id 根节点id
     * @return根节点下面的所有子节点
     * @author
     * @date
    */
    public List<CourseCategoryTreeDto> queryTreeNodes(String id);
}
