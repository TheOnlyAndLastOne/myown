package com.myown.manage.mapper;

import com.github.abel533.mapper.Mapper;
import com.myown.manage.pojo.Content;

import java.util.List;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/29 0029 11:17
 * @Description:
 */
public interface ContentMapper extends Mapper<Content> {
    /**
     * 根据categoryId查询内容列表，并且根据更新时间排序
     * @param categoryId
     * @return
     */
    public List<Content> queryContentList(Long categoryId);
}
