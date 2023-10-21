package com.test;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.Test;

public class Test1 {

    @Test
    public void test1(){

        JSONObject jsonObject =  JSONUtil.createObj();
        jsonObject.put("aabcd", 1);
        Student s = new Student();
        s.setName("absda");
        s.setPassword("sdfas");
        jsonObject.put("data",s);
        System.out.println(jsonObject.toJSONString(1));
    }

}
