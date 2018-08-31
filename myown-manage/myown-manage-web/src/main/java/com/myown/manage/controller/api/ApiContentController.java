package com.myown.manage.controller.api;

import com.myown.common.bean.EasyUIResult;
import com.myown.manage.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/29 0029 11:22
 * @Description:
 */
@Controller
@RequestMapping("/api/content")
public class ApiContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 根据内容分类id查询分类列表
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryListByCategoryId(@RequestParam("categoryId")Long categoryId,
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "10")Integer rows){
        try {
            EasyUIResult easyUIResult = this.contentService.queryListByCategoryId(categoryId,page,rows);
            return ResponseEntity.ok(easyUIResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }



}
