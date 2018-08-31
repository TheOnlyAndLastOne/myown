package com.myown.manage.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myown.common.bean.EasyUIResult;
import com.myown.manage.mapper.ContentMapper;
import com.myown.manage.pojo.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/29 0029 11:20
 * @Description:
 */
@Service
public class ContentService extends BaseService<Content> {

    @Autowired
    private ContentMapper contentMapper;

    public EasyUIResult queryListByCategoryId(Long categoryId, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        List<Content> list = this.contentMapper.queryContentList(categoryId);
        PageInfo<Content> pageInfo = new PageInfo<Content>(list);
        return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
    }
}
