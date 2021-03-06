package com.myown.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.myown.common.bean.EasyUIResult;
import com.myown.manage.pojo.Content;
import com.myown.common.service.ApiService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhaozhi
 * @Date: 2018/8/29 0029 15:29
 * @Description:
 */
@Service
public class IndexService  {
    @Autowired
    private ApiService apiService;

    @Value("${MYOWN_MANAGE_URL}")
    private String MYOWN_MANAGE_URL;

    @Value("${INDEX_AD1_URL}")
    private String INDEX_AD1_URL;

    @Value("${INDEX_AD2_URL}")
    private String INDEX_AD2_URL;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public String queryIndexAD1() {
        try {
            String url = MYOWN_MANAGE_URL + INDEX_AD1_URL;
            String jsonData = this.apiService.doGet(url);
            if (StringUtils.isEmpty(jsonData)) {
                return null;
            }
            // 解析json数据，封装成前端所需要的结构
            EasyUIResult easyUIResult = EasyUIResult.formatToList(jsonData, Content.class);
            List<Content> contents = (List<Content>) easyUIResult.getRows();
            List<Map<String, Object>> result = new ArrayList<>();
            for (Content content : contents) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("srcB", content.getPic());
                map.put("height", 240);
                map.put("alt",content.getTitle());
                map.put("width", 670);
                map.put("src", content.getPic());
                map.put("widthB", 550);
                map.put("href", content.getUrl());
                map.put("heightB", 240);
                result.add(map);
            }
            return MAPPER.writeValueAsString(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*public String queryIndexAD1() {
        try {
            String url = MYOWN_MANAGE_URL+INDEX_AD1_URL;
            String jsonData = this.apiService.doGet(url);
            if (StringUtils.isEmpty(jsonData)) {
                return null;
            }
            //解析json
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            ArrayNode rows = (ArrayNode) jsonNode.get("rows");
            List<Map<String,Object>> result = new ArrayList<>();
            for(JsonNode row : rows){
                Map<String,Object> map = new LinkedHashMap<>();
                map.put("srcB",row.get("pic").asText());
                map.put("height",240);
                map.put("alt",row.get("title").asText());
                map.put("width",670);
                map.put("src",row.get("pic").asText());
                map.put("widthB", 550);
                map.put("href",row.get("url").asText());
                map.put("heightB",240);
                result.add(map);
            }
            return MAPPER.writeValueAsString(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/



    public String queryIndexAD2() {
        try {
            String url = MYOWN_MANAGE_URL+INDEX_AD2_URL;
            String jsonData = this.apiService.doGet(url);
            if (StringUtils.isEmpty(jsonData)) {
                return null;
            }
            //解析json
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            ArrayNode rows = (ArrayNode) jsonNode.get("rows");
            List<Map<String,Object>> result = new ArrayList<>();
            for(JsonNode row : rows){
                Map<String,Object> map = new LinkedHashMap<>();
                map.put("width",310);
                map.put("height",70);
                map.put("src",row.get("pic").asText());
                map.put("href",row.get("url").asText());
                map.put("alt",row.get("title").asText());
                map.put("widthB", 210);
                map.put("heightB", 70);
                map.put("srcB",row.get("pic").asText());
                result.add(map);
            }
            return MAPPER.writeValueAsString(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
