package com.myown.manage.service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myown.common.bean.EasyUIResult;
import com.myown.common.service.ApiService;
import com.myown.manage.mapper.ItemMapper;
import com.myown.manage.pojo.Item;
import com.myown.manage.pojo.ItemDesc;
import com.myown.manage.pojo.ItemParam;
import com.myown.manage.pojo.ItemParamItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/27 0027 16:43
 * @Description:
 */
@Service
public class ItemService extends BaseService<Item> {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemDescService itemDescService;

    @Autowired
    private ItemParamItemService itemParamItemService;

    @Autowired
    private ApiService apiService;

    @Value("${MYOWN_WEB_URL}")
    private String MYOWN_WEB_URL;

    public Boolean saveItem(Item item,String desc,String itemParams){
        //保存商品数据
        item.setStatus(1);
        item.setId(null);
        Integer count1 = super.save(item);

        //保存商品描述数据
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        Integer count2 = this.itemDescService.save(itemDesc);

        //保存规格参数数据
        ItemParamItem itemParamItem = new ItemParamItem();
        itemParamItem.setItemId(item.getId());
        itemParamItem.setParamData(itemParams);
        Integer count3 = this.itemParamItemService.save(itemParamItem);

        return count1.intValue() == 1 && count2.intValue()==1 && count3.intValue()==1;
    }

    /**
     * 商品列表
     * @param page
     * @param rows
     * @return
     */
    public EasyUIResult queryItemList(Integer page, Integer rows) {
        //设置分页参数
        PageHelper.startPage(page,rows);
        Example example = new Example(ItemMapper.class);
        example.setOrderByClause("created DESC");
        List<Item> items = this.itemMapper.selectByExample(example);
        PageInfo<Item> pageInfo = new PageInfo<Item>(items);
        return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 编辑商品
     * @param item
     * @param desc
     * @return
     */
    public Boolean updateItem(Item item, String desc, String itemParams) {
        //强制设置状态不能被修改
        item.setStatus(null);
        Integer count1 = super.updateSelective(item);
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        Integer count2 = this.itemDescService.updateSelective(itemDesc);

        //更新规格参数
        Integer count3 = this.itemParamItemService.updateItemParamItem(item.getId(),itemParams);

        //删除本系统的缓存

        try {
            //通知其他系统该商品已经更新，让其他系统去删除系统中的缓存
            String url = MYOWN_WEB_URL+"/item/cache/"+item.getId()+".html";
            this.apiService.doPost(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return count1.intValue() == 1 && count2.intValue()==1 && count3.intValue()==1;
    }
}
