/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lib.base.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * 描述：json处理类.
 */
public class JsonUtil {

    private static JsonUtil jsonHelper;
    private Gson gson;

    private JsonUtil() {
        gson = new Gson();
    }

    public static JsonUtil getInstance() {
        if (jsonHelper == null) {
            jsonHelper = new JsonUtil();
        }
        return jsonHelper;
    }

    /**
     * 描述：将对象转化为json.
     *
     * @param obj
     * @return
     */
    public String toJson(Object obj) {
        String json = null;
        try {
            json = gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 描述：将列表转化为json.
     *
     * @param list
     * @return
     */
    public String toJson(List<?> list) {
        String json = null;
        try {
            json = gson.toJson(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 描述：将json转化为列表.
     *
     * @param json
     * @param typeToken new TypeToken<ArrayList<?>>() {}.getType();
     * @return
     */
    public <T> List<T> fromJson(String json, TypeToken typeToken) {
        List<T> list = null;
        try {
            Type type = typeToken.getType();
            list = gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 描述：将json转化为列表.
     *
     * @param json
     * @param type
     * @return
     */
    public <T> T fromJson(String json, Type type) {
        T t = null;
        try {
            t = gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 描述：将json转化为对象.
     *
     * @param json
     * @param clazz
     * @return
     */
    public <T> T fromJson(String json, Class clazz) {
        T obj = null;
        try {
            obj = (T) gson.fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        List<User> list = getInstance().fromJson("[{id:1,name:22},{id:2,name:33}]", new TypeToken<ArrayList<User>>() {
        });
        System.out.println(list.size());
        for (User u : list) {
            System.out.println(u.getName());
        }

        User u = getInstance().fromJson("{id:1,name:22}", User.class);
        System.out.println(u.getName());
    }

    static class User {
        String id;
        String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
