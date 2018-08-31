package com.myown.manage.controller.api;

import com.myown.manage.pojo.ItemParamItem;
import com.myown.manage.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/28 0028 11:55
 * @Description:
 */
@Controller
@RequestMapping("/api/item/param/item")
public class ApiItemParamItemController {

    @Autowired
    private ItemParamItemService itemParamItemService;

    /**
     * 根据商品id查询规格参数数据
     * @param itemId
     * @return
     */
    @RequestMapping(value="{itemId}",method = RequestMethod.GET)
    public ResponseEntity<ItemParamItem> queryByItemId(@PathVariable("itemId")Long itemId){
        try {
            ItemParamItem record = new ItemParamItem();
            record.setItemId(itemId);
            ItemParamItem itemParamItem = this.itemParamItemService.queryOne(record);
            if (null == itemParamItem) {
                //404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(itemParamItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
