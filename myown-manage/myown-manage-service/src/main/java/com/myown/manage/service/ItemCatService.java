package com.myown.manage.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myown.common.bean.ItemCatData;
import com.myown.common.bean.ItemCatResult;
import com.myown.common.service.RedisService;
import com.myown.manage.pojo.ItemCat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/27 0027 11:14
 * @Description:
 */
@Service
public class ItemCatService extends BaseService<ItemCat>{

    //自动注入和getMapper()会被泛型注入代替，泛型注入只在spring4之后才能使用
    /*@Autowired
    private ItemCatMapper itemCatMapper;*/

    /*public Mapper getMapper() {
        return this.itemCatMapper;
    }*/

    //该方法被通用BaseService中的方法代替
    /*public List<ItemCat> queryItemCatListByParentId(Long pid) {
        ItemCat record = new ItemCat();
        record.setParentId(pid);
        return this.itemCatMapper.select(record);
    }*/

    @Autowired
    private RedisService redisService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String REDIS_KEY = "OWN_MANAGE_ITEM_CAT_API";

    private static final Integer REDIS_TIME = 60*60*24*30*3;

    /**
     * 全部查询，并且生成树状结构
     * @return
     */
    public ItemCatResult queryAllToTree() {
        ItemCatResult result = new ItemCatResult();
        try {
            //先从缓存中读取，有返回，没有从数据库取
            String cacheData = this.redisService.get(REDIS_KEY);
            if(StringUtils.isNotEmpty(cacheData)){
                return MAPPER.readValue(cacheData,ItemCatResult.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 全部查出，并且在内存中生成树形结构
        List<ItemCat> cats = super.queryAll();

        // 转为map存储，key为父节点ID，value为数据集合
        Map<Long, List<ItemCat>> itemCatMap = new HashMap<>();
        for (ItemCat itemCat : cats) {
            if(!itemCatMap.containsKey(itemCat.getParentId())){
                itemCatMap.put(itemCat.getParentId(), new ArrayList<>());
            }
            itemCatMap.get(itemCat.getParentId()).add(itemCat);
        }

        // 封装一级对象
        List<ItemCat> itemCatList1 = itemCatMap.get(0L);
        for (ItemCat itemCat : itemCatList1) {
            ItemCatData itemCatData = new ItemCatData();
            itemCatData.setUrl("/products/" + itemCat.getId() + ".html");
            itemCatData.setName("<a href='"+itemCatData.getUrl()+"'>"+itemCat.getName()+"</a>");
            result.getItemCats().add(itemCatData);
            if(!itemCat.getIsParent()){
                continue;
            }

            // 封装二级对象
            List<ItemCat> itemCatList2 = itemCatMap.get(itemCat.getId());
            List<ItemCatData> itemCatData2 = new ArrayList<>();
            itemCatData.setItems(itemCatData2);
            for (ItemCat itemCat2 : itemCatList2) {
                ItemCatData id2 = new ItemCatData();
                id2.setName(itemCat2.getName());
                id2.setUrl("/products/" + itemCat2.getId() + ".html");
                itemCatData2.add(id2);
                if(itemCat2.getIsParent()){
                    // 封装三级对象
                    List<ItemCat> itemCatList3 = itemCatMap.get(itemCat2.getId());
                    List<String> itemCatData3 = new ArrayList<>();
                    id2.setItems(itemCatData3);
                    for (ItemCat itemCat3 : itemCatList3) {
                        itemCatData3.add("/products/" + itemCat3.getId() + ".html|"+itemCat3.getName());
                    }
                }
            }
            if(result.getItemCats().size() >= 14){
                break;
            }
        }

        //将数据库查询结果集写到缓存中
        try {
            this.redisService.set(REDIS_KEY,MAPPER.writeValueAsString(result),REDIS_TIME);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
