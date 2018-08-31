package com.myown.web.bean;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/30 0030 9:30
 * @Description:
 */
public class Item extends com.myown.manage.pojo.Item {
    public String[] getImages(){
        return StringUtils.split(super.getImage(),",");
    }
}
