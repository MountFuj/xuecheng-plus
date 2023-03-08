package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @description TODO
 * @date 2023/3/6 14:44
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory implements Serializable {
    List childrenTreeNodes;
}
