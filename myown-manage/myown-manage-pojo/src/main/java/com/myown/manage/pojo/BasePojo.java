package com.myown.manage.pojo;

import java.util.Date;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/27 0027 11:03
 * @Description:
 */
public class BasePojo {
    private Date created;
    private Date updated;
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
    public Date getUpdated() {
        return updated;
    }
    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
