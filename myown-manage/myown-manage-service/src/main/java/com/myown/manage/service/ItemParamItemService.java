package com.myown.manage.service;

import com.github.abel533.entity.Example;
import com.myown.manage.mapper.ItemParamItemMapper;
import com.myown.manage.pojo.ItemParamItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/28 0028 11:54
 * @Description:
 */
@Service
public class ItemParamItemService extends BaseService<ItemParamItem> {

    @Autowired
    private ItemParamItemMapper itemParamItemMapper;

    public Integer updateItemParamItem(Long itemId, String itemParams) {

        //更新的数据
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setParamData(itemParams);
        itemParamItem.setUpdated(new Date());

        //更新的条件
        Example example = new Example(ItemParamItem.class);
        example.createCriteria().andEqualTo("itemId",itemId);
        return this.itemParamItemMapper.updateByExampleSelective(itemParamItem,example);
    }
}
