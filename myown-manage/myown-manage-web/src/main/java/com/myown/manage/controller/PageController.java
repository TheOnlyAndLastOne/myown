package com.myown.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/27 0027 10:13
 * @Description:
 */
@Controller
@RequestMapping("/page")
public class PageController {
    /**
     * 具体的跳转页面逻辑 -- test4
     *
     * @param pageName
     * @return 视图名
     */
    @RequestMapping(value = "/{pageName}", method = RequestMethod.GET)
    public String toPage(@PathVariable("pageName") String pageName) {
        return pageName;
    }

}
