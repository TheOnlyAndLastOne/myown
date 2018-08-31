package com.myown.manage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/27 0027 17:47
 * @Description:
 */
@Service
public class PropertieService {
    @Value("${REPOSITORY_PATH}")
    public String REPOSITORY_PATH;

    @Value("${IMAGE_BASE_URL}")
    public String IMAGE_BASE_URL;
}
