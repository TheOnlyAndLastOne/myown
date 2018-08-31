package com.myown.web.controller;

import com.myown.web.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/28 0028 15:53
 * @Description:
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private IndexService indexservice;

    /**
     * 首页
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        //首页大广告
        String indexAD1 = this.indexservice.queryIndexAD1();
        mv.addObject("indexAD1",indexAD1);
        //首页小广告
        String indexAD2 = this.indexservice.queryIndexAD2();
        mv.addObject("indexAD2",indexAD2);
        return mv;
    }

}
