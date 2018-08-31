package com.myown.manage.service;

import com.myown.manage.pojo.ContentCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/29 0029 11:18
 * @Description:
 */
@Service
public class ContentCategoryService extends BaseService<ContentCategory> {

    /**
     * 新增节点
     * @param contentCategory
     */
    public void saveContentCategory(ContentCategory contentCategory) {
        contentCategory.setId(null);
        contentCategory.setIsParent(false);
        contentCategory.setSortOrder(1);
        contentCategory.setStatus(1);
        super.save(contentCategory);

        //判断该节点的父节点isparent是否为true,如果不是修改
        ContentCategory parent = super.queryById(contentCategory.getParentId());
        if(!parent.getIsParent()){
            parent.setIsParent(true);
            super.update(parent);
        }
    }

    public void deleteAll(ContentCategory contentCategory) {
        List<Object> ids = new ArrayList<Object>();
        ids.add(contentCategory.getId());

        //递归查找该节点下的所有子节点id
        this.findAllSubNode(ids,contentCategory.getId());
        super.deleteByIds(ids,ContentCategory.class,"id");

        //判断该节点是否还有兄弟节点，如果没有，修改父节点的isparent为false
        ContentCategory record = new ContentCategory();
        record.setParentId(contentCategory.getParentId());
        List<ContentCategory> list = super.queryListByWhere(record);
        if(null == list || list.isEmpty()){
            ContentCategory parent = new ContentCategory();
            parent.setId(contentCategory.getParentId());
            parent.setIsParent(false);
            super.updateSelective(parent);
        }
    }

    private void findAllSubNode(List<Object> ids ,Long pid){
        ContentCategory record = new ContentCategory();
        record.setParentId(pid);
        List<ContentCategory> list = super.queryListByWhere(record);
        for(ContentCategory contentCategory : list){
            ids.add(contentCategory.getId());
            //判断该节点是不是父节点，如果是，递归
            if(contentCategory.getIsParent()){
                findAllSubNode(ids,contentCategory.getId());
            }
        }
    }
}
