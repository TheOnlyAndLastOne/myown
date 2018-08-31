package com.myown.manage.controller.api;

import com.myown.manage.pojo.Item;
import com.myown.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/30 0030 9:01
 * @Description:
 */
@Controller
@RequestMapping("/api/item")
public class ApiItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/{itemId}",method = RequestMethod.GET)
    public ResponseEntity<Item> queryById(@PathVariable("itemId")Long itemId){
        try {
            Item item = this.itemService.queryById(itemId);
            if (null == item) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
