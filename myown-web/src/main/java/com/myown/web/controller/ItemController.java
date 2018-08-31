package com.myown.web.controller;

import com.myown.manage.pojo.ItemDesc;
import com.myown.web.bean.Item;
import com.myown.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/30 0030 8:53
 * @Description:
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value="/{itemId}",method = RequestMethod.GET)
    public ModelAndView itemDetail(@PathVariable("itemId")Long itemId){
        ModelAndView mv = new ModelAndView("item");
        //商品
        Item item = this.itemService.queryById(itemId);
        mv.addObject("item",item);

        //商品详情
        ItemDesc itemDesc = this.itemService.queryDescById(itemId);
        mv.addObject("itemDesc",itemDesc);

        //规格参数
        String itemParam = this.itemService.queryItemParamItemByItemId(itemId);
        mv.addObject("itemParam",itemParam);
        return mv;
    }


}
