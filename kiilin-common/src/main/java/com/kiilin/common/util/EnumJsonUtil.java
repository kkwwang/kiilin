package com.kiilin.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializeConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 枚举转json
 *
 * @author wagk
 */
public class EnumJsonUtil {

    public static JSON toJson(Class<? extends Enum> enumClass) {
        try {
            Method methodValues = enumClass.getMethod("values");
            Object[] invoke = (Object[]) methodValues.invoke(null);

            SerializeConfig config = new SerializeConfig();
            config.configEnumAsJavaBean(enumClass);

            String jsonString = JSON.toJSONString(invoke, config);

            return JSONArray.parseArray(jsonString);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
