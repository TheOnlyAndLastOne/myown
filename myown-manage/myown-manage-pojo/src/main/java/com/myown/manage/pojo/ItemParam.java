package com.myown.manage.pojo;

import javax.persistence.*;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/28 0028 11:49
 * @Description:
 */
@Table(name = "tb_item_param")
public class ItemParam extends BasePojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_cat_id")
    private Long itemCatId;

    @Column(name = "param_data")
    private String paramData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemCatId() {
        return itemCatId;
    }

    public void setItemCatId(Long itemCatId) {
        this.itemCatId = itemCatId;
    }

    public String getParamData() {
        return paramData;
    }

    public void setParamData(String paramData) {
        this.paramData = paramData;
    }
}
