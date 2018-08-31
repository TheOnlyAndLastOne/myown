package com.myown.web.controller;

import com.myown.common.service.RedisService;
import com.myown.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/30 0030 10:53
 * @Description:
 */
@Controller
@RequestMapping("/item/cache")
public class ItemCacheController {

    @Autowired
    private RedisService redisService;

    @RequestMapping(value="/{itemId}",method = RequestMethod.POST)
    public ResponseEntity<Void> deleteCache(@PathVariable("itemId")Long itemId){
        try {
            this.redisService.del(ItemService.REDIS_KEY+itemId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
